package com.yuhubs.ms.security.auth.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuhubs.ms.auth.mock.MockAuthUserService;
import com.yuhubs.ms.security.auth.SignUpRequest;
import com.yuhubs.ms.security.auth.WebConfiguredTestBase;
import com.yuhubs.ms.security.auth.web.AuthApiEndpoints;
import com.yuhubs.ms.security.auth.web.dto.LoginRequestDto;
import com.yuhubs.ms.security.auth.web.dto.SignUpRequestDto;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.util.function.Consumer;

import static org.junit.Assert.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public class AuthUserTest extends WebConfiguredTestBase implements AuthApiEndpoints {

	private static final String X_SET_AUTHORIZATION_BEARER_HEADER = "X-Set-Authorization-Bearer";

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String TOKEN_PREFIX = "Bearer ";


	@Autowired
	private ObjectMapper objectMapper;

	private JacksonTester<LoginRequestDto> loginJson;
	private JacksonTester<SignUpRequestDto> signUpJson;


	private static class JwtTokenValue implements Consumer<String> {

		private String jwtToken = null;

		@Override
		public void accept(String str) {
			this.jwtToken = str;
		}

		public String value() {
			return this.jwtToken;
		}

	}


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

		final JwtTokenValue jwtToken = new JwtTokenValue();

		this.client.post().uri(SIGNUP_ENDPOINT).accept(APPLICATION_JSON)
				.contentType(APPLICATION_JSON)
				.bodyValue(json.getJson())
				.exchange()
				.expectStatus().isNoContent()
				.expectHeader().exists(X_SET_AUTHORIZATION_BEARER_HEADER)
				.expectHeader().value(X_SET_AUTHORIZATION_BEARER_HEADER, jwtToken);

		assertNotNull(jwtToken.value());

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

		final JwtTokenValue jwtToken = new JwtTokenValue();

		this.client.post().uri(SIGNIN_ENDPOINT).accept(APPLICATION_JSON)
				.contentType(APPLICATION_JSON)
				.bodyValue(json.getJson())
				.exchange()
				.expectStatus().isNoContent()
				.expectHeader().exists(X_SET_AUTHORIZATION_BEARER_HEADER)
				.expectHeader().value(X_SET_AUTHORIZATION_BEARER_HEADER, jwtToken);

		assertNotNull(jwtToken.value());

		this.client.get().uri("/test").accept(APPLICATION_JSON)
				.header(AUTHORIZATION_HEADER, createBearerToken(jwtToken.value()))
				.exchange()
				.expectStatus().isNoContent();
	}

	private void doTestLoginFailure(String username, String password) throws Exception {
		LoginRequestDto dto = new LoginRequestDto(username, password);
		JsonContent<LoginRequestDto> json = loginJson.write(dto);

		this.client.post().uri(SIGNIN_ENDPOINT).accept(APPLICATION_JSON)
				.contentType(APPLICATION_JSON)
				.bodyValue(json.getJson())
				.exchange()
				.expectStatus().isUnauthorized()
				.expectBody()
				//.consumeWith(body -> System.out.println(body))
				.jsonPath("error.statusCode").isEqualTo(401);
	}


	private void resetUsers() {
		MockAuthUserService authUserService = authUserService();
		authUserService.mockUserManager().clearUsers();

		String passwordHash = this.securityContext.passwordEncoder().encode("root@Yuhubs");
		String rootEmail = "root@yuhubs.com";

		SignUpRequest request = new SignUpRequest();
		request.setEmail(rootEmail).setUsername("root").setPassword(passwordHash);

		authUserService.mockUserManager().signUpUser(request);
	}


	private static String createBearerToken(String token) {
		StringBuilder buf = new StringBuilder(token.length() + TOKEN_PREFIX.length());
		buf.append(TOKEN_PREFIX).append(token);
		return buf.toString();
	}

}
