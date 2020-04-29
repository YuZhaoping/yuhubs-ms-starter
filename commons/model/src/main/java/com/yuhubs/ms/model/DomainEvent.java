package com.yuhubs.ms.model;

public interface DomainEvent<T> {

	boolean sameEventAs(T other);

}
