package com.yuhubs.ms.auth.redis;

import com.yuhubs.ms.auth.model.AuthUserGroupRoleSupport;
import com.yuhubs.ms.security.auth.exceptions.UsernameAlreadyExistsException;

public class RedisAuthUserServiceBase {

	protected static final String USER_ID_SEQ_NAME = "auth-user:id-seq";
	protected static final long USER_ID_SEQ_INIT_VALUE = 1000L;

	private static final String USER_ID_KEY_PREFIX = "auth-user:id:";
	private static final String USER_ID_PROFILE_KEY_PREFIX = "auth-user:profile:id:";
	private static final String USER_NAME_KEY_PREFIX = "auth-user:name:";


	protected AuthUserGroupRoleSupport defaultGroupRoleSupport;


	public RedisAuthUserServiceBase() {
		this.defaultGroupRoleSupport = new AuthUserGroupRoleSupport();
	}


	protected AuthUserGroupRoleSupport getGroupRoleSupport() {
		return this.defaultGroupRoleSupport;
	}


	protected static String userIdToKey(Long userId) {
		return USER_ID_KEY_PREFIX + userId;
	}

	protected static Long userIdFromKey(String key) {
		return Long.parseLong(key.substring(USER_ID_KEY_PREFIX.length()));
	}

	protected static String userIdToProfileKey(Long userId) {
		return USER_ID_PROFILE_KEY_PREFIX + userId;
	}

	protected static String userNameToKey(String username) {
		return USER_NAME_KEY_PREFIX + username;
	}


	protected static UsernameAlreadyExistsException
		usernameAlreadyExistsException(String username) {
		return new UsernameAlreadyExistsException(
				"The \'" + username + "\' already exists");
	}

}
