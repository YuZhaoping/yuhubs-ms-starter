package com.yuhubs.ecom.catalog.app;

import com.yuhubs.ms.auth.mock.MockUserManager;
import com.yuhubs.ms.auth.mock.config.MockUserConfigSupport;
import com.yuhubs.ms.auth.mock.service.MockAuthUserService;
import com.yuhubs.ms.config.YamlPropertyLoaderFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:mock-users.yml"}, factory = YamlPropertyLoaderFactory.class)
@ConfigurationProperties(prefix = "mock")
public class MockUserConfig extends MockUserConfigSupport {

	@Bean
	public MockAuthUserService authUserService() {
		return new MockAuthUserService(new MockUserManager(this));
	}

}
