package com.yuhubs.ms.auth;

import com.yuhubs.ms.event.ConcurrentEventMulticaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.support.TaskUtils;

@Configuration
@EnableAsync
public class AsyncConfig {

	@Bean
	public TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}

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

}
