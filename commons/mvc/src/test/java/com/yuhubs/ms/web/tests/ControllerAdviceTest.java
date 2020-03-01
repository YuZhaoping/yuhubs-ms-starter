package com.yuhubs.ms.web.tests;

import com.yuhubs.ms.web.WebConfiguredTestBase;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ControllerAdviceTest extends WebConfiguredTestBase {

	@Test
	public void testBaseException() throws Exception {
		this.mockMvc.perform(get("/throwex").contentType(APPLICATION_JSON))
				//				.andDo(print())
				.andExpect(status().is5xxServerError())
				.andExpect(jsonPath("error.code", Matchers.is(500)))
				.andExpect(jsonPath("error.message", Matchers.is("Internal Server Error")));
	}

	@Test
	public void testUnsupportedOperationException() throws Exception {
		this.mockMvc.perform(get("/unsupported").contentType(APPLICATION_JSON))
//				.andDo(print())
				.andExpect(status().is5xxServerError())
				.andExpect(jsonPath("error.code", Matchers.is(501)))
				.andExpect(jsonPath("error.message", Matchers.is("Not Implemented")));
	}

}
