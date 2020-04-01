package com.yuhubs.ms.security.auth;

import reactor.core.publisher.Mono;

public interface AuthUserService {

	interface Provider {
		AuthUserService authUserService();
	}

	Mono<AuthUser> signUpUser(SignUpRequest request);

	Mono<AuthUser> getUserById(Long userId);

	Mono<AuthUser> getUserByName(String username);

}
