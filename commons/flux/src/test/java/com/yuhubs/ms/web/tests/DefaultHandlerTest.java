package com.yuhubs.ms.web.tests;

import com.yuhubs.ms.web.WebConfiguredTestBase;
import org.junit.Test;

public class DefaultHandlerTest extends WebConfiguredTestBase {

	@Test
	public void testNotFound() {
		this.client.get().uri("/")
				.exchange()
				.expectStatus().isNotFound()
				.expectBody()
				.jsonPath("error.code").isEqualTo(404);
//				.jsonPath("error.message").isEqualTo("Not Found");
	}

}
