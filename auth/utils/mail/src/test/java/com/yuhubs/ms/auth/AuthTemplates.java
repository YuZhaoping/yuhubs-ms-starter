package com.yuhubs.ms.auth;

import com.yuhubs.ms.config.YamlPropertyLoaderFactory;
import com.yuhubs.ms.mail.MailTemplateManager;
import com.yuhubs.ms.mail.template.config.TemplateConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:auth-templates.yml"}, factory = YamlPropertyLoaderFactory.class)
@ConfigurationProperties(prefix = "template")
public class AuthTemplates extends TemplateConfiguration {

	public static final String VERIFY_EMAIL_ID = "verify-email";
	public static final String RESET_PASSWORD_ID = "reset-password";


	@Bean
	public MailTemplateManager mailTemplateManager() {
		return new MailTemplateManager(this);
	}

}
