package com.yuhubs.ecom.catalog;

import com.yuhubs.ecom.catalog.web.WebConfigSupport;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
		WebConfigSupport.class
})
public class WebTestConfig {
}
