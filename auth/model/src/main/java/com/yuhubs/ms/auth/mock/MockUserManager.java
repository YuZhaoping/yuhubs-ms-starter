package com.yuhubs.ms.auth.mock;

import com.yuhubs.ms.auth.mock.config.MockUser;
import com.yuhubs.ms.auth.mock.config.MockUserConfigSupport;
import com.yuhubs.ms.security.auth.AuthSecurityContext;
import com.yuhubs.ms.security.auth.AuthUser;
import com.yuhubs.ms.security.auth.AuthUserProfile;
import com.yuhubs.ms.security.auth.SignUpRequest;
import com.yuhubs.ms.security.auth.exceptions.UserAlreadyExistsException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class MockUserManager {

	private final MockUserConfigSupport configSupport;

	private final Map<Long, AuthUser> idMapUsers;
	private final Map<String, AuthUser> nameMapUsers;

	private final AtomicLong userIdSeq;


	public MockUserManager(MockUserConfigSupport configSupport) {
		this.configSupport = configSupport;

		this.idMapUsers = new HashMap<>();
		this.nameMapUsers = new HashMap<>();

		this.userIdSeq = new AtomicLong(userIdSeqStart());
	}


	public void initUsers(final AuthSecurityContext context) throws Exception {
		final List<MockUser> users = configSupport.getUsers();

		users.forEach(user -> {
			String passwordHash = passwordHash(context, user.getPasswd());

			SignUpRequest request = new SignUpRequest(user.getEmail(), user.getName(), passwordHash);
			request.setStatus(user.getStatus());

			signUpUser(request);
		});
	}

	private static String passwordHash(AuthSecurityContext ctx, String password) {
		return ctx.passwordEncoder().encode(password);
	}


	public AuthUser signUpUser(SignUpRequest request) throws UserAlreadyExistsException {
		final String email = request.getEmail();
		final String username = request.getUsername();

		Optional<AuthUser> userOp;

		userOp = getUserByName(username);
		if (userOp.isPresent()) {
			throw new UserAlreadyExistsException("The username already exists");
		}

		userOp = getUserByName(email);
		if (userOp.isPresent()) {
			throw new UserAlreadyExistsException("The email already exists");
		}

		Long userId = userIdSeq.incrementAndGet();

		MockAuthUser user = new MockAuthUser(userId, configSupport.getUserPermissions(username));

		user.setPasswordHash(request.getPassword());
		user.setProfile(new AuthUserProfile(username, configSupport.getUserRole(username)));

		user.getAccountStatus().setValue(request.getStatus());

		idMapUsers.put(userId, user);

		nameMapUsers.put(email, user);
		nameMapUsers.put(username, user);

		return user;
	}

	public Optional<AuthUser> getUserById(Long userId) {
		return Optional.ofNullable(idMapUsers.get(userId));
	}

	public Optional<AuthUser> getUserByName(String username) {
		return Optional.ofNullable(nameMapUsers.get(username));
	}


	public void clearUsers() {
		idMapUsers.clear();
		nameMapUsers.clear();
	}


	private final int userIdSeqStart() {
		return 1000;
	}

}
