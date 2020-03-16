package com.yuhubs.ms.mail;

import com.yuhubs.ms.mail.template.TemplateContentProvider;
import com.yuhubs.ms.mail.template.TemplateEngine;
import com.yuhubs.ms.mail.template.config.TemplateEntry;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;

public final class MailTemplate {

	private static final class ContentProvider implements TemplateContentProvider {

		private final MailTemplate template;

		private ContentProvider(MailTemplate template) {
			this.template = template;
		}

		@Override
		public String getTemplateContent(String name) throws IOException {
			if (name.endsWith(".subject")) {
				return template.entry.getSubject();
			} else {
				return template.loadContent();
			}
		}
	}


	private final MailTemplateSupport support;

	private final TemplateEntry entry;

	private final ContentProvider contentProvider;


	MailTemplate(MailTemplateSupport support, TemplateEntry entry) {
		this.support = support;
		this.entry = entry;
		this.contentProvider = new ContentProvider(this);
	}


	TemplateEntry getEntry() {
		return this.entry;
	}

	String loadContent() throws IOException {
		return support.templateManager().loadTemplateContent(entry, support);
	}


	public MailSender getMailSender() {
		return support.getMailSender();
	}

	public MimeMessage createMimeMessage(String recipient, Object dataModel)
			throws MessagingException, IOException, TemplateException {
		MimeMessageHelper helper = createMimeMessage(recipient, dataModel, false);

		return helper.getMimeMessage();
	}

	public MimeMessageHelper createMimeMessage(String recipient, Object dataModel, boolean multipart)
			throws MessagingException, IOException, TemplateException {
		final boolean isHtml = isHtmlContent();

		MimeMessage message = support.sessionContext().createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "UTF-8");

		String from = entry.getFrom();
		if (from == null) {
			from = support.sessionContext().getAuthUserFrom();
		}
		helper.setFrom(from);

		helper.setTo(recipient);

		String subject = entry.getSubject();
		if (hasVariable(subject)) {
			subject = processTemplate(subjectTemplateName(), dataModel, subject.length());
		}
		helper.setSubject(subject);

		String content = processContentTemplate(dataModel);

		helper.setText(content, isHtml);

		return helper;
	}

	private String processTemplate(String name, Object dataModel, int initialSize)
			throws IOException, TemplateException {
		Template template = templateEngine().getTemplate(name, this.contentProvider);

		StringWriter out = new StringWriter(initialSize > 0 ? initialSize : entry.getContentLength());

		template.process(dataModel, out);

		return out.toString();
	}

	String processContentTemplate(Object dataModel) throws IOException, TemplateException {
		return processTemplate(contentTemplateName(), dataModel, 0);
	}

	private String subjectTemplateName() {
		return subjectTemplateName(entry);
	}

	static String subjectTemplateName(TemplateEntry entry) {
		return entry.getId() + ".subject";
	}

	private String contentTemplateName() {
		return contentTemplateName(entry);
	}

	static String contentTemplateName(TemplateEntry entry) {
		return entry.getId();
	}

	public static boolean hasVariable(String content) {
		int i = content.indexOf('$');
		if (i >= 0 && (++i < content.length())) {
			if (content.charAt(i) == '{' && (++i < content.length())) {
				return content.indexOf('}', i) > i;
			}
		}
		return false;
	}

	private boolean isHtmlContent() {
		String contentType = entry.getContentType();
		if (contentType != null) {
			return contentType.startsWith("text/html");
		}
		return false;
	}

	private TemplateEngine templateEngine() {
		return support.templateEngine();
	}

}
