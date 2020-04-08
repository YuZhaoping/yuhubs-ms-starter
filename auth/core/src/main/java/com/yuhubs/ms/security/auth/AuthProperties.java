package com.yuhubs.ms.security.auth;

import com.yuhubs.ms.security.SecurityProperties;
import org.springframework.core.env.Environment;

public final class AuthProperties {

	public static final String USER_ACCOUNT_INITIAL_STATUS = "yuhubs.ms.security.auth.user-account-initial-status";

	public static final String SIGN_UP_CONFIRM_LOGIN = "yuhubs.ms.security.auth.sign-up-confirm-login";


	private final SecurityProperties properties;

	private volatile int userAccountInitialStatus = -1;


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

	public void setUserAccountInitialStatus(int status) {
		this.userAccountInitialStatus = status;
	}

	private int envUserAccountInitialStatus() {
		String status = getEnvironment().getProperty(USER_ACCOUNT_INITIAL_STATUS);
		if (status != null) {
			return Integer.parseInt(status);
		}
		return 0;
	}


	public boolean isSignUpConfirmLogin() {
		String value = getEnvironment().getProperty(SIGN_UP_CONFIRM_LOGIN);
		if (value != null) {
			return "true".equalsIgnoreCase(value);
		}
		return false;
	}


	public int getJwtTokenExpiration() {
		return this.properties.getJwtTokenExpiration();
	}

	public void setJwtTokenExpiration(int expiration) {
		this.properties.setJwtTokenExpiration(expiration);
	}

}
