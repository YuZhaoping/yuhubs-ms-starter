package com.yuhubs.ms.auth.mock;

import com.yuhubs.ms.security.GrantedRolePermission;
import com.yuhubs.ms.security.auth.AccountStatus;
import com.yuhubs.ms.security.auth.AuthUser;
import com.yuhubs.ms.security.auth.AuthUserProfile;
import com.yuhubs.ms.security.auth.domain.ValueAccountStatus;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MockAuthUser implements AuthUser {

	private final Long id;

	private final String permissions;

	private String passwordHash;

	private final ValueAccountStatus accountStatus;

	private AuthUserProfile profile;


	public MockAuthUser(Long id, String permissions) {
		this.id = id;
		this.permissions = permissions;
		this.accountStatus = new ValueAccountStatus();
	}


	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public List<? extends GrantedRolePermission> getRolePermissions() {
		List<GrantedRolePermission> list = Arrays
				.stream(permissions.split(","))
				.map(String::trim)
				.filter(StringUtils::hasText)
				.map(Permission::new)
				.collect(Collectors.toList());

		return list;
	}

	@Override
	public String getPasswordHash() {
		return this.passwordHash;
	}

	@Override
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	@Override
	public ValueAccountStatus getAccountStatus() {
		return this.accountStatus;
	}

	@Override
	public void setAccountStatus(AccountStatus.Op op) {
		switch (op) {
			case SET_ACCOUNT_EXPIRED:
				this.accountStatus.setAccountExpired();
				break;
			case UNSET_ACCOUNT_EXPIRED:
				this.accountStatus.setAccountNonExpired();
				break;
			case SET_ACCOUNT_LOCKED:
				this.accountStatus.setAccountLocked();
				break;
			case UNSET_ACCOUNT_LOCKED:
				this.accountStatus.setAccountNonLocked();
				break;
			case SET_CREDENTIALS_EXPIRED:
				this.accountStatus.setCredentialsExpired();
				break;
			case UNSET_CREDENTIALS_EXPIRED:
				this.accountStatus.setCredentialsNonExpired();
				break;
			case SET_ACCOUNT_DISABLED:
				this.accountStatus.setAccountDisabled();
				break;
			case UNSET_ACCOUNT_DISABLED:
				this.accountStatus.setAccountEnabled();
				break;
			case SET_EMAIL_VERIFIED:
				this.accountStatus.setEmailVerified();
				break;
			case UNSET_EMAIL_VERIFIED:
				this.accountStatus.setEmailNotVerified();
				break;
		}
	}

	@Override
	public AuthUserProfile getProfile() {
		return profile;
	}

	public void setProfile(AuthUserProfile profile) {
		this.profile = profile;
	}


	private static final class Permission implements GrantedRolePermission {

		private final String permission;


		public Permission(String permission) {
			this.permission = permission;
		}


		@Override
		public String getRolePermission() {
			return permission;
		}

	}

}
