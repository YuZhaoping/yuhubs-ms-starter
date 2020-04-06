package com.yuhubs.ms.security.auth.web;

import com.yuhubs.ms.security.auth.event.AuthConfirmUrlsBuilder;
import com.yuhubs.ms.security.auth.service.AuthServiceSupplier;
import com.yuhubs.ms.security.auth.web.dto.ResetPasswordRequestDto;
import com.yuhubs.ms.security.auth.web.dto.SignUpRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.Rendering;
import org.springframework.web.server.ServerWebExchange;
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


	@PostMapping(SIGNUP_ENDPOINT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ResponseBody
	public Mono<Void> signUp(ServerWebExchange exchange, @RequestBody SignUpRequestDto dto) {
		return this.serviceSupplier.signUp(dto)
				.doOnNext(authentication -> {
					this.securityContext.onAuthenticationSuccess(exchange, authentication);
				}).then();
	}

	@RequestMapping(SIGNUP_ENDPOINT + "/{id}/verify_email/")
	public Mono<Rendering> confirmEmail(@PathVariable("id") Long userId,
										@RequestParam("token") String token) {
		String viewName = AuthTemplateViewID.VERIFY_EMAIL_DONE.getId();

		return this.serviceSupplier.confirmEmail(token)
				.then(Mono.just(Rendering.view(viewName).status(HttpStatus.OK).build()))
				.onErrorResume(ex -> Mono.just(
					Rendering.view(AuthTemplateViewID.VERIFY_EMAIL_FAIL.getId())
							.status(HttpStatus.UNAUTHORIZED)
							.modelAttribute("error", ex.getMessage())
							.build()
				));
	}


	@PutMapping(RESET_PASSWORD_ENDPOINT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ResponseBody
	public Mono<Void> emitResetPassword(@RequestBody ResetPasswordRequestDto dto) {
		return this.serviceSupplier.emitResetPassword(dto);
	}

	@GetMapping(SIGNUP_ENDPOINT + "/{id}/reset_password/")
	public Mono<Rendering> getResetPasswordView(@PathVariable("id") Long userId,
												@RequestParam("token") String token) {
		String viewName = AuthTemplateViewID.RESET_PASSWORD_VIEW.getId();

		return this.serviceSupplier.getResetPasswordModel(token)
				.flatMap(map -> Mono.just(
						Rendering.view(viewName).status(HttpStatus.OK)
								.model(map)
								.build()))
				.onErrorResume(ex -> Mono.just(
						Rendering.view(AuthTemplateViewID.RESET_PASSWORD_FAIL.getId())
								.status(HttpStatus.UNAUTHORIZED)
								.modelAttribute("error", ex.getMessage())
								.build()
				));
	}


	@GetMapping(SIGNIN_ENDPOINT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ResponseBody
	public Mono<Void> signOut() {
		// Nothing to do.
		return Mono.empty();
	}

}
