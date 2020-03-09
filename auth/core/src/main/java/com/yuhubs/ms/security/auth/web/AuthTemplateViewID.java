package com.yuhubs.ms.security.auth.web;

public enum AuthTemplateViewID {

	VERIFY_EMAIL_DONE("verify-email-done"),
	VERIFY_EMAIL_FAIL("verify-email-fail"),

	RESET_PASSWORD_VIEW("reset-password-view"),
	RESET_PASSWORD_DONE("reset-password-done"),
	RESET_PASSWORD_FAIL("reset-password-fail");


	private final String id;


	AuthTemplateViewID(String id) {
		this.id = id;
	}


	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return id;
	}

}
