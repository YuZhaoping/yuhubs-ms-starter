package com.yuhubs.ecom.catalog;

import com.yuhubs.ecom.catalog.app.ApplicationConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
		ApplicationConfig.class
})
public class ApplicationTestConfig {
}
