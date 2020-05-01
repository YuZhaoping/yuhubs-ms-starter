package com.yuhubs.ms.auth.model;

import com.yuhubs.ms.model.ValueObject;
import com.yuhubs.ms.security.auth.AuthUserProfile;

import java.util.Objects;

public class AuthUserProfileValue extends AuthUserProfile
		implements ValueObject<AuthUserProfileValue> {

	private static final long serialVersionUID = 1L;


	@Override
	public boolean sameValueAs(AuthUserProfileValue other) {
		return Objects.equals(this.getName(), other.getName());
	}

}
