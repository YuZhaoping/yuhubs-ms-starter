package com.yuhubs.ms.config;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;

public class YamlPropertyLoaderFactory extends DefaultPropertySourceFactory {

	@Override
	public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {

		if (resource == null) {
			return super.createPropertySource(name, resource);
		}

		final Resource res = resource.getResource();

		return new YamlPropertySourceLoader().load(res.getFilename(), res).get(0);
	}

}
