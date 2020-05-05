package com.yuhubs.ms.security.auth.service;

import com.yuhubs.ms.auth.model.AuthUserUpdatedValuesMark;
import com.yuhubs.ms.auth.model.AuthUsername;
import com.yuhubs.ms.auth.reactive.AuthUserService;
import com.yuhubs.ms.security.auth.AuthUser;
import com.yuhubs.ms.security.auth.SignUpRequest;
import reactor.core.publisher.Mono;

public final class NoOpAuthUserService implements AuthUserService {

	private static final NoOpAuthUserService INSTANCE = new NoOpAuthUserService();

	public static AuthUserService getInstance() {
		return INSTANCE;
	}

	private NoOpAuthUserService() {
	}


	@Override
	public Mono<AuthUser> signUpUser(SignUpRequest request) {
		return Mono.empty();
	}

	@Override
	public Mono<AuthUser> getUserById(Long userId) {
		return Mono.empty();
	}

	@Override
	public Mono<AuthUser> getUserByName(AuthUsername username) {
		return Mono.empty();
	}

	@Override
	public Mono<AuthUser> updateUsername(AuthUser user, AuthUsername username) {
		return Mono.just(user);
	}

	@Override
	public Mono<AuthUser> updateUser(AuthUser user, AuthUserUpdatedValuesMark mark) {
		return Mono.just(user);
	}

	@Override
	public Mono<Boolean> deleteUser(AuthUser user) {
		return Mono.just(false);
	}

}
