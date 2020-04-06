package com.yuhubs.ms.security.auth.service;

import com.yuhubs.ms.security.auth.*;
import com.yuhubs.ms.security.auth.exceptions.EmailNotVerifiedException;
import com.yuhubs.ms.security.auth.web.dto.SignUpRequestDto;
import org.springframework.security.core.AuthenticationException;
import reactor.core.publisher.Mono;

public final class SignUpService extends AuthServiceBase {

	SignUpService(AuthServiceSupplier supplier) {
		super(supplier);
	}


	public Mono<AuthUserAuthentication> signUp(SignUpRequestDto dto)
			throws AuthenticationException {
		final AuthProperties properties = authSecurityContext().authProperties();

		final String passwordHash = authSecurityContext().passwordEncoder().encode(dto.getPassword());

		final SignUpRequest signUpReq = createSignUpRequest(dto).setPassword(passwordHash);
		signUpReq.setStatus(properties.getUserAccountInitialStatus());

		return authUserService()
				.signUpUser(signUpReq)
				.doOnNext(user -> checkAccountStatus(user, signUpReq))
				.flatMap(user -> {
					if (properties.isSignUpConfirmLogin()) {
						return Mono.empty();
					}
					return Mono.just(AuthUserAuthentication.of(user));
				});
	}


	public Mono<Void> confirmEmail(String token) throws AuthenticationException {
		return getUserByToken(token)
				.doOnNext(user -> user.setAccountStatus(AccountStatus.Op.SET_EMAIL_VERIFIED))
				.then();
	}


	private void checkAccountStatus(AuthUser user, SignUpRequest signUpReq)
			throws AuthenticationException {
		try {
			AccountChecker.checkAccountStatus(user.getAccountStatus());
		} catch (EmailNotVerifiedException e) {

			authEventPublisher().publishVerifyEmailEvent(user, signUpReq.getEmail());

			throw e;
		} catch (AuthenticationException e) {
			throw e;
		}
	}

	private static SignUpRequest createSignUpRequest(SignUpRequestDto dto) {
		SignUpRequest req = new SignUpRequest();

		req.setEmail(dto.getEmail()).setUsername(dto.getUsername());

		return req;
	}

}
