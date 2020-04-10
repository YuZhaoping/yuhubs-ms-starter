package com.yuhubs.ms.auth.app;

import com.yuhubs.ms.config.YamlPropertyLoaderFactory;
import com.yuhubs.ms.mail.MailTemplateManager;
import com.yuhubs.ms.mail.template.config.TemplateConfiguration;
import com.yuhubs.ms.security.auth.web.AuthTemplateViewID;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerViewResolver;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@PropertySource(value = {"classpath:auth-templates.yml"}, factory = YamlPropertyLoaderFactory.class)
@ConfigurationProperties(prefix = "template")
public class AuthTemplates extends TemplateConfiguration {

	public static final String VERIFY_EMAIL_ID = "verify-email";
	public static final String RESET_PASSWORD_ID = "reset-password";


	private final MailTemplateManager templateManager;


	public AuthTemplates() {
		this.templateManager = new MailTemplateManager(this);
	}


	@Bean
	public MailTemplateManager mailTemplateManager() {
		return this.templateManager;
	}


	@Bean
	public FreeMarkerConfigurer freeMarkerConfigurer() {
		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		configurer.setConfiguration(this.templateManager.getTemplateEngine().getConfiguration());
		return configurer;
	}

	@Bean
	public FreeMarkerViewResolver freeMarkerViewResolver() {
		FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();

		List<String> viewNames = Arrays.stream(AuthTemplateViewID.values())
				.map(e -> e.getId())
				.collect(Collectors.toList());

		resolver.setViewNames(viewNames.toArray(new String[0]));
		resolver.setDefaultCharset(Charset.forName("UTF-8"));

		List<MediaType> supportedMediaTypes = new ArrayList<>();
		supportedMediaTypes.add(MediaType.TEXT_HTML);
		resolver.setSupportedMediaTypes(supportedMediaTypes);

		return resolver;
	}

}
