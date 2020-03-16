package com.yuhubs.ms.mail;

import freemarker.template.TemplateException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class MailTemplateTest extends ConfiguredTestBase {

	@Autowired
	private MailTemplateSupport templateSupport;


	@Test
	public void testLoad() throws IOException {
		Optional<MailTemplate> templateOp = templateSupport.getMailTemplate("confirm-email");

		assertTrue(templateOp.isPresent());

		MailTemplate template = templateOp.get();

		String content = template.loadContent();
		assertTrue(content.length() > 0);

		assertTrue(MailTemplate.hasVariable(content));
	}

	@Test
	public void testProcess() throws IOException, TemplateException {
		Optional<MailTemplate> templateOp = templateSupport.getMailTemplate("confirm-email");

		assertTrue(templateOp.isPresent());

		MailTemplate template = templateOp.get();

		String content = template.processContentTemplate(null);
		assertTrue(content.length() > 0);

		assertFalse(MailTemplate.hasVariable(content));
	}

	@Test
	public void testCreateMimeMessage()
			throws MessagingException, IOException, TemplateException {
		Optional<MailTemplate> templateOp = templateSupport.getMailTemplate("confirm-email");

		assertTrue(templateOp.isPresent());

		MailTemplate template = templateOp.get();

		String recipient = "user01@nineforce.com";
		MimeMessage message = template.createMimeMessage(recipient, null);

		assertEquals(template.getEntry().getSubject(), message.getSubject());

		assertNotNull(message.getAllRecipients());
		assertEquals(recipient, message.getAllRecipients()[0].toString());

		assertNotNull(message.getFrom());
	}

	@Test
	public void testMultipartMimeMessage()
			throws MessagingException, IOException, TemplateException {
		Optional<MailTemplate> templateOp = templateSupport.getMailTemplate("confirm-email");

		assertTrue(templateOp.isPresent());

		MailTemplate template = templateOp.get();

		String recipient = "user01@nineforce.com";
		MimeMessageHelper helper = template.createMimeMessage(recipient, null, true);

		MimeMessage message = helper.getMimeMessage();

		assertEquals(template.getEntry().getSubject(), message.getSubject());

		assertNotNull(message.getAllRecipients());
		assertEquals(recipient, message.getAllRecipients()[0].toString());

		assertNotNull(message.getFrom());
	}

}
