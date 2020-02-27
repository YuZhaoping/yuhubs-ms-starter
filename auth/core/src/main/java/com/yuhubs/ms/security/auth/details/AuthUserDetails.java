package com.yuhubs.ms.security.auth.details;

import com.yuhubs.ms.security.GrantedRolePermission;
import com.yuhubs.ms.security.auth.AccountStatus;
import com.yuhubs.ms.security.auth.AuthUser;
import com.yuhubs.ms.security.auth.AuthUserProfile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public final class AuthUserDetails implements UserDetails {

	private final String userId;

	private final Collection<SimpleGrantedAuthority> authorities;

	private final String passwordHash;

	private final AccountStatus accountStatus;

	private final AuthUserProfile profile;


	private AuthUserDetails(AuthUser user,
							Collection<SimpleGrantedAuthority> authorities) {
		this.userId = user.getId().toString();

		this.authorities = authorities;

		this.passwordHash = user.getPasswordHash();

		this.accountStatus = user.getAccountStatus();

		this.profile = user.getProfile();
	}


	public static AuthUserDetails of(AuthUser user) {
		Collection<SimpleGrantedAuthority> authorities = userAuthorities(user);

		return new AuthUserDetails(user, authorities);
	}

	public static Collection<SimpleGrantedAuthority> userAuthorities(AuthUser user) {
		Collection<SimpleGrantedAuthority> authorities =
				user.getRolePermissions().stream()
				.map(GrantedRolePermission::getRolePermission)
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toSet());

		return authorities;
	}


	public AccountStatus getAccountStatus() {
		return this.accountStatus;
	}

	public AuthUserProfile getProfile() {
		return profile;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.passwordHash;
	}

	@Override
	public String getUsername() {
		return this.userId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountStatus.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountStatus.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.accountStatus.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return this.accountStatus.isEnabled();
	}

}
