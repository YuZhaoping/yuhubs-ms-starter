package com.yuhubs.ms.security.auth;

import com.yuhubs.ms.security.auth.exceptions.UserAlreadyExistsException;

import java.util.Optional;

public interface AuthUserService {

	AuthUser signUpUser(SignUpRequest request) throws UserAlreadyExistsException;

	Optional<AuthUser> getUserById(Long userId);

	Optional<AuthUser> getUserByName(String emailOrUsername);

}
