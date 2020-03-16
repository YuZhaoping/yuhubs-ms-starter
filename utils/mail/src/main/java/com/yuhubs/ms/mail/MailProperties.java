package com.yuhubs.ms.mail;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

@Component
public final class MailProperties {

	public static final String AUTH_USERNAME = "yuhubs.ms.mail.auth.username";
	public static final String AUTH_PASSWORD = "yuhubs.ms.mail.auth.password";

	private static final String SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	private static final String SMTP_SSL_TRUST = "mail.smtp.ssl.trust";
	private static final String SMTP_HOST = "mail.smtp.host";


	private String sourcePath = "classpath:mail.properties";

	private final Properties props;


	MailProperties() {
		this.props = new Properties();
	}


	void load(ResourceLoader loader) throws IOException {
		Resource resource = loader.getResource(sourcePath);

		PropertiesLoaderUtils.fillProperties(props, new EncodedResource(resource, "UTF-8"));

		init();
	}

	private void init() {
		String value = props.getProperty(SMTP_STARTTLS_ENABLE);
		if (value != null && "true".equalsIgnoreCase(value)) {
			value = props.getProperty(SMTP_SSL_TRUST);
			if (value == null) {
				value = props.getProperty(SMTP_HOST);
				if (value != null) {
					props.setProperty(SMTP_SSL_TRUST, value);
				}
			}
		}
	}


	public Properties getProperties() {
		return this.props;
	}

	public void setSourcePath(String path) {
		this.sourcePath = path;
	}

	public String getAuthUsername() {
		return props.getProperty(AUTH_USERNAME);
	}

	public String getAuthPassword() {
		return props.getProperty(AUTH_PASSWORD);
	}

}
