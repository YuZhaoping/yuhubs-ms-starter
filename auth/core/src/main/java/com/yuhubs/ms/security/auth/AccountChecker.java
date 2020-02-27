package com.yuhubs.ms.security.auth;

import com.yuhubs.ms.security.auth.exceptions.*;

public final class AccountChecker {


	private AccountChecker() {
	}


	public static void checkAccountStatus(AccountStatus status)
			throws AccountExpiredException,
			AccountLockedException,
			CredentialsExpiredException,
			EmailNotVerifiedException,
			AccountDisabledException {

		if (!status.isEnabled()) {
			if (!status.isAccountNonExpired()) {
				throw new AccountExpiredException("Account expired");
			}
			if (!status.isAccountNonLocked()) {
				throw new AccountLockedException("Account locked");
			}
			if (!status.isCredentialsNonExpired()) {
				throw new CredentialsExpiredException("Password expired");
			}

			if (!status.isEmailVerified()) {
				throw new EmailNotVerifiedException("Email not verified");
			}

			throw new AccountDisabledException("Account disabled");
		}
	}

}
