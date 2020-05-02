package com.yuhubs.ms.redis;

import org.springframework.data.redis.support.atomic.RedisAtomicLong;

public final class RedisSequence {

	private final RedisAtomicLong atomicLong;


	RedisSequence(RedisAtomicLong atomicLong) {
		this.atomicLong = atomicLong;
	}


	public long nextVal() {
		return this.atomicLong.incrementAndGet();
	}

}
