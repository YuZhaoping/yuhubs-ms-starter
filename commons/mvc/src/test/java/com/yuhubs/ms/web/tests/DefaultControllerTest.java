package com.yuhubs.ms.web.tests;

import com.yuhubs.ms.web.WebConfiguredTestBase;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DefaultControllerTest extends WebConfiguredTestBase {

	@Test
	public void testNotFound() throws Exception {
		this.mockMvc.perform(get("/").contentType(APPLICATION_JSON))
//				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("error.code", Matchers.is(404)))
				.andExpect(jsonPath("error.message", Matchers.is("Not Found")));
	}

}
