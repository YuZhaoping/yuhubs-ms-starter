package com.yuhubs.ms.web.tests;

import com.yuhubs.ms.web.WebConfiguredTestBase;
import org.junit.Test;

public class ControllerErrorTest extends WebConfiguredTestBase {

	@Test
	public void testBaseException() {
		this.client.get().uri("/throwex")
				.exchange()
				.expectStatus().is5xxServerError()
				.expectBody()
				.jsonPath("error.code").isEqualTo(500)
				.jsonPath("error.message").isEqualTo("Internal Server Error");
	}

	@Test
	public void testUnsupportedOperationException() {
		this.client.get().uri("/unsupported")
				.exchange()
				.expectStatus().is5xxServerError()
				.expectBody()
				.jsonPath("error.code").isEqualTo(501)
				.jsonPath("error.message").isEqualTo("Not Implemented");
	}

}
