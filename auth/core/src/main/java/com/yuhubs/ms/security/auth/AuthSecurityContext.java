package com.yuhubs.ms.security.auth;

import com.yuhubs.ms.security.jwt.JwtTokenService;
import com.yuhubs.ms.security.jwt.JwtTokenServiceContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthSecurityContext implements ApplicationContextAware {

	protected ApplicationContext applicationContext;

	protected final JwtTokenServiceContext jwtContext;

	protected final AuthProperties authProperties;

	protected final PasswordEncoder passwordEncoder;


	public AuthSecurityContext(JwtTokenServiceContext context) {
		this.jwtContext = context;
		this.authProperties = new AuthProperties(context.getSecurityProperties());
		this.passwordEncoder = createPasswordEncoder();
	}


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public void publishEvent(ApplicationEvent event) {
		if (this.applicationContext != null) {
			this.applicationContext.publishEvent(event);
		}
	}


	protected PasswordEncoder createPasswordEncoder() {
		return new BCryptPasswordEncoder(10);
	}


	public final AuthProperties authProperties() {
		return this.authProperties;
	}

	public final JwtTokenService jwtTokenService() {
		return this.jwtContext.getJwtTokenService();
	}

	public final PasswordEncoder passwordEncoder() {
		return this.passwordEncoder;
	}

}
