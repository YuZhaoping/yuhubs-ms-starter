package com.yuhubs.ms.security.auth.web;

import com.yuhubs.ms.security.auth.event.AuthConfirmUrlsBuilder;
import com.yuhubs.ms.security.auth.service.AuthServiceSupplier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;

@Controller
public class AuthUserController implements AuthApiEndpoints {

	private static final class UrlsBuilder implements AuthConfirmUrlsBuilder {

		@Override
		public String buildVerifyEmailUrl(Long userId, String token) {
			StringBuilder buf = new StringBuilder();

			buf.append(SIGNUP_ENDPOINT).append('/').append(userId);
			buf.append("/verify_email");

			return buf.toString();
		}

		@Override
		public String buildResetPasswordUrl(Long userId, String token) {
			StringBuilder buf = new StringBuilder();

			buf.append(SIGNUP_ENDPOINT).append('/').append(userId);
			buf.append("/reset_password");

			return buf.toString();
		}

	}


	private final AuthWebSecurityContext securityContext;

	private final AuthServiceSupplier serviceSupplier;


	AuthUserController(AuthWebSecurityContext context) {
		this.securityContext = context;

		this.serviceSupplier = new AuthServiceSupplier(
				context,
				context.userServiceProvider(),
				new UrlsBuilder());
	}


	@PostMapping(SIGNIN_ENDPOINT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ResponseBody
	public Mono<Void> signIn() {
		// LoginAuthenticationManager has already signed in.
		return Mono.empty();
	}


	@GetMapping(SIGNIN_ENDPOINT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ResponseBody
	public Mono<Void> signOut() {
		// Nothing to do.
		return Mono.empty();
	}

}
