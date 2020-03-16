package com.yuhubs.ms.mail.template;

import freemarker.cache.StringTemplateLoader;
import freemarker.core.ParseException;
import freemarker.template.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class TemplateEngine {

	private final Configuration freemarker;

	private final ReadWriteLock rwl;
	private final Lock readLock;
	private final Lock writeLock;


	public TemplateEngine() {
		this.rwl = new ReentrantReadWriteLock();
		this.readLock = rwl.readLock();
		this.writeLock = rwl.writeLock();

		this.freemarker = new Configuration(Configuration.getVersion());

		init();
	}


	private void init() {
		StringTemplateLoader loader = new StringTemplateLoader();
		this.freemarker.setTemplateLoader(loader);

		this.freemarker.setDefaultEncoding("UTF-8");
	}


	public Configuration getConfiguration() {
		return this.freemarker;
	}

	public TemplatesInitializer initTemplates() {
		return new TemplatesInitializer(this.freemarker);
	}

	public void setSharedVariables(Map<String, ?> map) {
		try {
			this.freemarker.setSharedVaribles(map);
		} catch (TemplateModelException e) {
			// TODO
		}
	}

	public Template getTemplate(String name,
								TemplateContentProvider contentProvider)
			throws IOException {
		Template template = getTemplate(name);
		if (template != null) {
			return template;
		}

		readLock.lock();
		template = getTemplate(name);
		if (template == null) {
			readLock.unlock();
			writeLock.lock();

			try {
				template = getTemplate(name);
				if (template == null) {
					String content = contentProvider.getTemplateContent(name);

					if (content != null) {
						putTemplate(name, content);
					}
				}

				readLock.lock();
			} finally {
				writeLock.unlock();
			}
		}
		readLock.unlock();

		if (template == null) {
			template = this.freemarker.getTemplate(name);
		}

		return template;
	}

	private Template getTemplate(String name)
			throws MalformedTemplateNameException, ParseException, IOException {
		try {
			return this.freemarker.getTemplate(name);
		} catch (TemplateNotFoundException e) {
			return null;
		}
	}

	private void putTemplate(String name, String content) {
		StringTemplateLoader loader =
				(StringTemplateLoader)this.freemarker.getTemplateLoader();

		loader.putTemplate(name, content);

		this.freemarker.setTemplateLoader(loader);

		this.freemarker.clearTemplateCache();
	}

}
