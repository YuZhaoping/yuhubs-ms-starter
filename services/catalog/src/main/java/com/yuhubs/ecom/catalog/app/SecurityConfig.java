package com.yuhubs.ecom.catalog.app;

import com.yuhubs.ms.auth.mock.MockAuthUserService;
import com.yuhubs.ms.security.auth.AuthUserService;
import com.yuhubs.ms.security.auth.web.AuthConfigurationSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import static org.springframework.http.HttpMethod.GET;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends AuthConfigurationSupport {

	@Value("${yuhubs.ms.static-resources.enabled:false}")
	private boolean staticResourcesEnabled;

	@Autowired
	private MockAuthUserService authUserService;


	@Override
	protected void configureRequestAuthorization(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/sockjs-node/**").permitAll();

		if (staticResourcesEnabled) {
			http.authorizeRequests()
					.antMatchers(GET, "/public/**").permitAll();
		}

		super.configureRequestAuthorization(http);
	}


	@Override
	protected AuthUserService getAuthUserService() {
		return this.authUserService;
	}

}
