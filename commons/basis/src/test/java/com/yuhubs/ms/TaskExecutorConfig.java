package com.yuhubs.ms;

import com.yuhubs.ms.util.TaskExecutorBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.task.TaskExecutor;

@Configuration
@PropertySource(value = {"classpath:async.properties"})
@ConfigurationProperties(prefix = "task")
public class TaskExecutorConfig extends TaskExecutorBuilder.Support {

	@Bean(name = "defaultTaskExecutor")
	public TaskExecutor defaultTaskExecutor() {
		return createTaskExecutorBuilder().forIOTasks().build();
	}

}
