package com.yuhubs.ms.redis;

import java.util.function.Supplier;

public class RedisTemplateSupplier<T> implements Supplier<T> {

	private final Supplier<T> supplier;

	private volatile T template;
	private final Object lock;


	public RedisTemplateSupplier(Supplier<T> supplier) {
		this.supplier = supplier;
		this.lock = new Object();
	}


	@Override
	public T get() {
		T result = this.template;
		if (result == null) {
			synchronized (this.lock) {
				result = this.template;
				if (result == null) {
					result = this.supplier.get();
					this.template = result;
				}
			}
		}
		return result;
	}

}
