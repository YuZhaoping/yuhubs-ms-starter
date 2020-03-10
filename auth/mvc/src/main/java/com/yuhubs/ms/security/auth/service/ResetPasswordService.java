package com.yuhubs.ms.security.auth.service;

import com.yuhubs.ms.security.auth.AccountChecker;
import com.yuhubs.ms.security.auth.AuthUser;
import com.yuhubs.ms.security.auth.web.dto.ConfirmPasswordDto;
import com.yuhubs.ms.security.auth.web.dto.ResetPasswordRequestDto;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class ResetPasswordService extends AuthServiceBase {

	ResetPasswordService(AuthServiceSupplier supplier) {
		super(supplier);
	}


	public void emitResetPassword(ResetPasswordRequestDto dto) throws AuthenticationException {
		Optional<AuthUser> userOp = authUserService().getUserByName(dto.getEmail());
		if (!userOp.isPresent()) {
			throw new UsernameNotFoundException("User not found by the email");
		}

		AuthUser user = userOp.get();

		try {
			AccountChecker.checkAccountStatus(user.getAccountStatus());
		} catch (AuthenticationException e) {
			throw e;
		}

		authEventPublisher().publishResetPasswordEvent(user, dto.getEmail());
	}


	public Map<String, ?> getResetPasswordModel(String token) throws AuthenticationException {
		AuthUser user = getUserByToken(token);

		return getResetPasswordModel(user, token);
	}

	private Map<String, ?> getResetPasswordModel(AuthUser user, String token) {
		Map<String, Object> model = new HashMap<>();

		model.put("username", user.getProfile().getName());

		String confirmUrl = authUrlsBuilder().buildResetPasswordUrl(user.getId(), token);
		model.put("confirmUrl", confirmUrl);

		model.put("token", token);

		return model;
	}


	public void confirmPassword(String token, ConfirmPasswordDto dto)
			throws AuthenticationException {
		AuthUser user = getUserByToken(token);

		String passwordHash = authSecurityContext().passwordEncoder().encode(dto.getPassword());

		user.setPasswordHash(passwordHash);
	}

}
