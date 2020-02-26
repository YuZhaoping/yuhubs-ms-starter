package com.yuhubs.ms.auth;

import com.yuhubs.ms.auth.web.WebConfigSupport;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
		WebConfigSupport.class
})
public class WebTestConfig {
}
