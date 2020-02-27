package com.yuhubs.ms.security.auth;

public class AccountStatus {

	public enum Op {
		SET_ACCOUNT_EXPIRED,
		UNSET_ACCOUNT_EXPIRED,

		SET_ACCOUNT_LOCKED,
		UNSET_ACCOUNT_LOCKED,

		SET_CREDENTIALS_EXPIRED,
		UNSET_CREDENTIALS_EXPIRED,

		SET_ACCOUNT_DISABLED,
		UNSET_ACCOUNT_DISABLED,

		SET_EMAIL_VERIFIED,
		UNSET_EMAIL_VERIFIED
	}


	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

	public boolean isEmailVerified() {
		return true;
	}

}
