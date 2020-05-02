package com.yuhubs.ms.auth.redis;

public class RedisAuthUserServiceBase {

	protected static final String USER_ID_SEQ_NAME = "auth-user:id-seq";
	protected static final long USER_ID_SEQ_INIT_VALUE = 1000L;

	private static final String USER_ID_KEY_PREFIX    = "auth-user:id:";
	private static final String USER_NAME_KEY_PREFIX  = "auth-user:name:";


	protected static String userIdToKey(Long userId) {
		return USER_ID_KEY_PREFIX + userId;
	}

	protected static Long userIdFromKey(String key) {
		return Long.parseLong(key.substring(USER_ID_KEY_PREFIX.length()));
	}

	protected static String userNameToKey(String username) {
		return USER_NAME_KEY_PREFIX + username;
	}

}
