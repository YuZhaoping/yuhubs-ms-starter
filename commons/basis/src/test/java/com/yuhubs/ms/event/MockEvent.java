package com.yuhubs.ms.event;

import org.springframework.context.ApplicationEvent;

public class MockEvent extends ApplicationEvent {

	public MockEvent(Object source) {
		super(source);
	}

	public MockEvent(int id) {
		super(Integer.valueOf(id));
	}

}
