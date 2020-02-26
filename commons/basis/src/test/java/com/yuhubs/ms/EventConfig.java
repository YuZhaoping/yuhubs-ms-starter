package com.yuhubs.ms;

import com.yuhubs.ms.event.ConcurrentEventMulticaster;
import com.yuhubs.ms.event.MockEvent;
import com.yuhubs.ms.event.MockEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.EventListener;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.support.TaskUtils;

@Configuration
public class EventConfig {

	private final MockEventHandler mockEventHandler = new MockEventHandler();

	@Bean
	@Autowired(required = false)
	public ApplicationEventMulticaster applicationEventMulticaster(TaskExecutor executor) {
		if (executor == null) {
			executor = new SimpleAsyncTaskExecutor();
		}

		ConcurrentEventMulticaster eventMulticaster = new ConcurrentEventMulticaster(executor);

		eventMulticaster.setErrorHandler(TaskUtils.LOG_AND_SUPPRESS_ERROR_HANDLER);

		return eventMulticaster;
	}

	@Bean
	public MockEventHandler mockEventHandler() {
		return this.mockEventHandler;
	}


	@EventListener
	public void onMockEvent(MockEvent event) {
		this.mockEventHandler.handleEvent(event);
	}

}
