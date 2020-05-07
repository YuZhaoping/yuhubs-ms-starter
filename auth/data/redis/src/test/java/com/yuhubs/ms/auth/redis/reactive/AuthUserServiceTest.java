package com.yuhubs.ms.auth.redis.reactive;

import com.yuhubs.ms.auth.model.AuthUserUpdatedValuesMark;
import com.yuhubs.ms.auth.model.AuthUsername;
import com.yuhubs.ms.auth.redis.ConfiguredTestBase;
import com.yuhubs.ms.redis.ReactiveRedisTemplateProvider;
import com.yuhubs.ms.security.auth.AccountStatus;
import com.yuhubs.ms.security.auth.AuthUser;
import com.yuhubs.ms.security.auth.SignUpRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class AuthUserServiceTest extends ConfiguredTestBase {

	@Autowired
	private ReactiveRedisTemplateProvider authReactiveRedisTemplateProvider;

	private RedisAuthUserService authUserService;


	@Before
	public void setUp() {
		authUserService = new RedisAuthUserService(authReactiveRedisTemplateProvider);
	}

	@Test
	public void testSignUpUser() {
		final String name = "u101";
		final SignUpRequest request = createSignUpRequest(name);

		StepVerifier.create(authUserService.getUserByName(AuthUsername.of(name)))
				.verifyComplete();

		Mono<AuthUser> userMono = authUserService.signUpUser(request);
		verifyAuthUser(userMono, request);

		userMono = authUserService.getUserByName(AuthUsername.of(name));
		verifyAuthUser(userMono, request);

		StepVerifier.create(
				authUserService.getUserByName(AuthUsername.of(name))
						.flatMap(user -> authUserService.deleteUser(user))
		).expectNext(true).expectComplete().verify();
	}

	private void verifyAuthUser(Mono<AuthUser> userMono, final SignUpRequest request) {
		StepVerifier.create(userMono)
				.expectNextMatches(user ->
						request.getPassword().equals(user.getPasswordHash()) &&
								request.getUsername().equals(user.getProfile().getName())
				).expectComplete().verify();
	}

	@Test
	public void testUpdateUsername() {
		final String oldName = "u102";
		final String newName = "u104";
		final SignUpRequest request = createSignUpRequest(oldName);

		StepVerifier.create(authUserService.getUserByName(AuthUsername.of(oldName)))
				.verifyComplete();

		StepVerifier.create(authUserService.signUpUser(request))
				.expectNextCount(1)
				.expectComplete().verify();

		StepVerifier.create(authUserService.getUserByName(AuthUsername.of(oldName)))
				.expectNextCount(1)
				.expectComplete().verify();

		StepVerifier.create(authUserService.getUserByName(AuthUsername.of(newName)))
				.verifyComplete();

		StepVerifier.create(
				authUserService.getUserByName(AuthUsername.of(oldName))
						.flatMap(authUser -> authUserService.updateUsername(authUser, AuthUsername.ofName(newName)))
		).expectNextCount(1).expectComplete().verify();

		StepVerifier.create(authUserService.getUserByName(AuthUsername.of(oldName)))
				.verifyComplete();

		Mono<AuthUser> userMono = authUserService.getUserByName(AuthUsername.of(newName));
		request.setUsername(newName);
		verifyAuthUser(userMono, request);

		StepVerifier.create(
				authUserService.getUserByName(AuthUsername.of(newName))
						.flatMap(user -> authUserService.deleteUser(user))
		).expectNext(true).expectComplete().verify();
	}

	@Test
	public void testUpdateUser() {
		final String name = "u103";
		final SignUpRequest request = createSignUpRequest(name);

		StepVerifier.create(authUserService.signUpUser(request))
				.expectNextCount(1)
				.expectComplete().verify();

		StepVerifier.create(authUserService.getUserByName(AuthUsername.of(name)))
				.expectNextMatches(user -> user.getAccountStatus().isEnabled())
				.expectComplete().verify();

		StepVerifier.create(
				authUserService.getUserByName(AuthUsername.of(name))
						.flatMap(authUser -> {
							authUser.setAccountStatus(AccountStatus.Op.SET_ACCOUNT_DISABLED);

							AuthUserUpdatedValuesMark mark = new AuthUserUpdatedValuesMark();
							mark.setAccountStatusUpdated();

							return authUserService.updateUser(authUser, mark);
						})
		).expectNextCount(1).expectComplete().verify();

		Mono<AuthUser> userMono = authUserService.getUserByName(AuthUsername.of(name));
		verifyAuthUser(userMono, request);

		StepVerifier.create(authUserService.getUserByName(AuthUsername.of(name)))
				.expectNextMatches(user -> !user.getAccountStatus().isEnabled())
				.expectComplete().verify();

		StepVerifier.create(
				authUserService.getUserByName(AuthUsername.of(name))
						.flatMap(user -> authUserService.deleteUser(user))
		).expectNext(true).expectComplete().verify();
	}

}
