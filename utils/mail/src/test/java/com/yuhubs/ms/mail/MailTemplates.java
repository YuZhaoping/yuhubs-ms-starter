package com.yuhubs.ms.mail;

import com.yuhubs.ms.mail.config.YamlPropertyLoaderFactory;
import com.yuhubs.ms.mail.template.config.TemplateConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:email-templates.yml"}, factory = YamlPropertyLoaderFactory.class)
@ConfigurationProperties(prefix = "emails")
public class MailTemplates extends TemplateConfiguration {

	@Bean
	public MailTemplateManager mailTemplateManager() {
		return new MailTemplateManager(this);
	}

}
