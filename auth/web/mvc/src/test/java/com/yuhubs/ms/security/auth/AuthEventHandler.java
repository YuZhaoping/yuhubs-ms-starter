package com.yuhubs.ms.security.auth;

import com.yuhubs.ms.security.auth.event.data.ConfirmEmailData;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthEventHandler extends AuthEventListenerSupport {

	@Override
	protected void handleConfirmEmail(ConfirmEmailData eventData) {
	}

}
