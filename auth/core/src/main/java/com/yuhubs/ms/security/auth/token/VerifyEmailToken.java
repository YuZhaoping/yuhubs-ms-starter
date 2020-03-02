package com.yuhubs.ms.security.auth.token;

import com.yuhubs.ms.security.RolePermissionsAuthentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class VerifyEmailToken extends RolePermissionsAuthentication {

	public VerifyEmailToken(Object principle,
							Collection<? extends GrantedAuthority> authorities) {
		super(principle, authorities);
	}


	public static VerifyEmailToken of(Long userId, String permissions) {
		return new VerifyEmailToken(userId, authoritiesOf(permissions));
	}

	public static VerifyEmailToken of(Long userId) {
		return of(userId, "USER-VERIFY_EMAIL");
	}

}
