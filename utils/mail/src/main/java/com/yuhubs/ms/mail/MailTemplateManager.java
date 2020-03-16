package com.yuhubs.ms.mail;

import com.yuhubs.ms.mail.template.TemplateEngine;
import com.yuhubs.ms.mail.template.TemplatesInitializer;
import com.yuhubs.ms.mail.template.config.TemplateConfiguration;
import com.yuhubs.ms.mail.template.config.TemplateEntry;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public final class MailTemplateManager {

	private final TemplateConfiguration config;

	private final TemplateEngine templateEngine;

	private final Map<String, MailTemplate> templates;
	private final ReadWriteLock rwl;
	private final Lock readLock;
	private final Lock writeLock;

	private volatile boolean initialized = false;


	public MailTemplateManager(TemplateConfiguration config) {
		this.config = config;

		this.templateEngine = new TemplateEngine();

		this.templates = new HashMap<>();
		this.rwl = new ReentrantReadWriteLock();
		this.readLock = rwl.readLock();
		this.writeLock = rwl.writeLock();
	}


	public TemplateEngine getTemplateEngine() {
		return this.templateEngine;
	}

	public void preloadTemplates(MailTemplateSupport support) {
		readLock.lock();
		if (!this.initialized) {
			readLock.unlock();
			writeLock.lock();
			try {
				if (!this.initialized) {
					doInit(support);
					this.initialized = true;
				}

				readLock.lock();
			} finally {
				writeLock.unlock();
			}
		}
		readLock.unlock();
	}

	public Optional<MailTemplate> getMailTemplate(String templateId,
												  MailTemplateSupport support) {
		MailTemplate template;
		readLock.lock();
		template = this.templates.get(templateId);
		if (template == null) {
			readLock.unlock();
			writeLock.lock();

			try {
				if (!this.initialized) {
					doInit(support);
					this.initialized = true;
				}

				template = this.templates.get(templateId);
				if (template == null) {
					template = createMailTemplate(templateId, support);

					if (template == null) {
						return Optional.empty();
					}

					this.templates.put(templateId, template);
				}

				readLock.lock();
			} finally {
				writeLock.unlock();
			}
		}
		readLock.unlock();

		return Optional.of(template);
	}

	private MailTemplate createMailTemplate(String templateId,
											MailTemplateSupport support) {
		TemplateEntry entry = getTemplateEntry(templateId);
		if (entry != null) {
			return new MailTemplate(support, entry);
		}
		return null;
	}

	private TemplateEntry getTemplateEntry(String templateId) {
		List<TemplateEntry> entries = this.config.getEntries();

		for (TemplateEntry entry : entries) {
			if (entry.getId().equals(templateId)) {
				return entry;
			}
		}

		return null;
	}


	String loadTemplateContent(TemplateEntry entry, MailTemplateSupport support)
			throws IOException {
		String content = support.loadTemplateContent(getTemplateFilePath(entry));

		entry.setContentLength(content.length());

		return content;
	}

	private String getTemplateFilePath(TemplateEntry entry) {
		String location = entry.getLocation();
		if (location == null || location.isEmpty()) {
			location = getTemplateLocation();
		}
		return location + entry.getFileName();
	}


	private void doInit(MailTemplateSupport support) {
		Map<String, String> variables = getTemplateVariables();
		support.templateEngine().setSharedVariables(variables);

		TemplatesInitializer initializer =
				support.templateEngine().initTemplates().begin();

		List<TemplateEntry> entries = this.config.getEntries();

		for (TemplateEntry entry : entries) {
			try {
				String content = loadTemplateContent(entry, support);

				initializer.putTemplate(contentTemplateName(entry), content);

				content = entry.getSubject();
				if (MailTemplate.hasVariable(content)) {
					initializer.putTemplate(subjectTemplateName(entry), content);
				}
			} catch (Exception e) {
				// ignore
			}
		}

		initializer.end();
	}


	private static String subjectTemplateName(TemplateEntry entry) {
		return MailTemplate.subjectTemplateName(entry);
	}

	private static String contentTemplateName(TemplateEntry entry) {
		return MailTemplate.contentTemplateName(entry);
	}


	private Map<String, String> getTemplateVariables() {
		return this.config.getVariables();
	}

	private String getTemplateLocation() {
		return this.config.getLocation();
	}

}
