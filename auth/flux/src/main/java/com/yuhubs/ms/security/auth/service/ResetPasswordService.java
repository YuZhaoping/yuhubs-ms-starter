package com.yuhubs.ms.security.auth.service;

import com.yuhubs.ms.security.auth.AccountChecker;
import com.yuhubs.ms.security.auth.AuthUser;
import com.yuhubs.ms.security.auth.web.dto.ResetPasswordRequestDto;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Mono;

public final class ResetPasswordService extends AuthServiceBase {

	ResetPasswordService(AuthServiceSupplier supplier) {
		super(supplier);
	}


	public Mono<Void> emitResetPassword(ResetPasswordRequestDto dto) throws AuthenticationException {
		return authUserService().getUserByName(dto.getEmail())
				.switchIfEmpty(this.handleOnEmpty(dto))
				.doOnNext(user -> AccountChecker.checkAccountStatus(user.getAccountStatus()))
				.doOnNext(user -> authEventPublisher().publishResetPasswordEvent(user, dto.getEmail()))
				.then();
	}

	private Mono<AuthUser> handleOnEmpty(ResetPasswordRequestDto dto) throws AuthenticationException {
		throw new UsernameNotFoundException("User not found by " + dto.getEmail());
	}

}
