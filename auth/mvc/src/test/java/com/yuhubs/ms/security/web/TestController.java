package com.yuhubs.ms.security.web;

import com.yuhubs.ms.security.RolePermissionsAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class TestController {

	private final SecurityHandlerSupplier securityHandlerSupplier;


	TestController(SecurityHandlerSupplier supplier) {
		this.securityHandlerSupplier = supplier;
	}


	@GetMapping("/")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void handleRoot(HttpServletRequest request, HttpServletResponse response,
						   @RequestParam(value = "action", required = false) String action)
			throws IOException, ServletException {
		if (action != null) {
			if ("signup".equals(action)) {
				RolePermissionsAuthentication authentication =
						RolePermissionsAuthentication.of(Long.valueOf(0L), "USE-DATA");
				this.securityHandlerSupplier
						.authenticationSuccessHandler()
						.onAuthenticationSuccess(request, response, authentication);
			}
		}
	}

	@GetMapping("/test")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void handleTest() {
	}

}
