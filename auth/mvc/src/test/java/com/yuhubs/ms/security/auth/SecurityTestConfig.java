package com.yuhubs.ms.security.auth;

import com.yuhubs.ms.security.auth.web.AuthConfigurationSupport;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@Configuration
public class SecurityTestConfig extends AuthConfigurationSupport {
}
