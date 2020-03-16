package com.yuhubs.ms.mail;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.io.IOException;

public final class MailBeanInitProcessor implements BeanPostProcessor {

	private final MailConfigurationSupport support;


	MailBeanInitProcessor(MailConfigurationSupport support) {
		this.support = support;
	}


	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if ("mailProperties".equalsIgnoreCase(beanName)) {
			try {
				((MailProperties) bean).load(support.getResourceLoader());
			} catch (IOException e) {
				throw new BeanInitializationException(e.getMessage(), e);
			}
		}
		return bean;
	}

}
