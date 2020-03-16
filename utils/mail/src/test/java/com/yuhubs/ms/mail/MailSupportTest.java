package com.yuhubs.ms.mail;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Properties;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MailSupportTest extends ConfiguredTestBase {

	@Autowired
	private MailConfigurationSupport mailConfig;

	@Autowired
	private MailTemplates mailTemplates;


	@Test
	public void testProperties() {
		Properties props = mailConfig.mailProperties().getProperties();

		assertNotNull(props.getProperty("mail.smtp.host"));
		assertNotNull(props.getProperty("mail.smtp.port"));
	}

	@Test
	public void testTemplates() {
		assertNotNull(mailTemplates.getLocation());
		assertTrue(mailTemplates.getEntries().size() > 0);
	}

}
