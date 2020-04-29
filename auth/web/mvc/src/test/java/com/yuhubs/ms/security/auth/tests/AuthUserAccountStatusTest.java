package com.yuhubs.ms.security.auth.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuhubs.ms.auth.mock.service.MockAuthUserService;
import com.yuhubs.ms.auth.model.ValueAccountStatus;
import com.yuhubs.ms.security.auth.SignUpRequest;
import com.yuhubs.ms.security.auth.WebConfiguredTestBase;
import com.yuhubs.ms.security.auth.web.AuthApiEndpoints;
import com.yuhubs.ms.security.auth.web.dto.LoginRequestDto;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthUserAccountStatusTest extends WebConfiguredTestBase implements AuthApiEndpoints {

	@Autowired
	private ObjectMapper objectMapper;

	private JacksonTester<LoginRequestDto> loginJson;


	@Override
	@Before
	public void setUp() {
		super.setUp();

		JacksonTester.initFields(this, objectMapper);

		resetUsers();
	}


	@Test
	public void testAccountExpired() throws Exception {
		doTest(ValueAccountStatus.ACCOUNT_EXPIRED, 401001);
	}

	@Test
	public void testAccountLocked() throws Exception {
		doTest(ValueAccountStatus.ACCOUNT_LOCKED, 401002);
	}

	@Test
	public void testCredentialsExpired() throws Exception {
		doTest(ValueAccountStatus.CREDENTIALS_EXPIRED, 401003);
	}

	@Test
	public void testAccountDisabled() throws Exception {
		doTest(ValueAccountStatus.ACCOUNT_DISABLED, 401004);
	}

	@Test
	public void testEmailNotVerified() throws Exception {
		doTest(ValueAccountStatus.EMAIL_NOT_VERIFIED, 401005);
	}


	private void doTest(int accountStatus, int errorCode) throws Exception {
		String username = "user01";

		signUpUser(username, accountStatus);

		LoginRequestDto dto = new LoginRequestDto(username, username + "@Yuhubs");
		JsonContent<LoginRequestDto> json = loginJson.write(dto);

		MvcResult result = this.mockMvc.perform(
				post(SIGNIN_ENDPOINT)
						.contentType(APPLICATION_JSON)
						.content(json.getJson()))
				//.andDo(print())
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("error.statusCode", Matchers.is(401)))
				.andExpect(jsonPath("error.code", Matchers.is(errorCode)))
				.andReturn();
	}

	private void signUpUser(String username, int status) {
		MockAuthUserService authUserService = authUserService();

		String passwordHash = this.securityContext.passwordEncoder().encode(username + "@Yuhubs");
		String email = username + "@yuhubs.com";

		SignUpRequest request = new SignUpRequest(email, username, passwordHash);
		request.setStatus(status);

		authUserService.mockUserManager().signUpUser(request);
	}


	private void resetUsers() {
		authUserService().mockUserManager().clearUsers();
	}

}
