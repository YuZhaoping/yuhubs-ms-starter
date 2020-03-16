package com.yuhubs.ms.mail;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
		MailTemplates.class,
		MailConfig.class
})
public class ApplicationTestConfig {
}
