package com.yuhubs.ms.event;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class MockEventHandler {

	private volatile CountDownLatch doneSignal;


	public MockEventHandler() {
		this(0);
	}

	public MockEventHandler(int count) {
		this.doneSignal = new CountDownLatch(count);
	}


	public void handleEvent(MockEvent event) {
		this.doneSignal.countDown();
	}

	public void waitForDone() throws InterruptedException {
		this.doneSignal.await();
	}

	public void setCount(int count) {
		this.doneSignal = new CountDownLatch(count);
	}

}
