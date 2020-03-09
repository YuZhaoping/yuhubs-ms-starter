package com.yuhubs.ms.security.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void handleRoot() {
	}

	@GetMapping("/test")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void handleTest() {
	}

}
