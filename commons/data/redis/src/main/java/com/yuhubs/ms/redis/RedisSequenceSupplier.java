package com.yuhubs.ms.redis;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import java.util.function.Supplier;

public final class RedisSequenceSupplier implements Supplier<RedisSequence> {

	private final Helper helper;

	private volatile RedisSequence sequence;
	private final Object lock;


	RedisSequenceSupplier(RedisConnectionFactory factory, String seqName, long initValue) {
		this.helper = new Helper(factory, seqName, initValue);
		this.lock = new Object();
	}


	@Override
	public RedisSequence get() {
		RedisSequence result = this.sequence;
		if (result == null) {
			synchronized (this.lock) {
				result = this.sequence;
				if (result == null) {
					result = this.helper.createSequence();
					this.sequence = result;
				}
			}
		}
		return result;
	}


	private static final class Helper {

		private final RedisConnectionFactory factory;

		private final String seqName;

		private final long initValue;


		Helper(RedisConnectionFactory factory, String seqName, long initValue) {
			this.factory = factory;
			this.seqName = seqName;
			this.initValue = initValue;
		}


		RedisSequence createSequence() {
			RedisAtomicLong atomicLong = new RedisAtomicLong(seqName, factory);

			atomicLong.compareAndSet(0L, initValue);

			return new RedisSequence(atomicLong);
		}

	}

}
