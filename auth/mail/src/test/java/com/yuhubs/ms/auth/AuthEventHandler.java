package com.yuhubs.ms.auth;

import com.yuhubs.ms.auth.mail.AuthEmailSender;
import com.yuhubs.ms.mail.MailTemplateSupport;
import com.yuhubs.ms.security.auth.AuthEventSupport;
import com.yuhubs.ms.security.auth.event.data.ConfirmEmailData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;

@Configuration
public class AuthEventHandler extends AuthEventSupport {

	private final AuthEmailSender emailSender;

	@Autowired
	private MailTemplateSupport mailTemplateSupport;


	public AuthEventHandler() {
		this.emailSender = new AuthEmailSender();
	}


	@Bean
	public AuthEmailSender authEmailSender() {
		return this.emailSender;
	}


	@Override
	protected void handleConfirmEmail(ConfirmEmailData eventData) {
		String templateId;

		switch (eventData.getType()) {
			case RESET_PASSWORD:
				templateId = AuthTemplates.RESET_PASSWORD_ID;
				break;
			case VERIFY_EMAIL:
			default:
				templateId = AuthTemplates.VERIFY_EMAIL_ID;
				break;
		}

		try {
			this.emailSender.sendEmail(this.mailTemplateSupport,
					templateId, eventData);
		} catch (MailException e) {
			e.printStackTrace();
		}
	}

}
