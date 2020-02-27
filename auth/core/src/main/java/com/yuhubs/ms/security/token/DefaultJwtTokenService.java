package com.yuhubs.ms.security.token;

import com.yuhubs.ms.security.SecurityProperties;
import com.yuhubs.ms.security.exceptions.*;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

public class DefaultJwtTokenService implements JwtTokenService {

	private final byte[] secretkey;


	public DefaultJwtTokenService(String secretkey) {
		this.secretkey = Base64.getDecoder().decode(secretkey);
	}

	public DefaultJwtTokenService(SecurityProperties properties) {
		String key = properties.getJwtTokenSecretKey();
		this.secretkey = Base64.getDecoder().decode(key);
	}


	@Override
	public final String createJwtToken(Authentication authentication, int minutes) {
		Claims claims = Jwts.claims()
				.setId(UUID.randomUUID().toString())
				.setSubject(authentication.getName());

		setClaimsExpiration(claims, minutes);

		String authorities = authentication.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		claims.put(JwtAuthenticationToken.AUTHORITIES, authorities);

		Object details = authentication.getDetails();
		if (details != null) {
			claims.put(JwtAuthenticationToken.DETAILS, details);
		}

		return Jwts.builder()
				.setClaims(claims)
				.signWith(HS512, this.secretkey)
				.compact();
	}

	private static void setClaimsExpiration(Claims claims, int minutes) {
		Instant now = Instant.now();
		Instant expiration = now.plusSeconds(minutes * 60);

		claims.setExpiration(Date.from(expiration)).setIssuedAt(Date.from(now));
	}


	@Override
	public final Authentication parseJwtToken(String jwtToken) throws AuthenticationException {
		try {

			Claims claims = Jwts.parser().setSigningKey(this.secretkey)
					.parseClaimsJws(jwtToken)
					.getBody();

			return createJwtAuthenticationToken(claims);

		} catch (ExpiredJwtException e) {
			throw new JwtExpiredException(e.getMessage(), e);
		} catch (SignatureException | SecurityException e) {
			throw new BadCredentialsCodedException(e.getMessage(), e);
		} catch (IllegalStateException | UnsupportedJwtException e) {
			throw new JwtAuthenticationServiceException(e.getMessage(), e);
		} catch (ClaimJwtException e) {
			throw new InvalidJwtClaimException(e.getMessage(), e);
		} catch (JwtException e) {
			throw new InvalidJwtException(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			throw new IllegalJwtArgumentException(e.getMessage(), e);
		}
	}

	protected JwtAuthenticationToken createJwtAuthenticationToken(Claims claims) {
		return JwtAuthenticationToken.of(claims);
	}

}
