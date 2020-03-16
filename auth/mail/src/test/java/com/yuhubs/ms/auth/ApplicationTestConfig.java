package com.yuhubs.ms.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
		AuthTemplates.class,
		MailConfig.class,
		AsyncConfig.class,
		AuthEventHandler.class
})
public class ApplicationTestConfig {
}
