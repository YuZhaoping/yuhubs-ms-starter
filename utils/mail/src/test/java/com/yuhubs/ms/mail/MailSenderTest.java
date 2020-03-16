package com.yuhubs.ms.mail;

import freemarker.template.TemplateException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

public class MailSenderTest extends ConfiguredTestBase {

	@Autowired
	private MailTemplateSupport templateSupport;


	@Test
	public void testConnection() throws MessagingException {
		MailSender sender = templateSupport.getMailSender();

		//sender.testConnection();
	}

	@Test
	public void testTransport()
			throws MessagingException, IOException, TemplateException {
		Optional<MailTemplate> templateOp = templateSupport.getMailTemplate("confirm-email");

		assertTrue(templateOp.isPresent());

		MailTemplate template = templateOp.get();

		String recipient = "user01@nineforce.com";
		MimeMessage message = template.createMimeMessage(recipient, null);

		//MailSender.transport(message);
	}

	@Test
	public void testSend()
			throws MessagingException, IOException, TemplateException {
		Optional<MailTemplate> templateOp = templateSupport.getMailTemplate("confirm-email");

		assertTrue(templateOp.isPresent());

		MailTemplate template = templateOp.get();

		String recipient = "user01@nineforce.com";
		MimeMessage message = template.createMimeMessage(recipient, null);

		//template.getMailSender().send(message);
	}

	@Test
	public void testSendMultipartMessage()
			throws MessagingException, IOException, TemplateException {
		Optional<MailTemplate> templateOp = templateSupport.getMailTemplate("confirm-email");

		assertTrue(templateOp.isPresent());

		MailTemplate template = templateOp.get();

		String recipient = "user01@nineforce.com";
		MimeMessageHelper helper = template.createMimeMessage(recipient, null, true);

		//template.getMailSender().send(helper.getMimeMessage());
	}

}
