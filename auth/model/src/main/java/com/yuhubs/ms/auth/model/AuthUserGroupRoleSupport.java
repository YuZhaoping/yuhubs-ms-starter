package com.yuhubs.ms.auth.model;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class AuthUserGroupRoleSupport {

	public final String getRolesByGroups(String groups) {
		final StringBuilder result = new StringBuilder();

		rolesStreamByGroups(groups).forEach(role -> result.append(',').append(role));

		return (result.length() > 0) ? result.substring(1) : "";
	}

	public final String getPermissionsByGroups(String groups) {
		final StringBuilder result = new StringBuilder();

		permissionsStreamByRoles(rolesStreamByGroups(groups))
				.forEach(permission -> result.append(',').append(permission));

		return (result.length() > 0) ? result.substring(1) : "";
	}

	public final String getPermissionsByRoles(String roles) {
		final StringBuilder result = new StringBuilder();

		permissionsStreamByRoles(rolesStream(roles))
				.forEach(permission -> result.append(',').append(permission));

		return (result.length() > 0) ? result.substring(1) : "";
	}


	private final Stream<String> rolesStreamByGroups(String groups) {
		return streamOfString(groups)
				.map(this::getUserGroupRoles)
				.filter(Objects::nonNull)
				.flatMap(roles -> rolesStream(roles))
				.distinct();
	}

	private final Stream<String> permissionsStreamByRoles(Stream<String> rolesStream) {
		return rolesStream
				.map(this::getRolePermissions)
				.filter(Objects::nonNull)
				.flatMap(permissions -> permissionsStream(permissions))
				.distinct();
	}


	private static Stream<String> rolesStream(String roles) {
		return streamOfString(roles);
	}

	private static Stream<String> permissionsStream(String permissions) {
		return streamOfString(permissions);
	}

	private static Stream<String> streamOfString(String str) {
		return Arrays.stream(str.split(","))
				.map(String::trim)
				.filter(StringUtils::hasText);
	}


	public String getDefaultUserGroups() {
		return "USER";
	}

	public String getAllUserGroups() {
		return "ROOT, ADMIN, USER";
	}

	public String getUserGroupRoles(String userGroup) {
		switch (userGroup) {
			case "ROOT":  case "ADMIN":  case "USER":
				return userGroup;
		}
		return null;
	}

	public String getRolePermissions(String role) {
		switch (role) {
			case "ROOT": return "GRANT-ROLE";
			case "ADMIN": return "ADMIN-USER, USE-*";
			case "USER": return "USE-*";
		}
		return null;
	}

}
