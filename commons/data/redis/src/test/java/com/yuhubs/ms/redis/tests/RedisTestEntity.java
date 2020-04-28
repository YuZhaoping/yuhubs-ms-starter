package com.yuhubs.ms.redis.tests;

import java.io.Serializable;

public class RedisTestEntity implements Serializable {

	private static final long serialVersionUID = 4530493508318682155L;


	private String name;

	private long count;


	public RedisTestEntity() {
	}

	public RedisTestEntity(String name, long count) {
		this.name = name;
		this.count = count;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (null == obj) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}

		RedisTestEntity that = (RedisTestEntity)obj;

		if (this.name == null && that.name != null) {
			return false;
		} else if (!this.name.equals(that.name)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;

		result = prime * result +
				((this.name == null) ? 0 : this.name.hashCode());

		return result;
	}


	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public long getCount() {
		return count;
	}

}
