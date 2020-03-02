package com.yuhubs.ms.security.auth;

import com.yuhubs.ms.security.auth.events.ConfirmEmailEvent;
import com.yuhubs.ms.security.auth.events.data.ConfirmEmailData;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class AuthEventSupport {

	@EventListener
	public void onConfirmEmailEvent(ConfirmEmailEvent event) {
		handleConfirmEmail(event.getEventData());
	}


	protected void handleConfirmEmail(ConfirmEmailData eventData) {
	}

}
