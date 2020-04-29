package com.yuhubs.ms.security.web.jwt;

import com.yuhubs.ms.security.jwt.JwtAuthenticationToken;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		if (supports(authentication.getClass())) {
			// JwtAuthenticationToken is always authenticated, as JwtSecurityContextRepository has already
			// checked them through JwtTokenService.
			return Mono.just(authentication);
		}

		return Mono.empty();
	}

	public boolean supports(Class<?> authentication) {
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
