package com.yuhubs.ms.security.auth.service;

import com.yuhubs.ms.security.auth.AccountChecker;
import com.yuhubs.ms.security.auth.AuthUser;
import com.yuhubs.ms.security.auth.AuthUserAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public final class RefreshTokenService extends AuthServiceBase {

	RefreshTokenService(AuthServiceSupplier supplier) {
		super(supplier);
	}


	public AuthUserAuthentication refreshToken(Authentication tokenAuth)
			throws AuthenticationException {
		Long userId = (Long)tokenAuth.getPrincipal();

		Optional<AuthUser> userOp = authUserService().getUserById(userId);
		if (!userOp.isPresent()) {
			throw new UsernameNotFoundException("Invalid userId: " + userId);
		}

		AuthUser user = userOp.get();

		try {
			AccountChecker.checkAccountStatus(user.getAccountStatus());
		} catch (AuthenticationException e) {
			throw e;
		}

		AuthUserAuthentication authentication = AuthUserAuthentication.of(user);

		return authentication;
	}

}
