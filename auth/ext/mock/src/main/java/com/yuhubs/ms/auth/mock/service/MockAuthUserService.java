package com.yuhubs.ms.auth.mock.service;

import com.yuhubs.ms.auth.mock.MockUserManager;
import com.yuhubs.ms.auth.model.AuthUserUpdatedValuesMark;
import com.yuhubs.ms.auth.model.AuthUsername;
import com.yuhubs.ms.auth.service.AuthUserService;
import com.yuhubs.ms.security.auth.AuthUser;
import com.yuhubs.ms.security.auth.SignUpRequest;
import com.yuhubs.ms.security.auth.exceptions.UsernameAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("AuthUserService")
public class MockAuthUserService implements AuthUserService {

	private final MockUserManager userManager;


	public MockAuthUserService(MockUserManager userManager) {
		this.userManager = userManager;
	}


	@Override
	public Optional<AuthUser> signUpUser(SignUpRequest request) throws UsernameAlreadyExistsException {
		return Optional.of(this.userManager.signUpUser(request));
	}

	@Override
	public Optional<AuthUser> getUserById(Long userId) {
		return this.userManager.getUserById(userId);
	}

	@Override
	public Optional<AuthUser> getUserByName(AuthUsername username) {
		return this.userManager.getUserByName(username.value());
	}

	@Override
	public AuthUser updateUsername(AuthUser user, AuthUsername username) throws UsernameAlreadyExistsException {
		if (username.isEmail()) {
			return this.userManager.updateUserEmail(user, username.value());
		} else {
			return this.userManager.updateUserName(user, username.value());
		}
	}

	@Override
	public AuthUser updateUser(AuthUser user, AuthUserUpdatedValuesMark mark) {
		// Nothing to do.
		return user;
	}

	@Override
	public boolean deleteUser(AuthUser user) {
		return this.userManager.deleteUser(user);
	}


	public MockUserManager mockUserManager() {
		return this.userManager;
	}

}
