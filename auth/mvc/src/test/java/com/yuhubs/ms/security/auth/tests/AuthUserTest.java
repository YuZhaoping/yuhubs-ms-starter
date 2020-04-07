package com.yuhubs.ms.security.auth.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuhubs.ms.auth.mock.MockAuthUserService;
import com.yuhubs.ms.security.auth.SignUpRequest;
import com.yuhubs.ms.security.auth.WebConfiguredTestBase;
import com.yuhubs.ms.security.auth.web.AuthApiEndpoints;
import com.yuhubs.ms.security.auth.web.dto.LoginRequestDto;
import com.yuhubs.ms.security.auth.web.dto.SignUpRequestDto;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthUserTest extends WebConfiguredTestBase implements AuthApiEndpoints {

	private static final String X_SET_AUTHORIZATION_BEARER_HEADER = "X-Set-Authorization-Bearer";

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String TOKEN_PREFIX = "Bearer ";


	@Autowired
	private ObjectMapper objectMapper;

	private JacksonTester<LoginRequestDto> loginJson;
	private JacksonTester<SignUpRequestDto> signUpJson;


	@Override
	@Before
	public void setUp() {
		super.setUp();

		JacksonTester.initFields(this, objectMapper);

		resetUsers();
	}


	@Test
	public void testSignUp() throws Exception {
		String email = "u001@yuhubs.com";
		String username = "u001";
		String password = "u001@Yuhubs";

		SignUpRequestDto dto = new SignUpRequestDto(email, username, password);
		JsonContent<SignUpRequestDto> json = signUpJson.write(dto);

		MvcResult result = this.mockMvc.perform(
				post(SIGNUP_ENDPOINT)
						.contentType(APPLICATION_JSON)
						.content(json.getJson()))
				//.andDo(print())
				.andExpect(status().isNoContent())
				.andReturn();

		String jwtToken = result.getResponse().getHeader(X_SET_AUTHORIZATION_BEARER_HEADER);
		assertNotNull(jwtToken);

		doTestLoginSuccess(email, password);
	}

	@Test
	public void testLogin() throws Exception {
		doTestLoginSuccess("root", "root@Yuhubs");
	}

	@Test
	public void testLoginWithInvalidUsername() throws Exception {
		doTestLoginFailure("test", "root@Yuhubs");
	}

	@Test
	public void testLoginWithInvalidPassword() throws Exception {
		doTestLoginFailure("root", "test@Yuhubs");
	}


	private void doTestLoginSuccess(String username, String password) throws Exception {
		LoginRequestDto dto = new LoginRequestDto(username, password);
		JsonContent<LoginRequestDto> json = loginJson.write(dto);

		MvcResult result = this.mockMvc.perform(
				post(SIGNIN_ENDPOINT)
						.contentType(APPLICATION_JSON)
						.content(json.getJson()))
				//.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		String jwtToken = result.getResponse().getHeader(X_SET_AUTHORIZATION_BEARER_HEADER);
		assertNotNull(jwtToken);

		this.mockMvc.perform(
				get("/test")
						.header(AUTHORIZATION_HEADER, createBearerToken(jwtToken))
						.contentType(APPLICATION_JSON))
				//.andDo(print())
				.andExpect(status().isNoContent());
	}

	private void doTestLoginFailure(String username, String password) throws Exception {
		LoginRequestDto dto = new LoginRequestDto(username, password);
		JsonContent<LoginRequestDto> json = loginJson.write(dto);

		MvcResult result = this.mockMvc.perform(
				post(SIGNIN_ENDPOINT)
						.contentType(APPLICATION_JSON)
						.content(json.getJson()))
				//.andDo(print())
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("error.statusCode", Matchers.is(401)))
				.andExpect(jsonPath("error.code", Matchers.is(401)))
				.andReturn();
	}


	private void resetUsers() {
		MockAuthUserService authUserService = authUserService();
		authUserService.mockUserManager().clearUsers();

		String passwordHash = this.securityContext.passwordEncoder().encode("root@Yuhubs");
		String rootEmail = "root@yuhubs.com";

		SignUpRequest request = new SignUpRequest();
		request.setEmail(rootEmail).setUsername("root").setPassword(passwordHash);

		authUserService.signUpUser(request);
	}


	private static String createBearerToken(String token) {
		StringBuilder buf = new StringBuilder(token.length() + TOKEN_PREFIX.length());
		buf.append(TOKEN_PREFIX).append(token);
		return buf.toString();
	}

}
