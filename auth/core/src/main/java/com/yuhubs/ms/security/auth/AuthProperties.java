package com.yuhubs.ms.security.auth;

import com.yuhubs.ms.security.SecurityProperties;
import org.springframework.core.env.Environment;

public final class AuthProperties {

	public static final String USER_ACCOUNT_INITIAL_STATUS = "yuhubs.ms.security.auth.user-account-initial-status";

	public static final String SIGN_UP_CONFIRM_LOGIN = "yuhubs.ms.security.auth.sign-up-confirm-login";


	private final SecurityProperties properties;


	private volatile int userAccountInitialStatus = -1;

	private volatile int signUpConfirmLogin = -1;


	AuthProperties(SecurityProperties properties) {
		this.properties = properties;
	}


	public Environment getEnvironment() {
		return this.properties.getEnvironment();
	}

	public SecurityProperties securityProperties() {
		return this.properties;
	}


	public int getUserAccountInitialStatus() {
		if (this.userAccountInitialStatus < 0) {
			this.userAccountInitialStatus = envUserAccountInitialStatus();
		}
		return this.userAccountInitialStatus;
	}

	public AuthProperties setUserAccountInitialStatus(int status) {
		this.userAccountInitialStatus = status;
		return this;
	}

	private int envUserAccountInitialStatus() {
		String status = getEnvironment().getProperty(USER_ACCOUNT_INITIAL_STATUS);
		if (status != null) {
			return Integer.parseInt(status);
		}
		return 0;
	}


	public boolean isSignUpConfirmLogin() {
		if (this.signUpConfirmLogin < 0) {
			setSignUpConfirmLogin(envSignUpConfirmLogin());
		}
		return this.signUpConfirmLogin > 0;
	}

	public AuthProperties setSignUpConfirmLogin(boolean enabled) {
		this.signUpConfirmLogin = enabled ? 1 : 0;
		return this;
	}

	private boolean envSignUpConfirmLogin() {
		String value = getEnvironment().getProperty(SIGN_UP_CONFIRM_LOGIN);
		if (value != null) {
			return "true".equalsIgnoreCase(value);
		}
		return false;
	}


	public int getJwtTokenExpiration() {
		return this.properties.getJwtTokenExpiration();
	}

	public AuthProperties setJwtTokenExpiration(int expiration) {
		this.properties.setJwtTokenExpiration(expiration);
		return this;
	}

}
