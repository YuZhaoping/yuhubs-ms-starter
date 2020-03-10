package com.yuhubs.ms.security.auth.event;

import com.yuhubs.ms.security.auth.AuthSecurityContext;
import com.yuhubs.ms.security.auth.AuthUser;
import com.yuhubs.ms.security.auth.event.data.ConfirmEmailData;
import com.yuhubs.ms.security.auth.token.ResetPasswordToken;
import com.yuhubs.ms.security.auth.token.VerifyEmailToken;

public final class AuthEventPublisher {

	private final AuthSecurityContext securityContext;

	private final AuthConfirmUrlsBuilder urlsBuilder;


	public AuthEventPublisher(AuthSecurityContext context, AuthConfirmUrlsBuilder urlsBuilder) {
		this.securityContext = context;
		this.urlsBuilder = urlsBuilder;
	}


	public void publishVerifyEmailEvent(AuthUser user, String email) {
		VerifyEmailToken auth = VerifyEmailToken.of(user.getId());

		int exp = securityContext.authProperties().getJwtTokenExpiration();
		String token = securityContext.jwtTokenService().createJwtToken(auth, exp);

		String confirmUrl = urlsBuilder.buildVerifyEmailUrl(user.getId(), token);

		ConfirmEmailData eventData = new ConfirmEmailData(email, ConfirmEmailData.Type.VERIFY_EMAIL);

		eventData.setUsername(user.getProfile().getName())
				.setConfirmUrl(confirmUrl)
				.setToken(token);

		securityContext.publishEvent(new ConfirmEmailEvent(eventData));
	}

	public void publishResetPasswordEvent(AuthUser user, String email) {
		ResetPasswordToken auth = ResetPasswordToken.of(user.getId());

		int exp = securityContext.authProperties().getJwtTokenExpiration();
		String token = securityContext.jwtTokenService().createJwtToken(auth, exp);

		String confirmUrl = urlsBuilder.buildResetPasswordUrl(user.getId(), token);

		ConfirmEmailData eventData = new ConfirmEmailData(email, ConfirmEmailData.Type.RESET_PASSWORD);

		eventData.setUsername(user.getProfile().getName())
				.setConfirmUrl(confirmUrl)
				.setToken(token);

		securityContext.publishEvent(new ConfirmEmailEvent(eventData));
	}


	public AuthConfirmUrlsBuilder authConfirmUrlsBuilder() {
		return this.urlsBuilder;
	}

}
