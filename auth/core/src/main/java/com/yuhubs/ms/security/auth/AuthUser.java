package com.yuhubs.ms.security.auth;

import com.yuhubs.ms.security.GrantedRolePermission;

import java.util.List;

public interface AuthUser {

	Long getId();

	List<? extends GrantedRolePermission> getRolePermissions();

	String getPasswordHash();

	void setPasswordHash(String passwordHash);

	AccountStatus getAccountStatus();

	void setAccountStatus(AccountStatus.Op op);

	AuthUserProfile getProfile();

}
