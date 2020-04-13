package com.yuhubs.ms.auth.app;

import com.yuhubs.ms.auth.mock.MockAuthUserService;
import com.yuhubs.ms.auth.mock.MockUserManager;
import com.yuhubs.ms.security.auth.AuthUserService;
import com.yuhubs.ms.security.auth.web.AuthConfigurationSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.stereotype.Component;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig extends AuthConfigurationSupport {

	@Component
	public static class InitMockUsersTask implements CommandLineRunner {

		@Autowired
		private MockAuthUserService authUserService;

		@Autowired
		private SecurityConfig securityConfig;


		@Override
		public void run(String... args) throws Exception {
			MockUserManager mockUserManager = authUserService.mockUserManager();
			mockUserManager.initUsers(securityConfig.authSecurityContext());
		}

	}


	@Value("${yuhubs.ms.static-resources.enabled:false}")
	private boolean staticResourcesEnabled;

	@Autowired
	private MockAuthUserService authUserService;


	@Override
	protected void configureRequestAuthorization(ServerHttpSecurity http) {
		http.authorizeExchange()
				.pathMatchers("/sockjs-node/**").permitAll();

		if (staticResourcesEnabled) {
			http.authorizeExchange()
					.pathMatchers(HttpMethod.GET, "/public/**").permitAll();
		}

		super.configureRequestAuthorization(http);
	}

	@Override
	protected AuthUserService getAuthUserService() {
		return this.authUserService;
	}

}
