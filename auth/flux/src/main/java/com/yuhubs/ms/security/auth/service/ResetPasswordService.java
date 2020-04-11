package com.yuhubs.ms.security.auth.service;

import com.yuhubs.ms.security.auth.AccountChecker;
import com.yuhubs.ms.security.auth.AuthUser;
import com.yuhubs.ms.security.auth.web.dto.ConfirmPasswordDto;
import com.yuhubs.ms.security.auth.web.dto.ResetPasswordRequestDto;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public final class ResetPasswordService extends AuthServiceBase {

	ResetPasswordService(AuthServiceSupplier supplier) {
		super(supplier);
	}


	public Mono<Void> emitResetPassword(final ResetPasswordRequestDto dto)
			throws AuthenticationException {
		return authUserService().getUserByName(dto.getEmail())
				.switchIfEmpty(Mono.defer(() -> this.handleOnEmpty(dto)))
				.doOnNext(user -> AccountChecker.checkAccountStatus(user.getAccountStatus()))
				.doOnNext(user -> authEventPublisher().publishResetPasswordEvent(user, dto.getEmail()))
				.then();
	}

	private Mono<AuthUser> handleOnEmpty(ResetPasswordRequestDto dto)
			throws AuthenticationException {
		throw new UsernameNotFoundException("User not found by " + dto.getEmail());
	}


	public Mono<Map<String, ?>> getResetPasswordModel(final String token)
			throws AuthenticationException {
		return getUserByToken(token).flatMap(user -> this.getResetPasswordModel(user, token));
	}

	private Mono<Map<String, ?>> getResetPasswordModel(AuthUser user, String token) {
		Map<String, Object> model = new HashMap<>();

		model.put("username", user.getProfile().getName());

		String confirmUrl = authUrlsBuilder().buildResetPasswordUrl(user.getId(), token);
		model.put("confirmUrl", confirmUrl);

		model.put("token", token);

		return Mono.just(model);
	}


	public Mono<Void> confirmPassword(final String token, final ConfirmPasswordDto dto)
			throws AuthenticationException {
		return getUserByToken(token)
				.doOnNext(user -> resetUserPassword(user, dto))
				.then();
	}

	private void resetUserPassword(AuthUser user, ConfirmPasswordDto dto) {
		String passwordHash = authSecurityContext().passwordEncoder().encode(dto.getPassword());

		user.setPasswordHash(passwordHash);
	}

}
