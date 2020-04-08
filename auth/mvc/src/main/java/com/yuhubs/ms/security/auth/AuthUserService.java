package com.yuhubs.ms.security.auth;

import com.yuhubs.ms.security.auth.exceptions.UserAlreadyExistsException;

import java.util.Optional;

public interface AuthUserService {

	interface Provider {
		AuthUserService authUserService();
	}

	Optional<AuthUser> signUpUser(SignUpRequest request) throws UserAlreadyExistsException;

	Optional<AuthUser> getUserById(Long userId);

	Optional<AuthUser> getUserByName(String username);

}
