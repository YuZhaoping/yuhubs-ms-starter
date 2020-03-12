package com.yuhubs.ecom.catalog.app;

import com.yuhubs.ms.auth.mock.MockAuthUserService;
import com.yuhubs.ms.auth.mock.MockUserManager;
import com.yuhubs.ms.auth.mock.config.MockUserConfigSupport;
import com.yuhubs.ms.config.YamlPropertyLoaderFactory;
import com.yuhubs.ms.security.auth.web.AuthWebSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@PropertySource(value = {"classpath:mock-users.yml"}, factory = YamlPropertyLoaderFactory.class)
@ConfigurationProperties(prefix = "mock")
public class MockUserConfig extends MockUserConfigSupport {

	@Component
	public static class InitMockUsersTask implements CommandLineRunner {

		@Autowired
		private MockAuthUserService authUserService;

		@Autowired
		private AuthWebSecurityContext authSecurityContext;


		@Override
		public void run(String... args) throws Exception {
			MockUserManager mockUserManager = authUserService.mockUserManager();
			mockUserManager.initUsers(authSecurityContext);
		}

	}

	@Bean
	public MockAuthUserService authUserService() {
		return new MockAuthUserService(new MockUserManager(this));
	}

}
