package com.yuhubs.ms.security.jwt;

import com.yuhubs.ms.security.RolePermissionsAuthentication;
import com.yuhubs.ms.security.web.SecurityHandlerSupplier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class TestController {

	private final SecurityHandlerSupplier securityHandlerSupplier;


	TestController(SecurityHandlerSupplier supplier) {
		this.securityHandlerSupplier = supplier;
	}


	@GetMapping("/")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Mono<Void> handleRoot(ServerWebExchange exchange,
								 @RequestParam(value = "action", required = false) String action) {
		if (action != null) {
			if ("signup".equals(action)) {
				RolePermissionsAuthentication authentication =
						RolePermissionsAuthentication.of(Long.valueOf(0L), "USE-DATA");
				this.securityHandlerSupplier.onAuthenticationSuccess(exchange, authentication);
			}
		}
		return Mono.empty();
	}

	@GetMapping("/test")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Mono<Void> handleTest() {
		return Mono.empty();
	}

}
