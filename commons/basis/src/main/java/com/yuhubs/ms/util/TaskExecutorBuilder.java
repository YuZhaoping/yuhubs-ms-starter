package com.yuhubs.ms.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Properties;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;

public final class TaskExecutorBuilder {

	public static final String POOL_ALLOW_CORE_THREAD_TIMEOUT = "pool.allow-core-thread-timeout";
	public static final String POOL_CORE_SIZE = "pool.core-size";
	public static final String POOL_QUEUE_CAPACITY = "pool.queue-capacity";
	public static final String POOL_MAX_SIZE = "pool.max-size";
	public static final String POOL_KEEP_ALIVE = "pool.keep-alive";

	public static final String SHUTDOWN_AWAIT_TERMINATION = "shutdown.await-termination";
	public static final String SHUTDOWN_AWAIT_TERMINATION_PERIOD = "shutdown.await-termination-period";

	public static final String THREAD_NAME_PREFIX = "thread-name-prefix";


	public interface ExecutorFactory {
		ThreadPoolTaskExecutor newThreadPoolTaskExecutor();
	}


	@Configuration
	public static class Support {

		private Properties execution;


		public Support() {
			this.execution = new Properties();
		}


		public final TaskExecutorBuilder createTaskExecutorBuilder() {
			return createTaskExecutorBuilder(null);
		}

		public final TaskExecutorBuilder createTaskExecutorBuilder(ExecutorFactory factory) {
			TaskExecutorBuilder builder = new TaskExecutorBuilder(factory);

			builder.init(this.execution);

			return builder;
		}

		public Properties getExecution() {
			return execution;
		}

		public void setExecution(Properties execution) {
			this.execution = execution;
		}

	}


	private final ThreadPoolTaskExecutor taskExecutor;

	private int queueCapacity;
	private boolean isCorePoolSizeSetted;


	public TaskExecutorBuilder() {
		this(null);
	}

	public TaskExecutorBuilder(ExecutorFactory factory) {
		this.taskExecutor = (factory != null) ?
				factory.newThreadPoolTaskExecutor() : new ThreadPoolTaskExecutor();

		this.queueCapacity = Integer.MAX_VALUE;
		this.isCorePoolSizeSetted = false;

		init();
	}

	private void init() {
		int processors = Runtime.getRuntime().availableProcessors();

		this.taskExecutor.setAllowCoreThreadTimeOut(true);
		this.taskExecutor.setCorePoolSize(processors);
		this.taskExecutor.setKeepAliveSeconds(60);
		this.taskExecutor.setWaitForTasksToCompleteOnShutdown(false);
	}


	public TaskExecutorBuilder forCPUTasks() {
		if (this.isCorePoolSizeSetted) {
			return this;
		}
		int processors = Runtime.getRuntime().availableProcessors();
		this.taskExecutor.setCorePoolSize(processors + 1);
		return this;
	}

	public TaskExecutorBuilder forIOTasks() {
		if (this.isCorePoolSizeSetted) {
			return this;
		}
		int processors = Runtime.getRuntime().availableProcessors();
		processors <<= 1;
		this.taskExecutor.setCorePoolSize(processors + 1);
		return this;
	}


	public TaskExecutorBuilder setAllowCoreThreadTimeOut(boolean allowCoreThreadTimeOut) {
		this.taskExecutor.setAllowCoreThreadTimeOut(allowCoreThreadTimeOut);
		return this;
	}

	public TaskExecutorBuilder setCorePoolSize(int corePoolSize) {
		if (corePoolSize > 0) {
			this.taskExecutor.setCorePoolSize(corePoolSize);
			this.isCorePoolSizeSetted = true;
		}
		return this;
	}

	public TaskExecutorBuilder setQueueCapacity(int queueCapacity) {
		this.queueCapacity = queueCapacity;
		this.taskExecutor.setQueueCapacity(queueCapacity);
		return this;
	}

	public TaskExecutorBuilder setMaxPoolSize(int maxPoolSize) {
		this.taskExecutor.setMaxPoolSize(maxPoolSize);
		return this;
	}

	public TaskExecutorBuilder setKeepAliveSeconds(int keepAliveSeconds) {
		this.taskExecutor.setKeepAliveSeconds(keepAliveSeconds);
		return this;
	}

	public TaskExecutorBuilder setWaitForTasksToCompleteOnShutdown(boolean waitForJobsToCompleteOnShutdown) {
		this.taskExecutor.setWaitForTasksToCompleteOnShutdown(waitForJobsToCompleteOnShutdown);
		return this;
	}

	public TaskExecutorBuilder setAwaitTerminationSeconds(int awaitTerminationSeconds) {
		this.taskExecutor.setAwaitTerminationSeconds(awaitTerminationSeconds);
		return this;
	}

	public TaskExecutorBuilder setThreadNamePrefix(String threadNamePrefix) {
		this.taskExecutor.setThreadNamePrefix(threadNamePrefix);
		return this;
	}

	public TaskExecutorBuilder setRejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler) {
		this.taskExecutor.setRejectedExecutionHandler(rejectedExecutionHandler);
		return this;
	}

	public TaskExecutorBuilder setThreadFactory(ThreadFactory threadFactory) {
		this.taskExecutor.setThreadFactory(threadFactory);
		return this;
	}


	public ThreadPoolTaskExecutor build() {
		return build(false);
	}

	public ThreadPoolTaskExecutor build(boolean withInitialize) {
		final int corePoolSize = this.taskExecutor.getCorePoolSize();
		int maxPoolSize = this.taskExecutor.getMaxPoolSize();

		if (maxPoolSize < corePoolSize) {
			maxPoolSize = corePoolSize << 2;
			this.taskExecutor.setMaxPoolSize(maxPoolSize);
		}

		if (maxPoolSize > corePoolSize) {
			if (this.queueCapacity == Integer.MAX_VALUE) {
				this.taskExecutor.setQueueCapacity(corePoolSize << 1);
			}
		}

		if (withInitialize) {
			this.taskExecutor.initialize();
		}

		return this.taskExecutor;
	}


	private void init(Properties props) {
		String value;

		value = props.getProperty(POOL_ALLOW_CORE_THREAD_TIMEOUT);
		if (value != null) {
			setAllowCoreThreadTimeOut(Boolean.parseBoolean(value));
		}

		value = props.getProperty(POOL_CORE_SIZE);
		if (value != null) {
			setCorePoolSize(Integer.parseInt(value));
		}

		value = props.getProperty(POOL_QUEUE_CAPACITY);
		if (value != null) {
			setQueueCapacity(Integer.parseInt(value));
		}

		value = props.getProperty(POOL_MAX_SIZE);
		if (value != null) {
			int maxPoolSize = Integer.parseInt(value);
			int corePoolSize = this.taskExecutor.getCorePoolSize();
			if (maxPoolSize < corePoolSize) {
				maxPoolSize = corePoolSize << 2;
			}
			setMaxPoolSize(maxPoolSize);
		}

		value = props.getProperty(POOL_KEEP_ALIVE);
		if (value != null) {
			setKeepAliveSeconds(Integer.parseInt(value));
		}

		value = props.getProperty(SHUTDOWN_AWAIT_TERMINATION);
		if (value != null) {
			setWaitForTasksToCompleteOnShutdown(Boolean.parseBoolean(value));
		}

		value = props.getProperty(SHUTDOWN_AWAIT_TERMINATION_PERIOD);
		if (value != null) {
			setAwaitTerminationSeconds(Integer.parseInt(value));
		}

		value = props.getProperty(THREAD_NAME_PREFIX);
		if (value != null) {
			setThreadNamePrefix(value);
		}

	}

}
