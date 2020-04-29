package com.yuhubs.ms.security.auth.tests;

import com.yuhubs.ms.security.auth.WebConfiguredTestBase;
import org.junit.Test;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthSupportTest extends WebConfiguredTestBase {

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

}
