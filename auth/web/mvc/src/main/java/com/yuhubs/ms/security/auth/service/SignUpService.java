package com.yuhubs.ms.security.auth.service;

import com.yuhubs.ms.security.auth.*;
import com.yuhubs.ms.security.auth.exceptions.EmailNotVerifiedException;
import com.yuhubs.ms.security.auth.web.dto.SignUpRequestDto;
import org.springframework.security.core.AuthenticationException;

import java.util.Optional;

public final class SignUpService extends AuthServiceBase {

	SignUpService(AuthServiceSupplier supplier) {
		super(supplier);
	}


	public Optional<AuthUserAuthentication> signUp(SignUpRequestDto dto)
			throws AuthenticationException {
		final AuthProperties properties = authSecurityContext().authProperties();

		String passwordHash = authSecurityContext().passwordEncoder().encode(dto.getPassword());

		SignUpRequest signUpReq = createSignUpRequest(dto).setPassword(passwordHash);
		signUpReq.setStatus(properties.getUserAccountInitialStatus());


		Optional<AuthUser> userOp = authUserService().signUpUser(signUpReq);
		if (!userOp.isPresent()) {
			return Optional.empty();
		}

		AuthUser user = userOp.get();

		try {
			AccountChecker.checkAccountStatus(user.getAccountStatus());
		} catch (EmailNotVerifiedException e) {

			authEventPublisher().publishVerifyEmailEvent(user, signUpReq.getEmail());

			throw e;
		} catch (AuthenticationException e) {
			throw e;
		}

		if (properties.isSignUpConfirmLogin()) {
			return Optional.empty();
		}

		AuthUserAuthentication authentication = AuthUserAuthentication.of(user);

		return Optional.of(authentication);
	}

	public void confirmEmail(String token) throws AuthenticationException {
		AuthUser user = getUserByToken(token);

		user.setAccountStatus(AccountStatus.Op.SET_EMAIL_VERIFIED);
	}


	private static SignUpRequest createSignUpRequest(SignUpRequestDto dto) {
		SignUpRequest req = new SignUpRequest();

		req.setEmail(dto.getEmail()).setUsername(dto.getUsername());

		return req;
	}

}
