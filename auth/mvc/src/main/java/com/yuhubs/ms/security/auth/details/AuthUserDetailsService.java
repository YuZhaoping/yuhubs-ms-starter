package com.yuhubs.ms.security.auth.details;

import com.yuhubs.ms.security.auth.AuthUser;
import com.yuhubs.ms.security.auth.AuthUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.regex.Pattern;

public class AuthUserDetailsService implements UserDetailsService {

	private static final Pattern NUMERIC_PATTERN = Pattern.compile(
			"^[-\\+]?[\\d]*$");


	private final AuthUserService userService;


	public AuthUserDetailsService(AuthUserService userService) {
		this.userService = userService;
	}


	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		Optional<AuthUser> user;

		if (isNumeric(username)) {
			user = this.userService.getUserById(Long.valueOf(username));
		} else {
			user = this.userService.getUserByName(username);
		}

		if (!user.isPresent()) {
			throw new UsernameNotFoundException("User not found by '" + username + "'");
		}

		return AuthUserDetails.of(user.get());
	}


	private static boolean isNumeric(String str) {
		return NUMERIC_PATTERN.matcher(str).matches();
	}

}
