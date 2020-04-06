package com.yuhubs.ms.security.auth.service;

import com.yuhubs.ms.security.auth.AccountChecker;
import com.yuhubs.ms.security.auth.AuthUser;
import com.yuhubs.ms.security.auth.AuthUserAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Mono;

public final class RefreshTokenService extends AuthServiceBase {

	RefreshTokenService(AuthServiceSupplier supplier) {
		super(supplier);
	}


	public Mono<AuthUserAuthentication> refreshToken(Authentication tokenAuth)
			throws AuthenticationException {
		final Long userId = (Long)tokenAuth.getPrincipal();

		return authUserService().getUserById(userId)
				.switchIfEmpty(this.handleOnEmpty(userId))
				.doOnNext(user -> AccountChecker.checkAccountStatus(user.getAccountStatus()))
				.flatMap(user -> Mono.just(AuthUserAuthentication.of(user)));
	}

	private Mono<AuthUser> handleOnEmpty(Long userId) {
		throw new UsernameNotFoundException("Invalid userId: " + userId);
	}

}
