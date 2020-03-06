package com.yuhubs.ms.security.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

	static final String AUTHORITIES = "auth";
	static final String DETAILS = "prof";

	private final Long userId;


	public JwtAuthenticationToken(Long userId, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.userId = userId;
	}


	public static JwtAuthenticationToken of(Claims claims) {
		Long userId = Long.valueOf(claims.getSubject());

		Collection<GrantedAuthority> authorities = Arrays
				.stream(String.valueOf(claims.get(AUTHORITIES)).split(","))
				.map(String::trim)
				.filter(StringUtils::hasText)
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toSet());

		JwtAuthenticationToken token = new JwtAuthenticationToken(userId, authorities);

		Object details = claims.get(DETAILS);
		if (details != null) {
			token.setDetails(details);
		}

		token.setAuthenticated(isClaimsNotExpired(claims));

		return token;
	}

	private static boolean isClaimsNotExpired(Claims claims) {
		/* Already checked by Jwts parser (since 0.3)
		final Date now = Date.from(Instant.now());
		final Date expiration = claims.getExpiration();
		final Date notBefore = Optional.ofNullable(claims.getNotBefore()).orElse(now);
		return now.after(notBefore) && now.before(expiration);
		*/
		return true;
	}


	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return this.userId;
	}

}
