package com.yuhubs.ms.security.auth.tests;

import com.yuhubs.ms.security.auth.WebConfiguredTestBase;
import org.junit.Test;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public class AuthSupportTest extends WebConfiguredTestBase {

	@Test
	public void testAccessRoot() {
		this.client.get().uri("/").accept(APPLICATION_JSON)
				.exchange()
				.expectStatus().isNoContent();
	}

	@Test
	public void testAccessNotPermitted() {
		this.client.get().uri("/test").accept(APPLICATION_JSON)
				.exchange()
				.expectStatus().isUnauthorized();
	}

}
