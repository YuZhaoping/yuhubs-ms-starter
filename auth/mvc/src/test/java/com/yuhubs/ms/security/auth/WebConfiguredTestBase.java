package com.yuhubs.ms.security.auth;

import com.yuhubs.ms.auth.mock.MockAuthUserService;
import com.yuhubs.ms.security.auth.web.AuthWebSecurityContext;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ContextHierarchy({
		@ContextConfiguration(classes = ApplicationConfig.class),
		@ContextConfiguration(classes = WebTestConfig.class)
})
@WebAppConfiguration
public class WebConfiguredTestBase {

	@Autowired
	protected WebApplicationContext wac;

	protected MockMvc mockMvc;


	@Autowired
	protected AuthWebSecurityContext securityContext;

	protected MockAuthUserService authUserService() {
		return (MockAuthUserService)this.securityContext.authUserService();
	}


	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(this.wac)
				.apply(springSecurity())
				.build();
	}

}
