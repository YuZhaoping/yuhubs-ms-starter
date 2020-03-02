package com.yuhubs.ms.security.auth.events;

import org.springframework.context.ApplicationEvent;

public abstract class AuthBaseEvent<T> extends ApplicationEvent {

	private static final long serialVersionUID = 868842321993836094L;


	protected T eventData;


	public AuthBaseEvent(T eventData) {
		super(eventData);
		this.eventData = eventData;
	}

	public AuthBaseEvent(Object source, T eventData) {
		super(source);
		this.eventData = eventData;
	}


	public T getEventData() {
		return eventData;
	}

	public void setEventData(T eventData) {
		this.eventData = eventData;
	}

}
