package com.yuhubs.ms.security.auth;

import com.yuhubs.ms.auth.mock.MockAuthUserService;
import com.yuhubs.ms.auth.mock.MockUserManager;
import com.yuhubs.ms.auth.mock.config.MockUserConfigSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MockUserConfig extends MockUserConfigSupport {

	@Bean
	public MockAuthUserService authUserService() {
		return new MockAuthUserService(new MockUserManager(this));
	}

}
