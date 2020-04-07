package com.yuhubs.ecom.catalog.app;

import com.yuhubs.ms.auth.mail.AuthEmailSender;
import com.yuhubs.ms.mail.MailTemplateSupport;
import com.yuhubs.ms.security.auth.AuthEventListenerSupport;
import com.yuhubs.ms.security.auth.event.data.ConfirmEmailData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailException;

@Configuration
@Profile("dev")
public class AuthEventHandler extends AuthEventListenerSupport {

	private static Logger LOG = LoggerFactory.getLogger("yuhubs.ms.auth.mail");


	private final AuthEmailSender emailSender;

	@Autowired
	private MailTemplateSupport mailTemplateSupport;


	public AuthEventHandler() {
		this.emailSender = new AuthEmailSender();
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
