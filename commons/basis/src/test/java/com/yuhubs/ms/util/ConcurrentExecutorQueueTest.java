package com.yuhubs.ms.util;

import org.junit.Test;

import java.util.concurrent.*;

import static org.junit.Assert.assertTrue;

public class ConcurrentExecutorQueueTest {

	private static final class Event {

		private final int id;

		Event(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}

	}


	private static final class EventHandler implements ConcurrentExecutorQueue.Handler<Event> {

		private final CountDownLatch doneSignal;


		EventHandler(int count) {
			this.doneSignal = new CountDownLatch(count);
		}


		@Override
		public void executeHandle(Event event) {
			this.doneSignal.countDown();
		}

		public void waitForDone() throws InterruptedException {
			this.doneSignal.await();
		}

	}


	private static final class DirectExecutor implements Executor {
		public void execute(Runnable r) {
			r.run();
		}
	}

	private static final class ThreadPerTaskExecutor implements Executor {
		public void execute(Runnable r) {
			new Thread(r).start();
		}
	}


	@Test
	public void testDirectExecutor() throws InterruptedException {
		DirectExecutor executor = new DirectExecutor();

		doTest(executor);
	}

	@Test
	public void testThreadPerTaskExecutor() throws InterruptedException {
		ThreadPerTaskExecutor executor = new ThreadPerTaskExecutor();

		doTest(executor);
	}

	@Test
	public void testFixedThreadPool1() throws InterruptedException {
		doTestFixedThreadPool(1);
	}

	@Test
	public void testFixedThreadPool2() throws InterruptedException {
		doTestFixedThreadPool(2);
	}

	@Test
	public void testCachedThreadPool() throws InterruptedException {
		ExecutorService pool = Executors.newCachedThreadPool();

		try {
			doTest(pool);
		} finally {
			pool.shutdown();
		}
	}

	@Test
	public void testScheduledThreadPool1() throws InterruptedException {
		doTestScheduledThreadPool(1);
	}

	@Test
	public void testScheduledThreadPool2() throws InterruptedException {
		doTestScheduledThreadPool(2);
	}

	@Test
	public void testSingleThreadExecutor() throws InterruptedException {
		ExecutorService pool = Executors.newSingleThreadExecutor();

		try {
			doTest(pool);
		} finally {
			pool.shutdown();
		}
	}


	private void doTestFixedThreadPool(int poolSize) throws InterruptedException {
		ExecutorService pool = Executors.newFixedThreadPool(poolSize);

		try {
			doTest(pool);
		} finally {
			pool.shutdown();
		}
	}

	private void doTestScheduledThreadPool(int corePoolSize) throws InterruptedException {
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(corePoolSize);

		try {
			doTest(pool);
		} finally {
			pool.shutdown();
		}
	}

	private void doTest(Executor executor) throws InterruptedException {
		final int count = 7;

		EventHandler handler = new EventHandler(count);

		ConcurrentExecutorQueue<Event> queue = new ConcurrentExecutorQueue<>(executor, handler);

		for (int i = 0; i < count; ++i) {
			Event event = new Event(i);

			queue.offer(event);
		}

		handler.waitForDone();

		assertTrue(queue.isEmpty());
	}

}
