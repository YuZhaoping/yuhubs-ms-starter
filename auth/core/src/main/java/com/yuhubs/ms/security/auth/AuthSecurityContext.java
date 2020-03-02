package com.yuhubs.ms.security.auth;

import com.yuhubs.ms.security.JwtTokenServiceContext;
import com.yuhubs.ms.security.token.JwtTokenService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthSecurityContext {

	protected final JwtTokenServiceContext jwtContext;

	protected final AuthProperties authProperties;

	protected final PasswordEncoder passwordEncoder;


	public AuthSecurityContext(JwtTokenServiceContext context) {
		this.jwtContext = context;
		this.authProperties = new AuthProperties(context.getSecurityProperties());
		this.passwordEncoder = createPasswordEncoder();
	}


	protected PasswordEncoder createPasswordEncoder() {
		return new BCryptPasswordEncoder(10);
	}


	public final AuthProperties authProperties() {
		return this.authProperties;
	}

	public final JwtTokenService jwtTokenService() {
		return this.jwtContext.getJwtTokenService();
	}

	public final PasswordEncoder passwordEncoder() {
		return this.passwordEncoder;
	}

}
