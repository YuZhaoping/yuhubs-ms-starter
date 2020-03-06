package com.yuhubs.ms.security.jwt;

import com.yuhubs.ms.security.web.SecurityConfigurationSupport;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@Configuration
public class SecurityTestConfig extends SecurityConfigurationSupport {
}
