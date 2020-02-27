package com.yuhubs.ms.security.auth;

public class SignUpRequest {

	private String email;

	private String username;

	private String password;

	private int status;


	public SignUpRequest() {
	}

	public SignUpRequest(String email, String username, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
	}


	public String getEmail() {
		return email;
	}

	public SignUpRequest setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public SignUpRequest setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public SignUpRequest setPassword(String password) {
		this.password = password;
		return this;
	}

	public int getStatus() {
		return status;
	}

	public SignUpRequest setStatus(int status) {
		this.status = status;
		return this;
	}

}
