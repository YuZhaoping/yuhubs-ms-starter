package com.yuhubs.ms.security.auth.web.login;

import com.yuhubs.ms.security.auth.AccountChecker;
import com.yuhubs.ms.security.auth.AuthSecurityContext;
import com.yuhubs.ms.security.auth.AuthUserAuthentication;
import com.yuhubs.ms.security.auth.details.AuthUserDetails;
import com.yuhubs.ms.security.auth.exceptions.LoginFailureException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class LoginAuthenticationProvider implements AuthenticationProvider {

	private final AuthSecurityContext context;

	private final UserDetailsService userDetailsService;


	public LoginAuthenticationProvider(AuthSecurityContext context,
									   UserDetailsService userDetailsService) {
		this.context = context;

		this.userDetailsService = userDetailsService;
	}


	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();

		UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

		if (!passwordEncoder().matches(password, userDetails.getPassword())) {
			throw new LoginFailureException("Invalid username or password");
		}

		AuthUserDetails authUserDetails = (AuthUserDetails) userDetails;

		AccountChecker.checkAccountStatus(authUserDetails.getAccountStatus());

		return AuthUserAuthentication.of(authUserDetails);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}


	private final PasswordEncoder passwordEncoder() {
		return this.context.passwordEncoder();
	}

}
