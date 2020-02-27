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


	private final AuthUserService userService;


	public AuthUserDetailsService(AuthUserService userService) {
		this.userService = userService;
	}


	@Override
	public Mono<UserDetails> findByUsername(final String username) {
		Mono<AuthUser> user;

		if (isNumeric(username)) {
			user = this.userService.getUserById(Long.valueOf(username));
		} else {
			user = this.userService.getUserByName(username);
		}

		user.switchIfEmpty(Mono.defer(() -> {
			return Mono.error(new UsernameNotFoundException("User not found by '" + username + "'"));
		})).map(AuthUserDetails::of);

		return null;
	}


	private static boolean isNumeric(String str) {
		return NUMERIC_PATTERN.matcher(str).matches();
	}

}
