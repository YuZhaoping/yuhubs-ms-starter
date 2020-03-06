package com.yuhubs.ms.security.web.handler;

import com.yuhubs.ms.security.jwt.JwtTokenService;
import com.yuhubs.ms.security.web.SecurityHandlerSupplier;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenByAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private static final String X_SET_AUTHORIZATION_BEARER_HEADER = "X-Set-Authorization-Bearer";

	private final SecurityHandlerSupplier supplier;

	private volatile int expirationMinutes;


	public TokenByAuthenticationSuccessHandler(SecurityHandlerSupplier supplier) {
		this.supplier = supplier;
		this.expirationMinutes = -1;
	}


	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication)
			throws IOException, ServletException {

		int minutes = getExpirationMinutes();

		String jwtToken = jwtTokenService().createJwtToken(authentication, minutes);

		response.setHeader(X_SET_AUTHORIZATION_BEARER_HEADER, jwtToken);
	}


	private final int getExpirationMinutes() {
		if (this.expirationMinutes < 0) {
			this.expirationMinutes = this.supplier.securityProperties().getJwtTokenExpiration();
		}
		return this.expirationMinutes;
	}

	private final JwtTokenService jwtTokenService() {
		return this.supplier.jwtTokenService();
	}

}
