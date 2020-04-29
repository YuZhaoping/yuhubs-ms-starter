package com.yuhubs.ms.security.jwt;

import com.yuhubs.ms.security.web.SecurityConfigurationSupport;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig extends SecurityConfigurationSupport {
}
