package com.yuhubs.ms.mail;

import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public final class MailSessionContext {

	private final MailSender sender;

	private final MailConfigurationSupport support;

	private final MailProperties mailProps;

	private final Object lock;
	private volatile Session session;


	MailSessionContext(MailConfigurationSupport support, MailProperties mailProperties) {
		this.sender = new MailSender(this);

		this.support = support;
		this.mailProps = mailProperties;

		this.lock = new Object();
	}


	public MailSender getSender() {
		return this.sender;
	}


	public Session getSession() {
		Session session = this.session;
		if (session == null) {
			synchronized (this.lock) {
				session = this.session;
				if (session == null) {
					session = createSession();
					this.session = session;
				}
			}
		}
		return session;
	}

	private Session createSession() {
		final Properties props = mailProps.getProperties();

		final String username = mailProps.getAuthUsername();
		final String password = mailProps.getAuthPassword();

		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		return session;
	}


	public MimeMessage createMimeMessage() {
		return new MimeMessage(getSession());
	}

	public MimeMessage createFromMimeMessage() throws MessagingException {
		MimeMessage message = createMimeMessage();

		final String from = getAuthUserFrom();

		message.setFrom(new InternetAddress(from));

		return message;
	}

	public MimeMessage createMimeMessage(String recipient)
			throws MessagingException {
		MimeMessage message = createFromMimeMessage();

		message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

		return message;
	}


	public String getAuthUserFrom() {
		return mailProps.getAuthUsername();
	}

}
