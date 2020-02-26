package com.yuhubs.ms.auth;

import com.yuhubs.ms.auth.app.ApplicationConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
		ApplicationConfig.class
})
public class ApplicationTestConfig {
}
