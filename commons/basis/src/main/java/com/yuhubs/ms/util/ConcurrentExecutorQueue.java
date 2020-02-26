package com.yuhubs.ms.util;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

public final class ConcurrentExecutorQueue<E> {

	public interface Handler<E> {
		void executeHandle(E e);
	}


	private final ConcurrentLinkedQueue<E> queue;
	private final AtomicInteger size;

	private final Executor executor;
	private final Handler<E> handler;

	private final Worker<E> worker;


	public ConcurrentExecutorQueue(Executor executor, Handler<E> handler) {
		this.queue = new ConcurrentLinkedQueue<>();
		this.size = new AtomicInteger();

		this.executor = executor;
		this.handler = handler;

		this.worker = new Worker<>(this);
	}


	private static final class Worker<E> implements Runnable {

		private final ConcurrentExecutorQueue<E> ceq;

		private final AtomicInteger counter;


		private Worker(ConcurrentExecutorQueue ceq) {
			this.ceq = ceq;
			this.counter = new AtomicInteger();
		}


		private void activate() {
			spawn(0);
		}

		private void spawn(final int id) {
			int next = id + 1;
			if (this.counter.compareAndSet(id, next)) {
				this.ceq.executor.execute(this);
			}
		}

		@Override
		public void run() {
			int id = this.counter.get();
			for (;;) {
				try {
					doWork(id);
				} finally {
					int value = this.counter.decrementAndGet();

					if (value == 0 && !this.ceq.isEmpty()) {
						id = 1;
						if (this.counter.compareAndSet(0, id)) {
							continue;
						}
					}
				}

				break;
			}
		}

		private void doWork(int id) {
			for (;;) {
				E e = this.ceq.poll();

				if (e != null) {
					try {
						this.ceq.handler.executeHandle(e);
					} finally {
						id = moreWorker(id);
					}

					continue;
				}

				break;
			}
		}

		private int moreWorker(int id) {
			int workers = this.counter.get();
			if (id == workers) {
				int tasks = this.ceq.size();
				if (tasks > (workers << 1)) {
					spawn(id);
				}
			} else if (id > workers) {
				id = workers;
			}
			return id;
		}

	}


	public boolean offer(E e) {
		this.queue.offer(e);
		this.size.incrementAndGet();

		this.worker.activate();

		return true;
	}

	public boolean isEmpty() {
		return this.queue.isEmpty();
	}

	public int size() {
		return this.size.get();
	}


	private E poll() {
		E e = this.queue.poll();

		if (e != null) {
			this.size.decrementAndGet();
		}

		return e;
	}

}
