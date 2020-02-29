package com.yuhubs.ms.web.tests;

import com.yuhubs.ms.web.RestErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
public class DefaultController {

	@RequestMapping
	@ResponseBody
	public ResponseEntity<Object> handleUnmappedRequest(final HttpServletRequest request) {
		return ResponseEntity.status(NOT_FOUND).body(RestErrorResponse.of(NOT_FOUND).toRestApiError());
	}

}
