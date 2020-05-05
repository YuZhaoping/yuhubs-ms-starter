package com.yuhubs.ms.auth.service;

import com.yuhubs.ms.auth.model.AuthUserUpdatedValuesMark;
import com.yuhubs.ms.auth.model.AuthUsername;
import com.yuhubs.ms.security.auth.AuthUser;
import com.yuhubs.ms.security.auth.SignUpRequest;
import com.yuhubs.ms.security.auth.exceptions.UsernameAlreadyExistsException;

import java.util.Optional;

public interface AuthUserService {

	interface Provider {
		AuthUserService authUserService();
	}

	Optional<AuthUser> signUpUser(SignUpRequest request) throws UsernameAlreadyExistsException;

	Optional<AuthUser> getUserById(Long userId);

	Optional<AuthUser> getUserByName(AuthUsername username);

	AuthUser updateUsername(AuthUser user, AuthUsername username) throws UsernameAlreadyExistsException;

	AuthUser updateUser(AuthUser user, AuthUserUpdatedValuesMark mark);

	boolean deleteUser(AuthUser user);

}
