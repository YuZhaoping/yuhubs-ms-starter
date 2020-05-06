package com.yuhubs.ms.auth.redis.reactive;

import com.yuhubs.ms.auth.model.AuthUserGeneralValue;
import com.yuhubs.ms.auth.model.AuthUserProfileValue;
import com.yuhubs.ms.auth.model.AuthUserUpdatedValuesMark;
import com.yuhubs.ms.auth.model.AuthUsername;
import com.yuhubs.ms.auth.model.GenericAuthUser;
import com.yuhubs.ms.auth.reactive.AuthUserService;
import com.yuhubs.ms.auth.redis.RedisAuthUserServiceBase;
import com.yuhubs.ms.redis.ReactiveRedisTemplateProvider;
import com.yuhubs.ms.redis.RedisSequence;
import com.yuhubs.ms.redis.RedisSequenceSupplier;
import com.yuhubs.ms.security.auth.AuthUser;
import com.yuhubs.ms.security.auth.SignUpRequest;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

public final class RedisAuthUserService
		extends RedisAuthUserServiceBase implements AuthUserService {

	private final Supplier<ReactiveStringRedisTemplate> stringTemplateSupplier;

	private final UserGeneralValueTemplateSupplier generalValueTemplateSupplier;
	private final UserProfileTemplateSupplier profileTemplateSupplier;

	private final RedisSequenceSupplier sequenceSupplier;


	public RedisAuthUserService(ReactiveRedisTemplateProvider templateProvider) {
		this.stringTemplateSupplier = templateProvider.stringRedisTemplateSupplier();

		this.generalValueTemplateSupplier =
				new UserGeneralValueTemplateSupplier(templateProvider.reactiveRedisConnectionFactory());
		this.profileTemplateSupplier =
				new UserProfileTemplateSupplier(templateProvider.reactiveRedisConnectionFactory());

		this.sequenceSupplier = templateProvider.getRedisSequenceSupplier(
				USER_ID_SEQ_NAME,
				USER_ID_SEQ_INIT_VALUE
		);
	}


	@Override
	public Mono<AuthUser> signUpUser(final SignUpRequest request) {
		final String email = request.getEmail();
		final String emailKey = userNameToKey(email);

		final String name = request.getUsername();
		final String nameKey = userNameToKey(name);

		final ReactiveStringRedisTemplate keyTemplate = stringTemplate();

		return keyTemplate.hasKey(nameKey).filter(r -> !r)
				.switchIfEmpty(Mono.defer(() -> Mono.error(usernameAlreadyExistsException(name))))
				.then(keyTemplate.hasKey(emailKey)).filter(r -> !r)
				.switchIfEmpty(Mono.defer(() -> Mono.error(usernameAlreadyExistsException(email))))
				.then(Mono.just(Long.valueOf(userIdSequence().nextVal())))
				.flatMap(userId -> doSignUpUser(request, userId));
	}

	private Mono<AuthUser> doSignUpUser(final SignUpRequest request, final Long userId) {
		final String userIdKey = userIdToKey(userId);

		final String email = request.getEmail();
		final String emailKey = userNameToKey(email);

		final String name = request.getUsername();
		final String nameKey = userNameToKey(name);

		final ReactiveValueOperations<String, String> keyOps = stringValueOps();

		final String groups = getDefaultUserGroups();

		final AuthUserGeneralValue generalValue = new AuthUserGeneralValue(userId);
		final AuthUserProfileValue profileValue = new AuthUserProfileValue(name);

		return keyOps.setIfAbsent(nameKey, userIdKey).filter(r -> r)
				.switchIfEmpty(Mono.defer(() -> Mono.error(usernameAlreadyExistsException(name))))
				.then(keyOps.setIfAbsent(emailKey, userIdKey)).filter(r -> r)
				.switchIfEmpty(Mono.defer(() -> keyOps.delete(nameKey)
						.then(Mono.error(usernameAlreadyExistsException(email)))))
				.then(Mono.just(generalValue))
				.doOnNext(value -> {
					value.setPasswordHash(request.getPassword());
					value.setPermissions(getPermissionsByUserGroups(groups));
					value.setStatus(request.getStatus());
				})
				.flatMap(value -> generalValueOps().set(userIdKey, value))
				.then(Mono.just(profileValue))
				.doOnNext(value -> {
					value.setGroups(groups);
					value.setEmail(email);
				})
				.flatMap(value -> profileValueOps().set(userIdKey,value))
				.then(createAuthUser(generalValue, profileValue));
	}

	@Override
	public Mono<AuthUser> getUserById(final Long userId) {
		return getUserByIdKey(userIdToKey(userId));
	}

	@Override
	public Mono<AuthUser> getUserByName(final AuthUsername username) {
		return stringValueOps().get(username.value())
				.switchIfEmpty(Mono.empty())
				.flatMap(this::getUserByIdKey);
	}

	private Mono<AuthUser> getUserByIdKey(final String userIdKey) {
		return generalValueOps().get(userIdKey)
				.switchIfEmpty(Mono.empty())
				.zipWith(profileValueOps().get(userIdKey))
				.flatMap(tuple -> createAuthUser(tuple.getT1(), tuple.getT2()));
	}

	private static Mono<AuthUser> createAuthUser(
			final AuthUserGeneralValue generalValue,
			final AuthUserProfileValue profileValue) {

		final GenericAuthUser authUser = new GenericAuthUser(generalValue);

		authUser.setProfile(profileValue);

		return Mono.just(authUser);
	}


	@Override
	public Mono<AuthUser> updateUsername(AuthUser authUser, AuthUsername username) {
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

	private Mono<AuthUser> doUpdateUsername(GenericAuthUser user, String newName, String oldName) {
		final String userIdKey = userIdToKey(user.getId());

		final ReactiveValueOperations<String, String> keyOps = stringValueOps();

		return keyOps.setIfAbsent(userNameToKey(newName), userIdKey).filter(r -> r)
				.switchIfEmpty(Mono.defer(() -> Mono.error(usernameAlreadyExistsException(newName))))
				.then(keyOps.delete(userNameToKey(oldName)))
				.then(profileValueOps().setIfPresent(userIdKey, user.getProfile()))
				.thenReturn(user);
	}

	@Override
	public Mono<AuthUser> updateUser(AuthUser authUser, final AuthUserUpdatedValuesMark mark) {
		final GenericAuthUser user = (GenericAuthUser)authUser;

		return tryUpdateProfileValue(user, mark)
				.then(tryUpdateGeneralValue(user, mark))
				.thenReturn(user);
	}

	private Mono<Boolean> tryUpdateProfileValue(GenericAuthUser user, AuthUserUpdatedValuesMark mark) {
		if (mark.isUserGroupsUpdated()) {
			mark.unsetUserGroupsUpdated();
			return profileValueOps().setIfPresent(userIdToKey(user.getId()), user.getProfile());
		} else {
			return Mono.just(false);
		}
	}

	private Mono<Boolean> tryUpdateGeneralValue(GenericAuthUser user, AuthUserUpdatedValuesMark mark) {
		if (mark.hasUpdated()) {
			return generalValueOps().setIfPresent(userIdToKey(user.getId()), user.generalValue());
		} else {
			return Mono.just(false);
		}
	}


	@Override
	public Mono<Boolean> deleteUser(AuthUser authUser) {
		final GenericAuthUser user = (GenericAuthUser)authUser;

		final ReactiveValueOperations<String, String> keyOps = stringValueOps();

		final String userIdKey = userIdToKey(user.getId());

		return keyOps.delete(userNameToKey(user.getProfile().getName()))
				.then(keyOps.delete(userNameToKey(user.getProfile().getEmail())))
				.then(generalValueOps().delete(userIdKey))
				.then(profileValueOps().delete(userIdKey));
	}


	private ReactiveStringRedisTemplate stringTemplate() {
		return this.stringTemplateSupplier.get();
	}

	private ReactiveValueOperations<String, String> stringValueOps() {
		return stringTemplate().opsForValue();
	}

	private ReactiveRedisTemplate<String, AuthUserGeneralValue> generalValueTemplate() {
		return this.generalValueTemplateSupplier.get();
	}

	private ReactiveValueOperations<String, AuthUserGeneralValue> generalValueOps() {
		return generalValueTemplate().opsForValue();
	}

	private ReactiveRedisTemplate<String, AuthUserProfileValue> profileValueTemplate() {
		return this.profileTemplateSupplier.get();
	}

	private ReactiveValueOperations<String, AuthUserProfileValue> profileValueOps() {
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
