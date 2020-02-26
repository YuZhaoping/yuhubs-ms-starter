package com.yuhubs.ms.auth.app;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		configureApplication(new SpringApplicationBuilder()).run(args);
	}


	private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
		return builder.sources(Application.class)
				.bannerMode(Banner.Mode.OFF);
	}

}
