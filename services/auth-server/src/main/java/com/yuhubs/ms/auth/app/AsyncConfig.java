package com.yuhubs.ms.auth.app;

import com.yuhubs.ms.event.ConcurrentEventMulticaster;
import com.yuhubs.ms.util.TaskExecutorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.support.TaskUtils;

@Configuration
@PropertySource(value = {"classpath:async.properties"})
@ConfigurationProperties(prefix = "task")
@EnableAsync
public class AsyncConfig extends TaskExecutorBuilder.Support {

	@Bean(name = "taskExecutor")
	public TaskExecutor taskExecutor() {
		return createTaskExecutorBuilder().forIOTasks().build();
	}

	@Bean
	@Autowired
	public ApplicationEventMulticaster applicationEventMulticaster(TaskExecutor taskExecutor) {
		ConcurrentEventMulticaster eventMulticaster = new ConcurrentEventMulticaster(taskExecutor);

		eventMulticaster.setErrorHandler(TaskUtils.LOG_AND_SUPPRESS_ERROR_HANDLER);

		return eventMulticaster;
	}

}
