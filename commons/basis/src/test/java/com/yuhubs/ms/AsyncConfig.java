package com.yuhubs.ms;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;

import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class AsyncConfig {

	@Bean(name = "concurrentTaskExecutor")
	public TaskExecutor concurrentTaskExecutor() {
		return new ConcurrentTaskExecutor(
				Executors.newFixedThreadPool(3));
	}

}
