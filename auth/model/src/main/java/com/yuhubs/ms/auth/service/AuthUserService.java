package com.yuhubs.ms.auth.service;

import com.yuhubs.ms.security.auth.AuthUser;
import com.yuhubs.ms.security.auth.SignUpRequest;
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
