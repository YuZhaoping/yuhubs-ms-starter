package com.yuhubs.ms.security.auth.web;

public interface AuthApiEndpoints {

	String AUTH_BASE_URL = "/auth/api";

	String SIGNIN_ENDPOINT = AUTH_BASE_URL + "/login";
	String SIGNOUT_ENDPOINT = AUTH_BASE_URL + "/logout";
	String SIGNUP_ENDPOINT = AUTH_BASE_URL + "/users";

	String RESET_PASSWORD_ENDPOINT = AUTH_BASE_URL + "/reset_password";

	String REFRESH_TOKEN_ENDPOINT = AUTH_BASE_URL + "/refresh_token";

}
