package com.yuhubs.ms.security.auth;

import com.yuhubs.ms.security.jwt.JwtTokenService;
import com.yuhubs.ms.security.jwt.JwtTokenServiceContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthSecurityContext implements ApplicationEventPublisherAware {

	protected ApplicationEventPublisher publisher;

	protected final JwtTokenServiceContext jwtContext;

	protected final AuthProperties authProperties;

	protected final PasswordEncoder passwordEncoder;


	public AuthSecurityContext(JwtTokenServiceContext context) {
		this.jwtContext = context;
		this.authProperties = new AuthProperties(context.getSecurityProperties());
		this.passwordEncoder = createPasswordEncoder();
	}


	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	public void publishEvent(ApplicationEvent event) {
		if (this.publisher != null) {
			this.publisher.publishEvent(event);
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
