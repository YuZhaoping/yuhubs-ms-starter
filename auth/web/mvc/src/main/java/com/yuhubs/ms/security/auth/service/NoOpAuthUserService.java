package com.yuhubs.ms.security.auth.service;

import com.yuhubs.ms.auth.service.AuthUserService;
import com.yuhubs.ms.security.auth.AuthUser;
import com.yuhubs.ms.security.auth.SignUpRequest;
import com.yuhubs.ms.security.auth.exceptions.UsernameAlreadyExistsException;

import java.util.Optional;

public final class NoOpAuthUserService implements AuthUserService {

	private static final NoOpAuthUserService INSTANCE = new NoOpAuthUserService();

	public static AuthUserService getInstance() {
		return INSTANCE;
	}

	private NoOpAuthUserService() {
	}


	@Override
	public Optional<AuthUser> signUpUser(SignUpRequest request) throws UsernameAlreadyExistsException {
		return Optional.empty();
	}

	@Override
	public Optional<AuthUser> getUserById(Long userId) {
		return Optional.empty();
	}

	@Override
	public Optional<AuthUser> getUserByName(String username) {
		return Optional.empty();
	}

}
