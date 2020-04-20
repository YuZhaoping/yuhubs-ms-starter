package com.yuhubs.ms.security.auth;

import com.yuhubs.ms.auth.mock.reactive.MockAuthUserService;
import com.yuhubs.ms.security.auth.web.AuthWebSecurityContext;
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


	@Autowired
	protected AuthWebSecurityContext securityContext;

	protected MockAuthUserService authUserService() {
		return (MockAuthUserService)this.securityContext.authUserService();
	}


	@Before
	public void setUp() {
		this.client = WebTestClient
				.bindToApplicationContext(this.context)
				.configureClient()
				.build();
	}

}
