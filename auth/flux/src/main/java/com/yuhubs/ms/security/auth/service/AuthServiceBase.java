package com.yuhubs.ms.security.auth.service;

import com.yuhubs.ms.auth.reactive.AuthUserService;
import com.yuhubs.ms.security.auth.AuthSecurityContext;
import com.yuhubs.ms.security.auth.AuthUser;
import com.yuhubs.ms.security.auth.event.AuthConfirmUrlsBuilder;
import com.yuhubs.ms.security.auth.event.AuthEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Mono;

public abstract class AuthServiceBase {

	protected final AuthServiceSupplier supplier;


	public AuthServiceBase(AuthServiceSupplier supplier) {
		this.supplier = supplier;
	}


	protected final Mono<AuthUser> getUserByToken(String token) throws AuthenticationException {
		final Authentication authentication = authSecurityContext().jwtTokenService().parseJwtToken(token);

		final Long userId = (Long) authentication.getPrincipal();

		return authUserService().getUserById(userId)
				.switchIfEmpty(Mono.defer(() -> this.handleOnEmpty(userId)));
	}

	private final Mono<AuthUser> handleOnEmpty(Long userId) throws AuthenticationException {
		throw new UsernameNotFoundException("Invalid userId: " + userId);
	}


	protected final AuthSecurityContext authSecurityContext() {
		return this.supplier.authSecurityContext();
	}

	protected final AuthUserService authUserService() {
		return this.supplier.authUserService();
	}

	protected final AuthEventPublisher authEventPublisher() {
		return this.supplier.authEventPublisher();
	}

	protected final AuthConfirmUrlsBuilder authUrlsBuilder() {
		return authEventPublisher().authConfirmUrlsBuilder();
	}

}
