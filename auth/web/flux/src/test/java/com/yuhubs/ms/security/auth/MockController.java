package com.yuhubs.ms.security.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class MockController {

	@GetMapping("/")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Mono<Void> handleRoot() {
		return Mono.empty();
	}

	@GetMapping("/test")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Mono<Void> handleTest() {
		return Mono.empty();
	}

}
