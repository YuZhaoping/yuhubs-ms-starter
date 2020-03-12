package com.yuhubs.ecom.catalog.app;

import com.yuhubs.ecom.catalog.web.WebConfigSupport;
import com.yuhubs.ms.security.auth.web.AuthUserController;
import com.yuhubs.ms.security.auth.web.AuthWebExceptionHandler;
import com.yuhubs.ms.security.auth.web.AuthWebSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.net.URI;

// As: servlet-context.xml
@Configuration
public class WebConfig extends WebConfigSupport {

	private static Logger LOG = LoggerFactory.getLogger("yuhubs.ecom.catalog");


	@Autowired
	private AuthWebSecurityContext authSecurityContext;

	@Bean
	public AuthUserController authUserController() {
		return this.authSecurityContext.createAuthUserController();
	}


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


	@RestControllerAdvice
	public static class ExceptionHandler extends AuthWebExceptionHandler {

		@Value("${yuhubs.ms.app.rest-api.location}")
		private String restApiLocation;

		@Value("${yuhubs.ms.app.not-found-redirect.uri:/public/index.html}")
		private String redirectURI;

		@Override
		protected URI getNotFoundRedirectUri(WebRequest request,
											 String httpMethod,
											 String requestURL) {
			PathMatcher pathMatcher = new AntPathMatcher();
			if (!pathMatcher.match(restApiLocation, requestURL)) {
				return URI.create(redirectURI);
			}
			return null;
		}

		@Override
		protected Logger getLogger() {
			return LOG;
		}

	}

}
