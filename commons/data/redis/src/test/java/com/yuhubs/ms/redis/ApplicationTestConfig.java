package com.yuhubs.ms.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Configuration
@Import({
		RedisStandaloneConfig.class,
		RedisClusterConfig.class
})
public class ApplicationTestConfig {


	@Component
	public static class EmbededRedis {

		@Value("${spring.redis.port}")
		private int redisPort;

		private RedisServer redisServer;


		@PostConstruct
		public void startRedis() throws IOException {
			redisServer = new RedisServer(redisPort);
			redisServer.start();
		}

		@PreDestroy
		public void stopRedis() {
			redisServer.stop();
		}

	}

}
