package com.yuhubs.ms.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class RolePermissionsAuthentication extends AbstractAuthenticationToken {

	protected final Object principle;


	public RolePermissionsAuthentication(Object principle,
										 Collection<? extends GrantedAuthority> authorities) {
		super(authorities);

		this.principle = principle;

		setAuthenticated(true);
	}


	public static Collection<? extends GrantedAuthority> authoritiesOf(String permissions) {

		Collection<SimpleGrantedAuthority> authorities =
				Arrays.stream(permissions.split(","))
						.map(String::trim)
						.filter(StringUtils::hasText)
						.map(SimpleGrantedAuthority::new)
						.collect(Collectors.toSet());

		return authorities;
	}

	public static Collection<? extends GrantedAuthority> authoritiesOf(
			Collection<? extends GrantedRolePermission> permissions) {

		Collection<SimpleGrantedAuthority> authorities =
				permissions.stream()
						.map(GrantedRolePermission::getRolePermission)
						.map(SimpleGrantedAuthority::new)
						.collect(Collectors.toSet());

		return authorities;
	}


	public static RolePermissionsAuthentication of(Object principle, String permissions) {

		Collection<? extends GrantedAuthority> authorities = authoritiesOf(permissions);

		RolePermissionsAuthentication authentication =
				new RolePermissionsAuthentication(principle, authorities);

		return authentication;
	}

	public static RolePermissionsAuthentication of(Object principle,
												   Collection<? extends GrantedRolePermission> permissions) {

		Collection<? extends GrantedAuthority> authorities = authoritiesOf(permissions);

		RolePermissionsAuthentication authentication =
				new RolePermissionsAuthentication(principle, authorities);

		return authentication;
	}


	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return this.principle;
	}

}
