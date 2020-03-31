package com.yuhubs.ms.security.jwt;

import org.junit.Test;

import java.util.function.Consumer;

import static org.junit.Assert.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public class SecuritySupportTest extends WebConfiguredTestBase {

	private static final String X_SET_AUTHORIZATION_BEARER_HEADER = "X-Set-Authorization-Bearer";

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String TOKEN_PREFIX = "Bearer ";


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

	@Test
	public void testAccessPermitted() {
		JwtTokenValue jwtToken = new JwtTokenValue();

		this.client.get().uri("/?action=signup").accept(APPLICATION_JSON)
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

	private static String createBearerToken(String token) {
		StringBuilder buf = new StringBuilder(token.length() + TOKEN_PREFIX.length());
		buf.append(TOKEN_PREFIX).append(token);
		return buf.toString();
	}

}
