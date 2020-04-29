package com.yuhubs.ms.security.web.jwt;

import com.yuhubs.ms.security.jwt.JwtAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public final class JwtAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// JwtAuthenticationToken is always authenticated, as JwtAuthenticationFilter has already
		// checked them through JwtTokenService.
		return authentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
