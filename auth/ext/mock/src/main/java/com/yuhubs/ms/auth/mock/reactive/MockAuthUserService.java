package com.yuhubs.ms.auth.mock.reactive;

import com.yuhubs.ms.auth.mock.MockUserManager;
import com.yuhubs.ms.auth.model.AuthUserUpdatedValuesMark;
import com.yuhubs.ms.auth.model.AuthUsername;
import com.yuhubs.ms.auth.reactive.AuthUserService;
import com.yuhubs.ms.security.auth.AuthUser;
import com.yuhubs.ms.security.auth.SignUpRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service("AuthUserService")
public class MockAuthUserService implements AuthUserService {

	private final MockUserManager userManager;


	public MockAuthUserService(MockUserManager userManager) {
		this.userManager = userManager;
	}


	@Override
	public Mono<AuthUser> signUpUser(SignUpRequest request) {
		return Mono.just(request)
				.map(this.userManager::signUpUser);
	}

	@Override
	public Mono<AuthUser> getUserById(Long userId) {
		final Optional<AuthUser> userOp = this.userManager.getUserById(userId);
		if (userOp.isPresent()) {
			return Mono.just(userOp.get());
		} else {
			return Mono.empty();
		}
	}

	@Override
	public Mono<AuthUser> getUserByName(AuthUsername username) {
		final Optional<AuthUser> userOp = this.userManager.getUserByName(username.value());
		if (userOp.isPresent()) {
			return Mono.just(userOp.get());
		} else {
			return Mono.empty();
		}
	}

	@Override
	public Mono<AuthUser> updateUsername(AuthUser user, AuthUsername username) {
		if (username.isEmail()) {
			return Mono.just(this.userManager.updateUserEmail(user, username.value()));
		} else {
			return Mono.just(this.userManager.updateUserName(user, username.value()));
		}
	}

	@Override
	public Mono<AuthUser> updateUser(AuthUser user, AuthUserUpdatedValuesMark mark) {
		// Nothing to do.
		return Mono.just(user);
	}

	@Override
	public Mono<Boolean> deleteUser(AuthUser user) {
		return Mono.just(this.userManager.deleteUser(user));
	}


	public MockUserManager mockUserManager() {
		return this.userManager;
	}

}
