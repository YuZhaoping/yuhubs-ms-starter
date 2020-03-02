package com.yuhubs.ms.security.auth;

import com.yuhubs.ms.security.RolePermissionsAuthentication;
import com.yuhubs.ms.security.auth.details.AuthUserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthUserAuthentication extends RolePermissionsAuthentication {

	public AuthUserAuthentication(Object principle,
								  Collection<? extends GrantedAuthority> authorities) {
		super(principle, authorities);
	}


	public static AuthUserAuthentication of(AuthUser authUser) {

		Collection<? extends GrantedAuthority> authorities =
				authoritiesOf(authUser.getRolePermissions());

		AuthUserAuthentication authentication =
				new AuthUserAuthentication(authUser.getId(), authorities);

		authentication.setDetails(authUser.getProfile());

		return authentication;
	}

	public static AuthUserAuthentication of(AuthUserDetails userDetails) {
		AuthUserAuthentication authentication =
				new AuthUserAuthentication(userDetails.getUsername(),
						userDetails.getAuthorities());

		authentication.setDetails(userDetails.getProfile());

		return authentication;
	}

}
