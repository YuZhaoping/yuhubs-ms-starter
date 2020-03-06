package com.yuhubs.ms.auth.mock.config;

import com.yuhubs.ms.auth.mock.RolePermissions;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class MockUserConfigSupport extends RolePermissions {

	private Map<String, String> roles = new HashMap<>();

	private List<MockUser> users = new ArrayList<>();


	public MockUserConfigSupport() {
	}


	@Override
	public String getUserRole(String username) {
		MockUser user = findUserByName(username);
		if (user != null) {
			String role = user.getRole();
			if (!StringUtils.isEmpty(role)) {
				return role;
			}
		}

		return super.getUserRole(username);
	}

	private MockUser findUserByName(final String username) {
		return getUsers().stream()
				.filter(user -> username.equals(user.getName()))
				.findAny()
				.orElse(null);
	}


	@Override
	public String getRolePermissions(String role) {
		String permissions = getRoles().get(role);
		if (!StringUtils.isEmpty(permissions)) {
			return permissions;
		}

		return super.getRolePermissions(role);
	}


	public Map<String, String> getRoles() {
		return roles;
	}

	public void setRoles(Map<String, String> roles) {
		this.roles = roles;
	}

	public List<MockUser> getUsers() {
		return users;
	}

	public void setUsers(List<MockUser> users) {
		this.users = users;
	}

}
