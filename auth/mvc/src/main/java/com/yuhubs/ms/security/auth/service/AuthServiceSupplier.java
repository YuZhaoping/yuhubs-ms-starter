package com.yuhubs.ms.security.auth.service;

import com.yuhubs.ms.security.auth.AuthSecurityContext;
import com.yuhubs.ms.security.auth.AuthUserAuthentication;
import com.yuhubs.ms.security.auth.AuthUserService;
import com.yuhubs.ms.security.auth.event.AuthConfirmUrlsBuilder;
import com.yuhubs.ms.security.auth.event.AuthEventPublisher;
import com.yuhubs.ms.security.auth.web.dto.ConfirmPasswordDto;
import com.yuhubs.ms.security.auth.web.dto.ResetPasswordRequestDto;
import com.yuhubs.ms.security.auth.web.dto.SignUpRequestDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Map;
import java.util.Optional;

public final class AuthServiceSupplier {

	private final AuthSecurityContext securityContext;
	private final AuthUserService authUserService;
	private final AuthEventPublisher eventPublisher;

	private final SignUpService signUpService;
	private final ResetPasswordService resetPasswordService;
	private final RefreshTokenService refreshTokenService;


	public AuthServiceSupplier(AuthSecurityContext context,
							   AuthUserService service,
							   AuthConfirmUrlsBuilder urlsBuilder) {
		this.securityContext = context;
		this.authUserService = service;
		this.eventPublisher = new AuthEventPublisher(context, urlsBuilder);

		this.signUpService = new SignUpService(this);
		this.resetPasswordService = new ResetPasswordService(this);
		this.refreshTokenService = new RefreshTokenService(this);
	}


	public Optional<AuthUserAuthentication> signUp(SignUpRequestDto dto)
			throws AuthenticationException {
		return this.signUpService.signUp(dto);
	}

	public void confirmEmail(String token) throws AuthenticationException {
		this.signUpService.confirmEmail(token);
	}


	public void emitResetPassword(ResetPasswordRequestDto dto)
			throws AuthenticationException {
		this.resetPasswordService.emitResetPassword(dto);
	}

	public Map<String, ?> getResetPasswordModel(String token)
			throws AuthenticationException {
		return resetPasswordService.getResetPasswordModel(token);
	}

	public void confirmPassword(String token, ConfirmPasswordDto dto)
			throws AuthenticationException {
		this.resetPasswordService.confirmPassword(token, dto);
	}


	public AuthUserAuthentication refreshToken(Authentication tokenAuth)
			throws AuthenticationException {
		return this.refreshTokenService.refreshToken(tokenAuth);
	}


	public AuthSecurityContext authSecurityContext() {
		return this.securityContext;
	}

	public AuthUserService authUserService() {
		return this.authUserService;
	}

	public AuthEventPublisher authEventPublisher() {
		return this.eventPublisher;
	}

}
