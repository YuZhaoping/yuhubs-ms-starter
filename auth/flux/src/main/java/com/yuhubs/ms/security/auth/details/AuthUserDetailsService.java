package com.yuhubs.ms.security.auth.details;

import com.yuhubs.ms.security.auth.AuthUser;
import com.yuhubs.ms.security.auth.AuthUserService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Mono;

import java.util.regex.Pattern;

public class AuthUserDetailsService implements ReactiveUserDetailsService {

	private static final Pattern NUMERIC_PATTERN = Pattern.compile(
			"^[-\\+]?[\\d]*$");


	private final AuthUserService.Provider provider;


	public AuthUserDetailsService(AuthUserService.Provider provider) {
		this.provider = provider;
	}


	@Override
	public Mono<UserDetails> findByUsername(final String username) {
		Mono<AuthUser> user;

		final AuthUserService userService = this.provider.authUserService();

		if (isNumeric(username)) {
			user = userService.getUserById(Long.valueOf(username));
		} else {
			user = userService.getUserByName(username);
		}

		return user.switchIfEmpty(Mono.defer(() -> {
			return Mono.error(new UsernameNotFoundException("User not found by '" + username + "'"));
		})).map(AuthUserDetails::of);
	}


	private static boolean isNumeric(String str) {
		return NUMERIC_PATTERN.matcher(str).matches();
	}

}
