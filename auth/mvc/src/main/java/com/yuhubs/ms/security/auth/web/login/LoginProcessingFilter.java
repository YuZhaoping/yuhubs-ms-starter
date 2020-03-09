package com.yuhubs.ms.security.auth.web.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuhubs.ms.security.auth.web.dto.LoginRequestDto;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public final class LoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

	private final ObjectMapper objectMapper;


	public LoginProcessingFilter(String filterProcessesUrl, ObjectMapper objectMapper) {
		super(new AntPathRequestMatcher(filterProcessesUrl, "POST"));

		this.objectMapper = objectMapper;
	}


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: " + request.getMethod());
		}

		LoginRequestDto dto = this.objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);

		String username = Optional
				.ofNullable(dto.getUsername())
				.map(String::trim)
				.orElse("");

		String password = Optional
				.ofNullable(dto.getPassword())
				.map(String::trim)
				.orElse("");

		UsernamePasswordAuthenticationToken token =
				new UsernamePasswordAuthenticationToken(username, password);

		return this.getAuthenticationManager().authenticate(token);
	}

}
