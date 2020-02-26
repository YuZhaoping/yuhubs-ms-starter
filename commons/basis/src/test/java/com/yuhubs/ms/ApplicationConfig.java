package com.yuhubs.ms;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
		AsyncConfig.class,
		EventConfig.class
})
public class ApplicationConfig {
}
