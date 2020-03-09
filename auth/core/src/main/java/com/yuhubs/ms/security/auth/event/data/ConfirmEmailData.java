package com.yuhubs.ms.security.auth.event.data;

public class ConfirmEmailData extends EmailAddressData {

	public enum Type {
		VERIFY_EMAIL,
		RESET_PASSWORD
	}


	private final Type type;

	private String username;

	private String confirmUrl;

	private String token;


	public ConfirmEmailData(String email, Type type) {
		super(email);
		this.type = type;
	}


	public final Type getType() {
		return type;
	}

	public String getUsername() {
		return username;
	}

	public ConfirmEmailData setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getConfirmUrl() {
		return confirmUrl;
	}

	public ConfirmEmailData setConfirmUrl(String confirmUrl) {
		this.confirmUrl = confirmUrl;
		return this;
	}

	public String getToken() {
		return token;
	}

	public ConfirmEmailData setToken(String token) {
		this.token = token;
		return this;
	}

}
