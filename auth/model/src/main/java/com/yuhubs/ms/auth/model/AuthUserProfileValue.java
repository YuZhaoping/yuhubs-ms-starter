package com.yuhubs.ms.auth.model;

import com.yuhubs.ms.model.ValueObject;
import com.yuhubs.ms.security.auth.AuthUserProfile;

import java.util.Objects;

public class AuthUserProfileValue extends AuthUserProfile
		implements ValueObject<AuthUserProfileValue> {

	private static final long serialVersionUID = 1L;


	private String email;


	public AuthUserProfileValue(String name) {
		super(name);
	}

	public AuthUserProfileValue() {
		super();
	}


	@Override
	public boolean sameValueAs(AuthUserProfileValue other) {
		return Objects.equals(this.getName(), other.getName());
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
