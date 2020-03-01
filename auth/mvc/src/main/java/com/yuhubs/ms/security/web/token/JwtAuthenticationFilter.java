package com.yuhubs.ms.security.web.token;

import com.yuhubs.ms.security.web.SecurityHandlerSupplier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String TOKEN_PREFIX = "Bearer ";

	private final SecurityHandlerSupplier supplier;


	public JwtAuthenticationFilter(SecurityHandlerSupplier supplier) {
		this.supplier = supplier;
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
									FilterChain filterChain)
			throws ServletException, IOException {

		Authentication authentication = null;

		try {
			authentication = getAuthentication(request);
		} catch (AuthenticationException e) {
			SecurityContextHolder.clearContext();
			this.supplier.authenticationFailureHandler().onAuthenticationFailure(request, response, e);
			return;
		}

		if (authentication == null) {
			SecurityContextHolder.clearContext();
			filterChain.doFilter(request, response);
			return;
		}

		try {
			SecurityContextHolder.getContext().setAuthentication(authentication);
			filterChain.doFilter(request, response);
		} finally {
			SecurityContextHolder.clearContext();
		}
	}

	private Authentication getAuthentication(HttpServletRequest request)
			throws AuthenticationException {
		String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

		if (StringUtils.isEmpty(authorizationHeader)) {
			return null;
		}

		if (!StringUtils.substringMatch(authorizationHeader, 0, TOKEN_PREFIX)) {
			return null;
		}

		String jwtToken = authorizationHeader.substring(TOKEN_PREFIX.length());

		return this.supplier.jwtTokenService().parseJwtToken(jwtToken);
	}

}
