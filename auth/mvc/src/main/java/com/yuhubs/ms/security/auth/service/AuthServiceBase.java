package com.yuhubs.ms.security.auth.service;

import com.yuhubs.ms.security.auth.AuthSecurityContext;
import com.yuhubs.ms.security.auth.AuthUser;
import com.yuhubs.ms.security.auth.AuthUserService;
import com.yuhubs.ms.security.auth.event.AuthConfirmUrlsBuilder;
import com.yuhubs.ms.security.auth.event.AuthEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public abstract class AuthServiceBase {

	protected final AuthServiceSupplier supplier;


	public AuthServiceBase(AuthServiceSupplier supplier) {
		this.supplier = supplier;
	}


	protected final AuthUser getUserByToken(String token) throws AuthenticationException {
		Authentication authentication = authSecurityContext().jwtTokenService().parseJwtToken(token);

		Long userId = (Long) authentication.getPrincipal();

		Optional<AuthUser> userOp = authUserService().getUserById(userId);
		if (!userOp.isPresent()) {
			throw new UsernameNotFoundException("Invalid userId: " + userId);
		}

		return userOp.get();
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
