package com.yuhubs.ms.security.auth.web;

import com.yuhubs.ms.security.auth.AuthUserAuthentication;
import com.yuhubs.ms.security.auth.event.AuthConfirmUrlsBuilder;
import com.yuhubs.ms.security.auth.service.AuthServiceSupplier;
import com.yuhubs.ms.security.auth.web.dto.ConfirmPasswordDto;
import com.yuhubs.ms.security.auth.web.dto.ResetPasswordRequestDto;
import com.yuhubs.ms.security.auth.web.dto.SignUpRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

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
	public void signIn() {
		// LoginAuthenticationProvider has already signed in.
	}


	@PostMapping(SIGNUP_ENDPOINT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ResponseBody
	public void signUp(HttpServletRequest request, HttpServletResponse response,
					   @RequestBody SignUpRequestDto dto)
			throws IOException, ServletException {
		Optional<AuthUserAuthentication> authOp = this.serviceSupplier.signUp(dto);

		if (!authOp.isPresent()) {
			return;
		}

		AuthUserAuthentication authentication = authOp.get();

		this.securityContext.authenticationSuccessHandler()
				.onAuthenticationSuccess(request, response, authentication);
	}

	@RequestMapping(SIGNUP_ENDPOINT + "/{id}/verify_email/")
	public ModelAndView confirmEmail(@PathVariable("id") Long userId,
									 @RequestParam("token") String token) {
		String viewName = AuthTemplateViewID.VERIFY_EMAIL_DONE.getId();

		ModelAndView view;

		try {
			this.serviceSupplier.confirmEmail(token);

			view = new ModelAndView(viewName, HttpStatus.OK);

		} catch (AuthenticationException ex) {
			viewName = AuthTemplateViewID.VERIFY_EMAIL_FAIL.getId();

			view = new ModelAndView(viewName, "error", ex.getMessage());

			view.setStatus(HttpStatus.UNAUTHORIZED);
		}

		return view;
	}


	@PutMapping(RESET_PASSWORD_ENDPOINT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ResponseBody
	public void emitResetPassword(@RequestBody ResetPasswordRequestDto dto) {
		this.serviceSupplier.emitResetPassword(dto);
	}

	@GetMapping(SIGNUP_ENDPOINT + "/{id}/reset_password/")
	public ModelAndView getResetPasswordView(@PathVariable("id") Long userId,
											 @RequestParam("token") String token) {
		String viewName = AuthTemplateViewID.RESET_PASSWORD_VIEW.getId();

		ModelAndView view;

		try {
			Map<String, ?> model = this.serviceSupplier.getResetPasswordModel(token);

			view = new ModelAndView(viewName, model, HttpStatus.OK);

		} catch (AuthenticationException ex) {
			viewName = AuthTemplateViewID.RESET_PASSWORD_FAIL.getId();

			view = new ModelAndView(viewName, "error", ex.getMessage());

			view.setStatus(HttpStatus.UNAUTHORIZED);
		}

		return view;
	}

	@PostMapping(value = SIGNUP_ENDPOINT + "/{id}/reset_password/",
			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView confirmPassword(@PathVariable("id") Long userId,
										@RequestParam Map<String, String> params) {
		String token = params.get("token");

		ConfirmPasswordDto dto = new ConfirmPasswordDto();
		dto.setPassword(params.get("password"));
		dto.setConfirmPassword(params.get("confirmPassword"));

		String viewName = AuthTemplateViewID.RESET_PASSWORD_DONE.getId();

		ModelAndView view;

		try {
			this.serviceSupplier.confirmPassword(token, dto);

			view = new ModelAndView(viewName, HttpStatus.OK);

		} catch (AuthenticationException ex) {
			viewName = AuthTemplateViewID.RESET_PASSWORD_FAIL.getId();

			view = new ModelAndView(viewName, "error", ex.getMessage());

			view.setStatus(HttpStatus.UNAUTHORIZED);
		}

		return view;
	}


	@GetMapping(REFRESH_TOKEN_ENDPOINT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ResponseBody
	public void refreshToken(HttpServletRequest request, HttpServletResponse response,
							 Authentication tokenAuth)
			throws IOException, ServletException {
		AuthUserAuthentication authentication = this.serviceSupplier.refreshToken(tokenAuth);

		this.securityContext.authenticationSuccessHandler()
				.onAuthenticationSuccess(request, response, authentication);
	}


	@GetMapping(SIGNIN_ENDPOINT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ResponseBody
	public void signOut() {
		// Nothing to do.
	}

}
