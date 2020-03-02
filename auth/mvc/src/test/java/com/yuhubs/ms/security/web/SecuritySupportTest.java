package com.yuhubs.ms.security.web;

import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SecuritySupportTest extends WebConfiguredTestBase {

	private static final String X_SET_AUTHORIZATION_BEARER_HEADER = "X-Set-Authorization-Bearer";

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String TOKEN_PREFIX = "Bearer ";


	@Test
	public void testAccessRoot() throws Exception {
		this.mockMvc.perform(get("/").contentType(APPLICATION_JSON))
				//.andDo(print())
				.andExpect(status().isNoContent());
	}

	@Test
	public void testAccessNotPermitted() throws Exception {
		this.mockMvc.perform(get("/test").contentType(APPLICATION_JSON))
				//.andDo(print())
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void testAccessPermitted() throws Exception {
		MvcResult result =
				this.mockMvc.perform(get("/?action=signup").contentType(APPLICATION_JSON))
						//.andDo(print())
						.andExpect(status().isNoContent())
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


	private static String createBearerToken(String token) {
		StringBuilder buf = new StringBuilder(token.length() + TOKEN_PREFIX.length());
		buf.append(TOKEN_PREFIX).append(token);
		return buf.toString();
	}

}
