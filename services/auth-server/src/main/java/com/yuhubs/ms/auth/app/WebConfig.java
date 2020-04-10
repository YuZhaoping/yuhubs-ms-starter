package com.yuhubs.ms.auth.app;

import com.yuhubs.ms.auth.web.WebConfigSupport;
import com.yuhubs.ms.security.auth.web.AuthUserController;
import com.yuhubs.ms.security.auth.web.AuthWebSecurityContext;
import com.yuhubs.ms.web.RestExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.net.URI;

@Configuration
public class WebConfig extends WebConfigSupport {

	@Value("${yuhubs.ms.static-resources.enabled:false}")
	private boolean staticResourcesEnabled;

	@Value("${spring.resources.static-locations}")
	private String resourceLocations;


	@Value("${yuhubs.ms.app.rest-api.location}")
	private String restApiLocation;

	@Value("${yuhubs.ms.app.not-found-redirect.uri:/public/index.html}")
	private String redirectURI;


	@Autowired
	private AuthWebSecurityContext authSecurityContext;

	@Bean
	public AuthUserController authUserController() {
		return this.authSecurityContext.createAuthUserController();
	}


	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		if (staticResourcesEnabled) {
			registry.addResourceHandler("/public/**")
					.addResourceLocations(resourceLocations);
		}
	}


	@Override
	protected RestExceptionHandler createRestExceptionHandler() {
		return new ExceptionHandler(this);
	}


	private static final class ExceptionHandler extends RestExceptionHandler {

		private final WebConfig config;


		private ExceptionHandler(WebConfig config) {
			this.config = config;
		}

		@Override
		protected URI getNotFoundRedirectUri(ServerRequest request,
											 String httpMethod,
											 String requestURL) {
			PathMatcher pathMatcher = new AntPathMatcher();
			if (!pathMatcher.match(config.restApiLocation, requestURL)) {
				return URI.create(config.redirectURI);
			}
			return null;
		}

	}

}
