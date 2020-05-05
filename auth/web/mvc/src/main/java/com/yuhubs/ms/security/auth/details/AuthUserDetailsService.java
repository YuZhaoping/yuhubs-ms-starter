package com.yuhubs.ms.security.auth.details;

import com.yuhubs.ms.auth.model.AuthUsername;
import com.yuhubs.ms.auth.service.AuthUserService;
import com.yuhubs.ms.security.auth.AuthUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.regex.Pattern;

public class AuthUserDetailsService implements UserDetailsService {

	private static final Pattern NUMERIC_PATTERN = Pattern.compile(
			"^[-\\+]?[\\d]*$");


	private final AuthUserService.Provider provider;


	public AuthUserDetailsService(AuthUserService.Provider provider) {
		this.provider = provider;
	}


	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		final AuthUserService userService = this.provider.authUserService();

		Optional<AuthUser> user;

		if (isNumeric(username)) {
			user = userService.getUserById(Long.valueOf(username));
		} else {
			user = userService.getUserByName(AuthUsername.of(username));
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
