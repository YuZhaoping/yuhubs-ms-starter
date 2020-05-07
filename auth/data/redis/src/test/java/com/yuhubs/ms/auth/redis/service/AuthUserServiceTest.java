package com.yuhubs.ms.auth.redis.service;

import com.yuhubs.ms.auth.model.AuthUserUpdatedValuesMark;
import com.yuhubs.ms.auth.model.AuthUsername;
import com.yuhubs.ms.auth.redis.ConfiguredTestBase;
import com.yuhubs.ms.redis.RedisTemplateProvider;
import com.yuhubs.ms.security.auth.AccountStatus;
import com.yuhubs.ms.security.auth.AuthUser;
import com.yuhubs.ms.security.auth.SignUpRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.Assert.*;

public class AuthUserServiceTest extends ConfiguredTestBase {

	@Autowired
	private RedisTemplateProvider authRedisTemplateProvider;

	private RedisAuthUserService authUserService;


	@Before
	public void setUp() {
		authUserService = new RedisAuthUserService(authRedisTemplateProvider);
	}

	@Test
	public void testSignUpUser() {
		final String name = "u001";
		final SignUpRequest request = createSignUpRequest(name);

		assertFalse(authUserService.getUserByName(AuthUsername.of(name)).isPresent());

		Optional<AuthUser> userOp = authUserService.signUpUser(request);
		verifyAuthUser(userOp, request);

		userOp = authUserService.getUserByName(AuthUsername.of(name));
		verifyAuthUser(userOp, request);

		authUserService.deleteUser(userOp.get());
		assertFalse(authUserService.getUserByName(AuthUsername.of(name)).isPresent());
	}

	private void verifyAuthUser(Optional<AuthUser> userOp, SignUpRequest request) {
		assertTrue(userOp.isPresent());
		assertEquals(request.getPassword(), userOp.get().getPasswordHash());
		assertEquals(request.getUsername(), userOp.get().getProfile().getName());
	}

	@Test
	public void testUpdateUsername() {
		final String oldName = "u002";
		final String newName = "u004";
		final SignUpRequest request = createSignUpRequest(oldName);

		assertFalse(authUserService.getUserByName(AuthUsername.of(oldName)).isPresent());

		authUserService.signUpUser(request);

		Optional<AuthUser> userOp = authUserService.getUserByName(AuthUsername.of(oldName));
		assertTrue(userOp.isPresent());

		assertFalse(authUserService.getUserByName(AuthUsername.of(newName)).isPresent());

		authUserService.updateUsername(userOp.get(), AuthUsername.ofName(newName));

		assertFalse(authUserService.getUserByName(AuthUsername.of(oldName)).isPresent());

		userOp = authUserService.getUserByName(AuthUsername.of(newName));
		request.setUsername(newName);
		verifyAuthUser(userOp, request);

		authUserService.deleteUser(userOp.get());
		assertFalse(authUserService.getUserByName(AuthUsername.of(newName)).isPresent());
	}

	@Test
	public void testUpdateUser() {
		final String name = "u003";
		final SignUpRequest request = createSignUpRequest(name);

		authUserService.signUpUser(request);

		Optional<AuthUser> userOp = authUserService.getUserByName(AuthUsername.of(name));
		assertTrue(userOp.isPresent());
		assertTrue(userOp.get().getAccountStatus().isEnabled());

		AuthUser authUser = userOp.get();
		authUser.setAccountStatus(AccountStatus.Op.SET_ACCOUNT_DISABLED);

		AuthUserUpdatedValuesMark mark = new AuthUserUpdatedValuesMark();
		mark.setAccountStatusUpdated();

		authUserService.updateUser(authUser, mark);

		userOp = authUserService.getUserByName(AuthUsername.of(name));
		verifyAuthUser(userOp, request);
		assertFalse(userOp.get().getAccountStatus().isEnabled());

		authUserService.deleteUser(userOp.get());
	}

}
