package com.yuhubs.ms.security.auth.service;

import com.yuhubs.ms.security.auth.AuthSecurityContext;
import com.yuhubs.ms.security.auth.AuthUserAuthentication;
import com.yuhubs.ms.security.auth.AuthUserService;
import com.yuhubs.ms.security.auth.event.AuthConfirmUrlsBuilder;
import com.yuhubs.ms.security.auth.event.AuthEventPublisher;
import com.yuhubs.ms.security.auth.web.dto.ResetPasswordRequestDto;
import com.yuhubs.ms.security.auth.web.dto.SignUpRequestDto;
import org.springframework.security.core.AuthenticationException;
import reactor.core.publisher.Mono;

public final class AuthServiceSupplier {

	private final AuthSecurityContext securityContext;
	private final AuthUserService.Provider userServiceProvider;
	private final AuthEventPublisher eventPublisher;

	private final SignUpService signUpService;
	private final ResetPasswordService resetPasswordService;


	public AuthServiceSupplier(AuthSecurityContext context,
							   AuthUserService.Provider userServiceProvider,
							   AuthConfirmUrlsBuilder urlsBuilder) {
		this.securityContext = context;
		this.userServiceProvider = userServiceProvider;
		this.eventPublisher = new AuthEventPublisher(context, urlsBuilder);

		this.signUpService = new SignUpService(this);
		this.resetPasswordService = new ResetPasswordService(this);
	}


	public Mono<AuthUserAuthentication> signUp(SignUpRequestDto dto)
			throws AuthenticationException {
		return this.signUpService.signUp(dto);
	}

	public Mono<Void> confirmEmail(String token) throws AuthenticationException {
		return this.signUpService.confirmEmail(token);
	}


	public Mono<Void> emitResetPassword(ResetPasswordRequestDto dto)
			throws AuthenticationException {
		return this.resetPasswordService.emitResetPassword(dto);
	}


	public AuthSecurityContext authSecurityContext() {
		return this.securityContext;
	}

	public AuthUserService authUserService() {
		return this.userServiceProvider.authUserService();
	}

	public AuthEventPublisher authEventPublisher() {
		return this.eventPublisher;
	}

}
