package com.yuhubs.ms.redis;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

/**
 * NOTE: All properties' key MUST remove the prefix "spring.redis."
 */
public class RedisConfigProperties {

	protected final Properties props;


	public RedisConfigProperties(Properties props) {
		this.props = props;
	}

	public RedisConfigProperties() {
		this.props = new Properties();
	}


	public void load(ResourceLoader loader, String resourcePath) throws IOException {
		Resource resource = loader.getResource(resourcePath);

		final Properties temp = new Properties();

		PropertiesLoaderUtils.fillProperties(temp, new EncodedResource(resource, "UTF-8"));

		final int beginIndex = "spring.redis.".length();

		temp.keySet().stream().map(key -> key.toString())
				.filter(key -> key.startsWith("spring.redis."))
				.forEach(key -> {
					this.props.setProperty(key.substring(beginIndex), temp.getProperty(key));
				});
	}


	public final Properties getProperties() {
		return props;
	}


	public static Duration parseDuration(String str) {
		int index = 0;
		if (str.charAt(0) == '-') {
			++index;
		}

		if (str.charAt(index) == 'P') {
			return Duration.parse(str);
		}

		final int length = str.length();
		for (; index < length; ++index) {
			if (!Character.isDigit(str.charAt(index))) break;
		}

		long value = Long.parseLong(str.substring(0, index));

		if (index >= length) {
			return Duration.ofMillis(value);
		}

		switch (str.substring(index)) {
			case "ns": return Duration.ofNanos(value);
			case "us": return Duration.ofNanos(value * 1000);
			case "ms": return Duration.ofMillis(value);
			case "s":  return Duration.ofSeconds(value);
			case "m":  return Duration.ofMinutes(value);
			case "h":  return Duration.ofHours(value);
			case "d":  return Duration.ofDays(value);
		}

		return Duration.ofMillis(value);
	}

}
