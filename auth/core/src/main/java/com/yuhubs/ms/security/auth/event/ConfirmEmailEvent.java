package com.yuhubs.ms.security.auth.event;

import com.yuhubs.ms.security.auth.event.data.ConfirmEmailData;

public class ConfirmEmailEvent extends AuthBaseEvent<ConfirmEmailData> {

	public ConfirmEmailEvent(ConfirmEmailData eventData) {
		super(eventData);
	}

	public ConfirmEmailEvent(Object source, ConfirmEmailData eventData) {
		super(source, eventData);
	}

}
