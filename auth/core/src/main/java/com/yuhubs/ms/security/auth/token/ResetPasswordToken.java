package com.yuhubs.ms.security.auth.token;

import com.yuhubs.ms.security.RolePermissionsAuthentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class ResetPasswordToken extends RolePermissionsAuthentication {

	public ResetPasswordToken(Object principle,
							  Collection<? extends GrantedAuthority> authorities) {
		super(principle, authorities);
	}


	public static ResetPasswordToken of(Long userId, String permissions) {
		return new ResetPasswordToken(userId, authoritiesOf(permissions));
	}

	public static ResetPasswordToken of(Long userId) {
		return of(userId, "USER-RESET_PASSWORD");
	}

}
