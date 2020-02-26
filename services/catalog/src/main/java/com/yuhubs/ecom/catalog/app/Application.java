package com.yuhubs.ecom.catalog.app;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@SpringBootApplication
public class Application extends SpringBootServletInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return configureApplication(builder);
	}


	public static void main(String[] args) {
		configureApplication(new SpringApplicationBuilder()).run(args);
	}


	private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
		return builder.sources(Application.class)
				.bannerMode(Banner.Mode.OFF);
	}

}
