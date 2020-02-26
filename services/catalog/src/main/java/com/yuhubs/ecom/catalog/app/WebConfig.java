package com.yuhubs.ecom.catalog.app;

import com.yuhubs.ecom.catalog.web.WebConfigSupport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

// As: servlet-context.xml
@Configuration
public class WebConfig extends WebConfigSupport {

	@Value("${yuhubs.ms.static-resources.enabled:false}")
	private boolean staticResourcesEnabled;

	@Value("${spring.resources.static-locations}")
	private String resourceLocations;

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		if (staticResourcesEnabled) {
			registry.addResourceHandler("/public/**")
					.addResourceLocations(resourceLocations);
		}
	}

}
