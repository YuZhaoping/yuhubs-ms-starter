package com.yuhubs.ms.auth.mock;

import com.yuhubs.ms.auth.mock.config.MockUser;
import com.yuhubs.ms.auth.mock.config.MockUserConfigSupport;
import com.yuhubs.ms.auth.model.AuthUserProfileValue;
import com.yuhubs.ms.security.auth.AuthSecurityContext;
import com.yuhubs.ms.security.auth.AuthUser;
import com.yuhubs.ms.security.auth.SignUpRequest;
import com.yuhubs.ms.security.auth.exceptions.UsernameAlreadyExistsException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public final class MockUserManager {

	private final MockUserConfigSupport configSupport;

	private final Map<Long, AuthUser> idMapUsers;
	private final Map<String, Long> nameMapIds;

	private final AtomicLong userIdSeq;


	public MockUserManager(MockUserConfigSupport configSupport) {
		this.configSupport = configSupport;

		this.idMapUsers = new HashMap<>();
		this.nameMapIds = new HashMap<>();

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


	public AuthUser signUpUser(SignUpRequest request) throws UsernameAlreadyExistsException {
		final String email = request.getEmail();
		final String username = request.getUsername();

		Optional<AuthUser> userOp;

		userOp = getUserByName(username);
		if (userOp.isPresent()) {
			throw new UsernameAlreadyExistsException("The \'" + username + "\' already exists");
		}

		userOp = getUserByName(email);
		if (userOp.isPresent()) {
			throw new UsernameAlreadyExistsException("The \'" + email + "\' already exists");
		}

		Long userId = userIdSeq.incrementAndGet();

		MockAuthUser user = new MockAuthUser(userId, configSupport.getUserPermissions(username));

		user.setPasswordHash(request.getPassword());
		user.setProfile(new AuthUserProfileValue(username));
		user.getProfile().setGroups(configSupport.getUserRole(username));
		user.getProfile().setEmail(email);

		user.getAccountStatus().setValue(request.getStatus());

		idMapUsers.put(userId, user);

		nameMapIds.put(username, userId);
		nameMapIds.put(email, userId);

		return user;
	}

	public Optional<AuthUser> getUserById(Long userId) {
		return Optional.ofNullable(idMapUsers.get(userId));
	}

	public Optional<AuthUser> getUserByName(String username) {
		final Long userId = nameMapIds.get(username);
		if (userId != null) {
			return Optional.ofNullable(idMapUsers.get(userId));
		} else {
			return Optional.empty();
		}
	}

	public AuthUser updateUserName(AuthUser user, String name) throws UsernameAlreadyExistsException {
		MockAuthUser mockUser = (MockAuthUser)user;

		if (nameMapIds.putIfAbsent(name, user.getId()) != null) {
			throw new UsernameAlreadyExistsException("The \'" + name + "\' already exists");
		}

		nameMapIds.remove(mockUser.getProfile().getName());
		mockUser.getProfile().setName(name);

		return mockUser;
	}

	public AuthUser updateUserEmail(AuthUser user, String email) throws UsernameAlreadyExistsException {
		MockAuthUser mockUser = (MockAuthUser)user;

		if (nameMapIds.putIfAbsent(email, user.getId()) != null) {
			throw new UsernameAlreadyExistsException("The \'" + email + "\' already exists");
		}

		nameMapIds.remove(mockUser.getProfile().getEmail());
		mockUser.getProfile().setEmail(email);

		return mockUser;
	}

	public boolean deleteUser(AuthUser user) {
		MockAuthUser mockUser = (MockAuthUser)user;

		final String username = mockUser.getProfile().getName();
		final String email = mockUser.getProfile().getEmail();

		nameMapIds.remove(username);
		nameMapIds.remove(email);

		idMapUsers.remove(mockUser.getId());

		return true;
	}


	public void clearUsers() {
		nameMapIds.clear();
		idMapUsers.clear();
	}


	private final int userIdSeqStart() {
		return 1000;
	}

}
