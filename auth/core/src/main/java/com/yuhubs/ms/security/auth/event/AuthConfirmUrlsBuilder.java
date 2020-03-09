package com.yuhubs.ms.security.auth.event;

public interface AuthConfirmUrlsBuilder {

	String buildVerifyEmailUrl(Long userId, String token);

	String buildResetPasswordUrl(Long userId, String token);

}
