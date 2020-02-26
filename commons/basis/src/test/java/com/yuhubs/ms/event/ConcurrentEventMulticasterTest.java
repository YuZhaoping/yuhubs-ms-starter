package com.yuhubs.ms.event;

import com.yuhubs.ms.ConfiguredTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class ConcurrentEventMulticasterTest extends ConfiguredTestBase {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private MockEventHandler mockEventHandler;


	@Test
	public void test() throws InterruptedException {
		final int count = 7;
		mockEventHandler.setCount(count);

		for (int i = 0; i < count; ++i) {
			applicationContext.publishEvent(new MockEvent(i));
		}

		mockEventHandler.waitForDone();
	}

}
