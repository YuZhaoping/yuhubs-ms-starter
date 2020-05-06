package com.yuhubs.ms.auth.redis.service;

import com.yuhubs.ms.auth.model.AuthUserGeneralValue;
import com.yuhubs.ms.auth.model.AuthUserProfileValue;
import com.yuhubs.ms.auth.model.AuthUserUpdatedValuesMark;
import com.yuhubs.ms.auth.model.AuthUsername;
import com.yuhubs.ms.auth.model.GenericAuthUser;
import com.yuhubs.ms.auth.redis.RedisAuthUserServiceBase;
import com.yuhubs.ms.auth.service.AuthUserService;
import com.yuhubs.ms.redis.RedisSequence;
import com.yuhubs.ms.redis.RedisSequenceSupplier;
import com.yuhubs.ms.redis.RedisTemplateProvider;
import com.yuhubs.ms.security.auth.AuthUser;
import com.yuhubs.ms.security.auth.SignUpRequest;
import com.yuhubs.ms.security.auth.exceptions.UsernameAlreadyExistsException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Optional;
import java.util.function.Supplier;

public final class RedisAuthUserService
		extends RedisAuthUserServiceBase implements AuthUserService {

	private final Supplier<StringRedisTemplate> stringTemplateSupplier;

	private final UserGeneralValueTemplateSupplier generalValueTemplateSupplier;
	private final UserProfileTemplateSupplier profileTemplateSupplier;

	private final RedisSequenceSupplier sequenceSupplier;


	public RedisAuthUserService(RedisTemplateProvider templateProvider) {
		this.stringTemplateSupplier = templateProvider.stringRedisTemplateSupplier();

		this.generalValueTemplateSupplier =
				new UserGeneralValueTemplateSupplier(templateProvider.redisConnectionFactory());
		this.profileTemplateSupplier =
				new UserProfileTemplateSupplier(templateProvider.redisConnectionFactory());

		this.sequenceSupplier = templateProvider.getRedisSequenceSupplier(
				USER_ID_SEQ_NAME,
				USER_ID_SEQ_INIT_VALUE
		);
	}


	@Override
	public Optional<AuthUser> signUpUser(SignUpRequest request) throws UsernameAlreadyExistsException {
		final String email = request.getEmail();
		final String emailKey = userNameToKey(email);

		final String name = request.getUsername();
		final String nameKey = userNameToKey(name);

		final StringRedisTemplate keyTemplate = stringTemplate();

		if (keyTemplate.hasKey(nameKey)) {
			throw usernameAlreadyExistsException(name);
		}

		if (keyTemplate.hasKey(emailKey)) {
			throw usernameAlreadyExistsException(email);
		}

		final Long userId = Long.valueOf(userIdSequence().nextVal());
		final String userIdKey = userIdToKey(userId);

		final ValueOperations<String, String> keyOps = keyTemplate.opsForValue();

		if (!keyOps.setIfAbsent(nameKey, userIdKey)) {
			throw usernameAlreadyExistsException(name);
		}

		if (!keyOps.setIfAbsent(emailKey, userIdKey)) {
			keyTemplate.delete(nameKey);
			throw usernameAlreadyExistsException(email);
		}

		final AuthUserGeneralValue generalValue = new AuthUserGeneralValue(userId);
		generalValue.setPasswordHash(request.getPassword());
		generalValue.setStatus(request.getStatus());

		final String groups = getDefaultUserGroups();

		generalValue.setPermissions(getPermissionsByUserGroups(groups));

		final AuthUserProfileValue profileValue = new AuthUserProfileValue(name);
		profileValue.setGroups(groups);
		profileValue.setEmail(email);

		generalValueOps().set(userIdKey, generalValue);
		profileValueOps().set(userIdKey, profileValue);

		GenericAuthUser authUser = new GenericAuthUser(generalValue);
		authUser.setProfile(profileValue);

		return Optional.of(authUser);
	}

	@Override
	public Optional<AuthUser> getUserById(Long userId) {
		return getUserByIdKey(userIdToKey(userId));
	}

	@Override
	public Optional<AuthUser> getUserByName(AuthUsername username) {
		final String usernameKey = userNameToKey(username.value());

		final String userIdKey = stringValueOps().get(usernameKey);
		if (userIdKey == null) {
			return Optional.empty();
		}

		return getUserByIdKey(userIdKey);
	}

	private Optional<AuthUser> getUserByIdKey(String userIdKey) {
		AuthUserGeneralValue generalValue = generalValueOps().get(userIdKey);
		if (generalValue == null) {
			return Optional.empty();
		}

		GenericAuthUser authUser = new GenericAuthUser(generalValue);
		authUser.setProfile(profileValueOps().get(userIdKey));

		return Optional.of(authUser);
	}


	@Override
	public AuthUser updateUsername(AuthUser authUser, AuthUsername username) throws UsernameAlreadyExistsException {
		final GenericAuthUser user = (GenericAuthUser)authUser;

		final String newName = username.value();

		if (username.isEmail()) {
			final String oldName = user.getProfile().getEmail();

			user.getProfile().setEmail(newName);

			return doUpdateUsername(user, newName, oldName);
		} else {
			final String oldName = user.getProfile().getName();

			user.getProfile().setName(newName);

			return doUpdateUsername(user, newName, oldName);
		}
	}

	private AuthUser doUpdateUsername(GenericAuthUser user, String newName, String oldName)
			throws UsernameAlreadyExistsException {
		final StringRedisTemplate keyTemplate = stringTemplate();
		final ValueOperations<String, String> keyOps = keyTemplate.opsForValue();

		final String userIdKey = userIdToKey(user.getId());

		if (!keyOps.setIfAbsent(userNameToKey(newName), userIdKey)) {
			throw usernameAlreadyExistsException(newName);
		}

		keyTemplate.delete(userNameToKey(oldName));

		profileValueOps().setIfPresent(userIdKey, user.getProfile());

		return user;
	}

	@Override
	public AuthUser updateUser(AuthUser authUser, AuthUserUpdatedValuesMark mark) {
		final GenericAuthUser user = (GenericAuthUser)authUser;

		final String userIdKey = userIdToKey(user.getId());

		if (mark.isUserGroupsUpdated()) {
			profileValueOps().setIfPresent(userIdKey, user.getProfile());

			mark.unsetUserGroupsUpdated();
		}

		if (mark.hasUpdated()) {
			generalValueOps().setIfPresent(userIdKey, user.generalValue());
		}

		return user;
	}


	@Override
	public boolean deleteUser(AuthUser authUser) {
		final GenericAuthUser user = (GenericAuthUser)authUser;

		final StringRedisTemplate keyTemplate = stringTemplate();

		keyTemplate.delete(userNameToKey(user.getProfile().getName()));
		keyTemplate.delete(userNameToKey(user.getProfile().getEmail()));

		final String userIdKey = userIdToKey(user.getId());

		generalValueTemplate().delete(userIdKey);
		profileValueTemplate().delete(userIdKey);

		return true;
	}


	private StringRedisTemplate stringTemplate() {
		return this.stringTemplateSupplier.get();
	}

	private ValueOperations<String, String> stringValueOps() {
		return stringTemplate().opsForValue();
	}

	private RedisTemplate<String, AuthUserGeneralValue> generalValueTemplate() {
		return this.generalValueTemplateSupplier.get();
	}

	private ValueOperations<String, AuthUserGeneralValue> generalValueOps() {
		return generalValueTemplate().opsForValue();
	}

	private RedisTemplate<String, AuthUserProfileValue> profileValueTemplate() {
		return this.profileTemplateSupplier.get();
	}

	private ValueOperations<String, AuthUserProfileValue> profileValueOps() {
		return profileValueTemplate().opsForValue();
	}

	private RedisSequence userIdSequence() {
		return this.sequenceSupplier.get();
	}

	private String getPermissionsByUserGroups(String groups) {
		return getGroupRoleSupport().getPermissionsByGroups(groups);
	}

	private String getDefaultUserGroups() {
		return getGroupRoleSupport().getDefaultUserGroups();
	}

}
