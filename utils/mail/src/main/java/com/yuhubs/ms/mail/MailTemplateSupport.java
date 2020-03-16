package com.yuhubs.ms.mail;

import com.yuhubs.ms.mail.template.TemplateEngine;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.Optional;

@Component
public final class MailTemplateSupport {

	private final MailConfigurationSupport support;


	MailTemplateSupport(MailConfigurationSupport support) {
		this.support = support;
	}


	public MailSender getMailSender() {
		return sessionContext().getSender();
	}

	public Optional<MailTemplate> getMailTemplate(String templateId) {
		return templateManager().getMailTemplate(templateId, this);
	}

	public String getRealPath(String path) {
		return support.getRealPath(path);
	}


	String loadTemplateContent(String sourcePath) throws IOException {
		Resource resource = loadTemplateResource(sourcePath);

		StringBuilder content = new StringBuilder();

		Reader reader = new EncodedResource(resource, "UTF-8").getReader();
		try {
			CharBuffer buf = CharBuffer.allocate(1024);

			while (reader.read(buf) > 0) {
				buf.flip();
				content.append(buf.toString());
				buf.clear();
			}

			return content.toString();
		} finally {
			reader.close();
		}
	}

	private Resource loadTemplateResource(String sourcePath) {
		if (sourcePath.startsWith("classpath:") ||
				sourcePath.startsWith("file:")) {
			return support.getResourceLoader().getResource(sourcePath);
		} else {
			String realPath = getRealPath(sourcePath);
			if (realPath != null) {
				sourcePath = "file:" + realPath;
			}
			return support.getResourceLoader().getResource(sourcePath);
		}
	}


	MailTemplateManager templateManager() {
		return support.getMailTemplateManager();
	}

	TemplateEngine templateEngine() {
		return templateManager().getTemplateEngine();
	}

	MailSessionContext sessionContext() {
		return support.getSessionContext();
	}

}
