package com.yuhubs.ms.auth.model;

import com.yuhubs.ms.model.Entity;
import com.yuhubs.ms.security.GrantedRolePermission;
import com.yuhubs.ms.security.auth.AccountStatus;
import com.yuhubs.ms.security.auth.AuthUser;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GenericAuthUser implements AuthUser, Entity<GenericAuthUser> {

	public static final class Permission implements GrantedRolePermission {

		private final String permission;


		public Permission(String permission) {
			this.permission = permission;
		}


		@Override
		public String getRolePermission() {
			return permission;
		}

	}


	protected final AuthUserGeneralValue generalValue;

	protected final ValueAccountStatus accountStatus;

	protected AuthUserProfileValue profile;


	public GenericAuthUser(AuthUserGeneralValue generalValue) {
		this.generalValue = generalValue;
		this.accountStatus = createAccountStatus();

		this.accountStatus.setValue(generalValue.getStatus());
	}


	@Override
	public boolean sameIdentityAs(GenericAuthUser other) {
		return this.generalValue.sameValueAs(other.generalValue);
	}


	@Override
	public Long getId() {
		return this.generalValue.getId();
	}

	@Override
	public List<? extends GrantedRolePermission> getRolePermissions() {
		List<GrantedRolePermission> list = Arrays
				.stream(this.generalValue.getPermissions().split(","))
				.map(String::trim)
				.filter(StringUtils::hasText)
				.map(Permission::new)
				.collect(Collectors.toList());

		return list;
	}

	@Override
	public String getPasswordHash() {
		return this.generalValue.getPasswordHash();
	}

	@Override
	public void setPasswordHash(String passwordHash) {
		this.generalValue.setPasswordHash(passwordHash);
	}

	@Override
	public ValueAccountStatus getAccountStatus() {
		return this.accountStatus;
	}

	@Override
	public void setAccountStatus(AccountStatus.Op op) {
		this.accountStatus.setAccountStatus(op);
		this.generalValue.setStatus(this.accountStatus.getValue());
	}

	@Override
	public AuthUserProfileValue getProfile() {
		return profile;
	}


	public void setProfile(AuthUserProfileValue profile) {
		this.profile = profile;
	}

	public AuthUserGeneralValue generalValue() {
		return this.generalValue;
	}


	protected ValueAccountStatus createAccountStatus() {
		return new ValueAccountStatus();
	}

}
