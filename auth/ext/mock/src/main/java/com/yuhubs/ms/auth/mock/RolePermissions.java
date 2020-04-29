package com.yuhubs.ms.auth.mock;

public class RolePermissions {

	public String getUserPermissions(String username) {
		return getRolePermissions(getUserRole(username));
	}

	public String getUserRole(String username) {
		return "USER";
	}

	public String getRolePermissions(String role) {
		return "USE-DATA";
	}

}
