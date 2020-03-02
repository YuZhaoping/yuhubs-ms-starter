package com.yuhubs.ms.security.auth.events;

import com.yuhubs.ms.security.auth.events.data.ConfirmEmailData;

public class ConfirmEmailEvent extends AuthBaseEvent<ConfirmEmailData> {

	public ConfirmEmailEvent(ConfirmEmailData eventData) {
		super(eventData);
	}

	public ConfirmEmailEvent(Object source, ConfirmEmailData eventData) {
		super(source, eventData);
	}

}
