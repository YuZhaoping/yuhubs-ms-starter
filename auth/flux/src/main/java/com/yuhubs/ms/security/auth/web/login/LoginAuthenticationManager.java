package com.yuhubs.ms.security.auth.web.login;

import com.yuhubs.ms.security.auth.AccountChecker;
import com.yuhubs.ms.security.auth.AuthSecurityContext;
import com.yuhubs.ms.security.auth.AuthUserAuthentication;
import com.yuhubs.ms.security.auth.details.AuthUserDetails;
import com.yuhubs.ms.security.auth.exceptions.LoginFailureException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

public final class LoginAuthenticationManager implements ReactiveAuthenticationManager {

	private final AuthSecurityContext context;

	private final ReactiveUserDetailsService userDetailsService;


	public LoginAuthenticationManager(AuthSecurityContext context,
									  ReactiveUserDetailsService userDetailsService) {
		this.context = context;
		this.userDetailsService = userDetailsService;
	}


	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		if (supports(authentication.getClass())) {
			return doAuthenticate(authentication);
		}
		return Mono.empty();
	}

	private Mono<Authentication> doAuthenticate(Authentication authentication) {
		final String username = (String) authentication.getPrincipal();
		final String password = (String) authentication.getCredentials();

		return retrieveUser(username)
				.filter(u -> this.passwordEncoder().matches(password, u.getPassword()))
				.switchIfEmpty(Mono.defer(() -> Mono.error(new LoginFailureException("Invalid username or password"))))
				.doOnNext(this::checkAccountStatus)
				.map(this::toAuthenticationToken);
	}

	private Mono<UserDetails> retrieveUser(String username) {
		return this.userDetailsService.findByUsername(username);
	}

	private void checkAccountStatus(UserDetails userDetails) {
		AuthUserDetails authUserDetails = (AuthUserDetails) userDetails;

		AccountChecker.checkAccountStatus(authUserDetails.getAccountStatus());
	}

	private Authentication toAuthenticationToken(UserDetails userDetails) {
		AuthUserDetails authUserDetails = (AuthUserDetails) userDetails;

		return AuthUserAuthentication.of(authUserDetails);
	}


	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

	private final PasswordEncoder passwordEncoder() {
		return this.context.passwordEncoder();
	}

}
