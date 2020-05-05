package com.yuhubs.ms.auth.reactive;

import com.yuhubs.ms.auth.model.AuthUserUpdatedValuesMark;
import com.yuhubs.ms.auth.model.AuthUsername;
import com.yuhubs.ms.security.auth.AuthUser;
import com.yuhubs.ms.security.auth.SignUpRequest;
import reactor.core.publisher.Mono;

public interface AuthUserService {

	interface Provider {
		AuthUserService authUserService();
	}

	Mono<AuthUser> signUpUser(SignUpRequest request);

	Mono<AuthUser> getUserById(Long userId);

	Mono<AuthUser> getUserByName(AuthUsername username);

	Mono<AuthUser> updateUsername(AuthUser user, AuthUsername username);

	Mono<AuthUser> updateUser(AuthUser user, AuthUserUpdatedValuesMark mark);

	Mono<Boolean> deleteUser(AuthUser user);

}
