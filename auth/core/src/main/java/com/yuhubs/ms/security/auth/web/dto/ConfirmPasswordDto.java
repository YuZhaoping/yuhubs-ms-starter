package com.yuhubs.ms.security.auth.web.dto;

public class ConfirmPasswordDto {

	private String password;

	private String confirmPassword;


	public ConfirmPasswordDto() {
	}

	public ConfirmPasswordDto(String password, String confirmPassword) {
		this.password = password;
		this.confirmPassword = confirmPassword;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String password) {
		this.confirmPassword = password;
	}

}
