package com.yuhubs.ms.auth.reactive;

import com.yuhubs.ms.security.auth.AuthUser;
import com.yuhubs.ms.security.auth.SignUpRequest;
import reactor.core.publisher.Mono;

public interface AuthUserService {

	interface Provider {
		AuthUserService authUserService();
	}

	Mono<AuthUser> signUpUser(SignUpRequest request);

	Mono<AuthUser> getUserById(Long userId);

	Mono<AuthUser> getUserByName(String username);

}
