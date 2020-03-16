package com.yuhubs.ms.mail;

import org.springframework.mail.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.*;

public final class MailSender implements JavaMailSender {

	private static final String HEADER_MESSAGE_ID = "Message-ID";

	private final MailSessionContext context;


	MailSender(MailSessionContext context) {
		this.context = context;
	}


	public static void transport(MimeMessage message) throws MessagingException {
		Transport.send(message);
	}


	public void testConnection() throws MessagingException {
		Transport transport = null;
		try {
			transport = connectTransport();
		} finally {
			if (transport != null) {
				transport.close();
			}
		}
	}


	@Override
	public void send(SimpleMailMessage... simpleMessages) throws MailException {
		List<MimeMessage> mimeMessages = new ArrayList<>(simpleMessages.length);
		for (SimpleMailMessage simpleMessage : simpleMessages) {
			MimeMailMessage message = new MimeMailMessage(createMimeMessage());
			simpleMessage.copyTo(message);
			mimeMessages.add(message.getMimeMessage());
		}

		send(mimeMessages.toArray(new MimeMessage[0]), simpleMessages);
	}

	@Override
	public void send(SimpleMailMessage simpleMessage) throws MailException {
		MimeMailMessage message = new MimeMailMessage(createMimeMessage());
		simpleMessage.copyTo(message);

		send(message.getMimeMessage(), simpleMessage);
	}


	@Override
	public MimeMessage createMimeMessage() {
		return this.context.createMimeMessage();
	}

	@Override
	public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
		try {
			return new MimeMessage(this.context.getSession(), contentStream);
		} catch (Exception ex) {
			throw new MailParseException("Could not parse raw MIME content", ex);
		}
	}

	@Override
	public void send(MimeMessage... mimeMessages) throws MailException {
		send(mimeMessages, null);
	}

	@Override
	public void send(MimeMessage mimeMessage) throws MailException {
		send(mimeMessage, null);
	}

	@Override
	public void send(MimeMessagePreparator... preparators) throws MailException {
		try {
			List<MimeMessage> mimeMessages = new ArrayList<>(preparators.length);

			for (MimeMessagePreparator preparator : preparators) {
				MimeMessage mimeMessage = createMimeMessage();
				preparator.prepare(mimeMessage);
				mimeMessages.add(mimeMessage);
			}

			send(mimeMessages.toArray(new MimeMessage[0]), null);
		} catch (MessagingException ex) {
			throw new MailParseException(ex);
		} catch (MailException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new MailPreparationException(ex);
		}
	}

	@Override
	public void send(MimeMessagePreparator preparator) throws MailException {
		try {
			MimeMessage mimeMessage = createMimeMessage();
			preparator.prepare(mimeMessage);

			send(mimeMessage, null);
		} catch (MessagingException ex) {
			throw new MailParseException(ex);
		} catch (MailException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new MailPreparationException(ex);
		}
	}


	private void send(MimeMessage mimeMessage, Object originalMessage) throws MailException {
		Map<Object, Exception> failedMessages = null;
		Transport transport = null;

		try {
			try {
				transport = doConnect(transport);
			} catch (AuthenticationFailedException ex) {
				throw new MailAuthenticationException(ex);
			} catch (Exception ex) {
				failedMessages = putFailedMessage(failedMessages, mimeMessage, originalMessage, ex);

				throw new MailSendException(
						"Mail server connection failed",
						ex, failedMessages);
			}

			try {
				doSend(transport, mimeMessage);
			} catch (Exception ex) {
				failedMessages = putFailedMessage(failedMessages, mimeMessage, originalMessage, ex);
			}
		} finally {
			if (transport != null) {
				close(transport, failedMessages);
			}
		}

		if (failedMessages != null) {
			throw new MailSendException(failedMessages);
		}
	}

	private void send(MimeMessage[] mimeMessages, Object[] originalMessages) throws MailException {
		Map<Object, Exception> failedMessages = null;
		Transport transport = null;

		try {
			for (int i = 0; i < mimeMessages.length; ++i) {
				try {
					transport = doConnect(transport);
				} catch (AuthenticationFailedException ex) {
					throw new MailAuthenticationException(ex);
				} catch (Exception ex) {
					for (int j = i; j < mimeMessages.length; ++j) {
						failedMessages = putFailedMessage(failedMessages,
								mimeMessages[j],
								(originalMessages != null) ? originalMessages[j] : null,
								ex);
					}

					throw new MailSendException(
							"Mail server connection failed",
							ex, failedMessages);
				}

				try {
					doSend(transport, mimeMessages[i]);
				} catch (Exception ex) {
					failedMessages = putFailedMessage(failedMessages,
							mimeMessages[i],
							(originalMessages != null) ? originalMessages[i] : null,
							ex);
				}
			}
		} finally {
			if (transport != null) {
				close(transport, failedMessages);
			}
		}

		if (failedMessages != null) {
			throw new MailSendException(failedMessages);
		}
	}


	private static void doSend(Transport transport, MimeMessage mimeMessage) throws MessagingException {
		if (mimeMessage.getSentDate() == null) {
			mimeMessage.setSentDate(new Date());
		}

		String messageId = mimeMessage.getMessageID();
		mimeMessage.saveChanges();
		if (messageId == null) {
			messageId = UUID.randomUUID().toString();
		}
		mimeMessage.setHeader(HEADER_MESSAGE_ID, messageId);

		Address[] addresses = mimeMessage.getAllRecipients();
		if (addresses == null) {
			addresses = new Address[0];
		}

		transport.sendMessage(mimeMessage, addresses);
	}

	private Transport doConnect(Transport transport) throws MessagingException {
		if (transport == null || !transport.isConnected()) {
			if (transport != null) {
				try {
					transport.close();
				} catch (Exception e) {
					// ignore
				}
			}

			transport = connectTransport();
		}

		return transport;
	}

	private Transport connectTransport() throws MessagingException {
		Transport transport = context.getSession().getTransport();

		transport.connect();

		return transport;
	}


	private static void close(Transport transport,
							  Map<Object, Exception> failedMessages)
			throws MailException {
		try {
			if (transport.isConnected()) {
				transport.close();
			}
		} catch (Exception ex) {
			if (failedMessages != null) {
				throw new MailSendException(
						"Failed to close server connection after message failures",
						ex,
						failedMessages);
			}
			else {
				throw new MailSendException(
						"Failed to close server connection after message sending",
						ex);
			}
		}
	}


	private static Map<Object, Exception> putFailedMessage(
			Map<Object, Exception> failedMessages,
			MimeMessage mimeMessage,  Object originalMessage,
			Exception ex) {

		if (failedMessages == null) {
			failedMessages = new LinkedHashMap<>();
		}

		Object original = (originalMessage != null) ? originalMessage : mimeMessage;

		failedMessages.put(original, ex);

		return failedMessages;
	}

}
