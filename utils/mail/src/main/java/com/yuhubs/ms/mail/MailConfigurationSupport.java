package com.yuhubs.ms.mail;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;

public abstract class MailConfigurationSupport {

	private final MailBeanInitProcessor initProcessor;

	protected final MailProperties properties;

	protected final MailSessionContext sessionContext;

	protected final MailTemplateSupport templateSupport;


	public MailConfigurationSupport() {
		this.initProcessor = new MailBeanInitProcessor(this);

		this.properties = new MailProperties();

		this.sessionContext = new MailSessionContext(this, this.properties);

		this.templateSupport = new MailTemplateSupport(this);
	}


	protected abstract ResourceLoader getResourceLoader();

	protected abstract MailTemplateManager getMailTemplateManager();


	protected String getRealPath(String path) {
		return null;
	}


	@Bean
	public BeanPostProcessor mailInitProcessor() {
		return this.initProcessor;
	}

	@Bean
	public MailProperties mailProperties() {
		return this.properties;
	}

	@Bean
	public MailSessionContext mailSessionContext() {
		return this.sessionContext;
	}

	@Bean
	public MailTemplateSupport mailTemplateSupport() {
		return this.templateSupport;
	}


	public final MailSender getMailSender() {
		return this.sessionContext.getSender();
	}


	final MailSessionContext getSessionContext() {
		return this.sessionContext;
	}

}
