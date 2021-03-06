package com.yuhubs.ms.auth;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ContextHierarchy({
		@ContextConfiguration(classes = ApplicationTestConfig.class),
		@ContextConfiguration(classes = WebTestConfig.class)
})
@WebAppConfiguration
public class WebConfiguredTestBase {

	@Autowired
	protected ApplicationContext context;

	protected WebTestClient client;


	@Before
	public void setup() {
		this.client = WebTestClient
				.bindToApplicationContext(this.context)
				.configureClient()
				.build();
	}

}
