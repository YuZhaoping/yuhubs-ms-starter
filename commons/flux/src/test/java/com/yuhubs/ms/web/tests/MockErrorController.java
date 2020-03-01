package com.yuhubs.ms.web.tests;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class MockErrorController {

	@RequestMapping("/throwex")
	public Mono<String> mockThrowException() throws Exception {
		throw new Exception("Internal Server Error");
	}

	@RequestMapping("/unsupported")
	public Mono<String> mockThrowUnsupportedOperation() {
		throw new UnsupportedOperationException("Not Implemented");
	}

}
