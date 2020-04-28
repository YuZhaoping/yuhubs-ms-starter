package com.yuhubs.ms.auth.redis;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
		AuthRedisConfig.class
})
public class ApplicationTestConfig {
}
