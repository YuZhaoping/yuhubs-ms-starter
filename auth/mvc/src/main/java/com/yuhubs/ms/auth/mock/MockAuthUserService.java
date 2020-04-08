package com.yuhubs.ms.auth.mock;

import com.yuhubs.ms.security.auth.AuthUser;
import com.yuhubs.ms.security.auth.AuthUserService;
import com.yuhubs.ms.security.auth.SignUpRequest;
import com.yuhubs.ms.security.auth.exceptions.UserAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("AuthUserService")
public class MockAuthUserService implements AuthUserService {

	private final MockUserManager userManager;


	public MockAuthUserService(MockUserManager userManager) {
		this.userManager = userManager;
	}


	@Override
	public Optional<AuthUser> signUpUser(SignUpRequest request) throws UserAlreadyExistsException {
		return Optional.of(this.userManager.signUpUser(request));
	}

	@Override
	public Optional<AuthUser> getUserById(Long userId) {
		return this.userManager.getUserById(userId);
	}

	@Override
	public Optional<AuthUser> getUserByName(String username) {
		return this.userManager.getUserByName(username);
	}


	public MockUserManager mockUserManager() {
		return this.userManager;
	}

}
