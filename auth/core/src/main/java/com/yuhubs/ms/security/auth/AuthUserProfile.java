package com.yuhubs.ms.security.auth;

import java.io.Serializable;

public class AuthUserProfile implements Serializable {

	private static final long serialVersionUID = 1L;


	private String name;

	private String groups;


	public AuthUserProfile() {
	}

	public AuthUserProfile(String name, String groups) {
		this.name = name;
		this.groups = groups;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

}
