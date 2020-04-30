package com.yuhubs.ms.auth.redis;

public class RedisAuthUserServiceBase {

	private static final String USER_ID_KEY_PREFIX    = "auth-user:id:";
	private static final String USER_NAME_KEY_PREFIX  = "auth-user:name:";
	private static final String USER_EMAIL_KEY_PREFIX = "auth-user:email:";


	protected static String userIdToKey(Long userId) {
		return USER_ID_KEY_PREFIX + userId;
	}

	protected static Long userIdFromKey(String key) {
		return Long.parseLong(key.substring(USER_ID_KEY_PREFIX.length()));
	}

	protected static String userNameToKey(String username) {
		return USER_NAME_KEY_PREFIX + username;
	}

	protected static String userNameFromKey(String key) {
		return key.substring(USER_NAME_KEY_PREFIX.length());
	}

	protected static String userEmailToKey(String email) {
		return USER_EMAIL_KEY_PREFIX + email;
	}

	protected static String userEmailFromKey(String key) {
		return key.substring(USER_EMAIL_KEY_PREFIX.length());
	}

}
