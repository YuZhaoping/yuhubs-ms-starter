package com.yuhubs.ms.auth.redis;

import com.yuhubs.ms.security.auth.SignUpRequest;
import org.junit.runner.RunWith;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = ApplicationTestConfig.class)
@TestPropertySource(locations="classpath:redis.properties")
@EnableConfigurationProperties
public class ConfiguredTestBase {

	protected static SignUpRequest createSignUpRequest(String name) {
		final SignUpRequest request = new SignUpRequest();
		request.setUsername(name);
		request.setEmail(name + "@yuhubs.com");
		request.setPassword(name + "@Yuhubs");
		request.setStatus(0);

		return request;
	}

}
