package com.yuhubs.ms.auth.model;

import com.yuhubs.ms.model.ValueObject;

import java.util.Objects;

public class AuthUserGeneralValue
		implements ValueObject<AuthUserGeneralValue> {

	private static final long serialVersionUID = 1L;


	private Long id;

	private String permissions;

	private String passwordHash;

	private int status;


	@Override
	public boolean sameValueAs(AuthUserGeneralValue other) {
		return Objects.equals(this.id, other.id);
	}


	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

}
