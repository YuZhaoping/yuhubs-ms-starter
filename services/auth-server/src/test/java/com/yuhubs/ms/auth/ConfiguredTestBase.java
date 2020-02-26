package com.yuhubs.ms.auth;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.main.web-application-type=reactive")
@ContextConfiguration(classes = ApplicationTestConfig.class)
public class ConfiguredTestBase {
}
