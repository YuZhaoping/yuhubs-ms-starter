package com.yuhubs.ms.web.tests;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockErrorController {

	@RequestMapping("/throwex")
	public ResponseEntity<String> mockThrowException() throws Exception {
		throw new Exception("Internal Server Error");
	}

	@RequestMapping("/unsupported")
	public ResponseEntity<String> mockThrowUnsupportedOperation() {
		throw new UnsupportedOperationException("Not Implemented");
	}

}
