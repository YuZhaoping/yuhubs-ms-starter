package com.yuhubs.ms.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public interface JwtTokenService {

	String createJwtToken(Authentication authentication, int minutes);

	Authentication parseJwtToken(String jwtToken) throws AuthenticationException;

}
