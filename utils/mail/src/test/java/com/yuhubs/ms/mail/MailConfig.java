package com.yuhubs.ms.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

@Configuration
public class MailConfig extends MailConfigurationSupport {

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private MailTemplateManager mailTemplateManager;


	@Override
	protected ResourceLoader getResourceLoader() {
		return this.resourceLoader;
	}

	@Override
	protected MailTemplateManager getMailTemplateManager() {
		return this.mailTemplateManager;
	}

}
