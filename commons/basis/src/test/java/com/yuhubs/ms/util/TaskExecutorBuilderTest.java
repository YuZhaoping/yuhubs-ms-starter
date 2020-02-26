package com.yuhubs.ms.util;

import com.yuhubs.ms.TaskExecutorConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = {
		TaskExecutorConfig.class
})
@EnableConfigurationProperties
public class TaskExecutorBuilderTest {

	@Autowired
	private TaskExecutorConfig taskExecutorConfig;

	@Resource(name = "defaultTaskExecutor")
	private TaskExecutor taskExecutor;


	@Test
	public void testProperties() {
		Properties props = taskExecutorConfig.getExecution();

		assertEquals("60", props.getProperty(TaskExecutorBuilder.POOL_KEEP_ALIVE));
		assertEquals("ms-task-", props.getProperty(TaskExecutorBuilder.THREAD_NAME_PREFIX));

		assertNull(props.getProperty(TaskExecutorBuilder.POOL_MAX_SIZE));
	}

	@Test
	public void testBuild() {
		TaskExecutorBuilder builder = taskExecutorConfig.createTaskExecutorBuilder();

		ThreadPoolTaskExecutor taskExecutor = builder.build();

		assertEquals(60, taskExecutor.getKeepAliveSeconds());
		assertEquals("ms-task-", taskExecutor.getThreadNamePrefix());
	}

	@Test
	public void testExecutor() throws InterruptedException {
		final boolean[] ran = {false};
		final CountDownLatch doneSignal = new CountDownLatch(1);

		taskExecutor.execute(() -> {
			ran[0] = true;
			doneSignal.countDown();
		});

		doneSignal.await();
		assertTrue(ran[0]);
	}

}
