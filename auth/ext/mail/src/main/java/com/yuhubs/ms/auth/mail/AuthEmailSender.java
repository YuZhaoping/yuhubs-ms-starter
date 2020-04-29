package com.yuhubs.ms.auth.mail;

import com.yuhubs.ms.mail.MailSender;
import com.yuhubs.ms.mail.MailTemplate;
import com.yuhubs.ms.mail.MailTemplateSupport;
import com.yuhubs.ms.security.auth.event.data.EmailAddressData;
import freemarker.template.TemplateException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailPreparationException;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Optional;

@Component
public final class AuthEmailSender {

	public interface Interceptor {
		void onSendEmail(MailSender sender, MimeMessage message) throws MailException;
	}


	private volatile Interceptor interceptor;


	public AuthEmailSender() {
		this.interceptor = null;
	}


	public void sendEmail(MailTemplateSupport support, String templateId, EmailAddressData data)
			throws MailException {
		Optional<MailTemplate> opt = support.getMailTemplate(templateId);
		if (!opt.isPresent()) {
			return;
		}

		MailTemplate template = opt.get();

		MimeMessage message = null;
		try {
			message = template.createMimeMessage(data.getEmail(), data);
		} catch (MessagingException e) {
			throw new MailPreparationException(e.getMessage(), e);
		} catch (IOException e) {
			throw new MailPreparationException(e.getMessage(), e);
		} catch (TemplateException e) {
			throw new MailPreparationException(e.getMessage(), e);
		}

		doSend(support.getMailSender(), message);
	}


	private void doSend(MailSender sender, MimeMessage message) throws MailException {
		final Interceptor interceptor = this.interceptor;

		if (interceptor != null) {
			interceptor.onSendEmail(sender, message);
		} else {
			sender.send(message);
		}
	}


	public Interceptor setInterceptor(Interceptor interceptor) {
		final Interceptor old = this.interceptor;

		this.interceptor = interceptor;

		return old;
	}

}
