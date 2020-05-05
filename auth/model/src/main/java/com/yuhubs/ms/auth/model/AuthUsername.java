package com.yuhubs.ms.auth.model;

import java.util.Objects;

public final class AuthUsername {

	public enum Type {
		UNKNOWN,
		NAME,
		EMAIL
	}


	private final String value;

	private final Type type;


	private AuthUsername(String value, Type type) {
		this.value = value;
		this.type = type;
	}


	public static AuthUsername ofName(String name) {
		return new AuthUsername(name, Type.NAME);
	}

	public static AuthUsername ofEmail(String email) {
		return new AuthUsername(email, Type.EMAIL);
	}

	public static AuthUsername of(String str) {
		return new AuthUsername(str, Type.UNKNOWN);
	}


	public String value() {
		return this.value;
	}

	public Type type() {
		return this.type;
	}

	public boolean isTypeUnknown() {
		return this.type == Type.UNKNOWN;
	}

	public boolean isEmail() {
		return this.type == Type.EMAIL;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		AuthUsername that = (AuthUsername) o;
		return Objects.equals(value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

}
