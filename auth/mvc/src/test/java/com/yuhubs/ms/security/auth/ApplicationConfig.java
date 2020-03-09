package com.yuhubs.ms.security.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
		MockUserConfig.class,
		SecurityTestConfig.class
})
public class ApplicationConfig {
}
