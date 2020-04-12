package com.yuhubs.ms.web;

import com.yuhubs.ms.exceptions.BadRequestException;
import com.yuhubs.ms.web.annotation.ExceptionStatusMapper;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.handler.WebFluxResponseStatusExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class RestExceptionHandler {

	private final ResponseStatusExceptionHandler responseStatusExceptionHandler;

	private final Map<Class<? extends Throwable>, HttpStatus> statusMap;


	public RestExceptionHandler() {
		this.responseStatusExceptionHandler = new ResponseStatusExceptionHandler();
		this.statusMap = new HashMap<>();
	}


	protected void init() {
		handleMapperAnnotations();
	}

	protected final void addMapStatus(Class<? extends Throwable> clazz, HttpStatus status) {
		this.statusMap.put(clazz, status);
	}


	@ExceptionStatusMapper(BadRequestException.class)
	public HttpStatus mapBadRequestException() {
		return HttpStatus.BAD_REQUEST;
	}

	@ExceptionStatusMapper(RuntimeException.class)
	public HttpStatus mapRuntimeException() {
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}


	public final HttpStatus determineStatus(Throwable error) {
		HttpStatus status = mapHttpStatus(error, true);
		if (status != null) {
			return status;
		}

		status = this.responseStatusExceptionHandler.determineStatus(error);
		if (status != null) {
			return status;
		}

		status = mapHttpStatus(error, false);
		if (status != null) {
			return status;
		}

		if (error instanceof RuntimeException) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return null;
	}

	private final HttpStatus mapHttpStatus(Throwable ex, boolean first) {
		Class<?> clazz = ex.getClass();
		while (clazz != null && clazz != Object.class) {
			HttpStatus status = this.statusMap.get(clazz);
			if (status != null) {
				return status;
			}

			clazz = clazz.getSuperclass();

			if (first) {
				if (clazz == RuntimeException.class ||
						clazz == Exception.class) {
					break;
				}
			}
		}

		return null;
	}


	public Throwable determineException(Throwable error) {
		if (error instanceof ResponseStatusException) {
			return (error.getCause() != null) ? error.getCause() : error;
		}
		return error;
	}

	public String determineMessage(Throwable error) {
		if (error instanceof WebExchangeBindException) {
			return error.getMessage();
		}
		if (error instanceof ResponseStatusException) {
			return ((ResponseStatusException) error).getReason();
		}
		return error.getMessage();
	}


	protected final void handleMapperAnnotations() {
		Helper helper = new Helper(this, this.getClass());
		helper.handleMapperAnnotation();
	}


	protected String getNoMatchingHandlerExceptionMessage() {
		return "No matching handler";
	}

	protected URI getNotFoundRedirectUri(ServerRequest request,
										 String httpMethod,
										 String requestURL) {
		return null;
	}


	protected void logError(ServerRequest request, HttpStatus status, Throwable throwable) {
		if (throwable != null) {
			Logger logger = getLogger();
			if (logger != null) {
				logger.error("{} for {}\n{}",
						formatHttpStatus(status), formatRequest(request),
						formatThrowable(throwable));
			}
		}
	}

	protected Logger getLogger() {
		return null;
	}


	private final String formatHttpStatus(HttpStatus status) {
		return status.value() + " " + status.getReasonPhrase();
	}

	private final String formatRequest(ServerRequest request) {
		return "HTTP " + request.methodName() + " \"" + request.path() + "\"";
	}

	private final String formatThrowable(Throwable throwable) {
		return throwable.getClass().getName() + ": " + throwable.getMessage();
	}


	private static final class ResponseStatusExceptionHandler extends WebFluxResponseStatusExceptionHandler {

		@Override
		public final HttpStatus determineStatus(Throwable throwable) {
			return super.determineStatus(throwable);
		}

	}


	private static final class Helper {

		private final RestExceptionHandler handler;

		private final Class<?> clazz;


		Helper(RestExceptionHandler handler, Class<?> clazz) {
			this.handler = handler;
			this.clazz = clazz;
		}


		void handleMapperAnnotation() {
			Class<?> superClazz = this.clazz.getSuperclass();
			if (superClazz != null &&
					superClazz != WebFluxResponseStatusExceptionHandler.class) {
				Helper parent = new Helper(this.handler, superClazz);
				parent.handleMapperAnnotation();
			}

			doHandle();
		}

		private void doHandle() {
			Method[] methods = this.clazz.getMethods();

			for (Method method : methods) {
				ExceptionStatusMapper mapper =
						method.getAnnotation(ExceptionStatusMapper.class);
				if (mapper != null) {
					addMapperValues(mapper, method);
				}
			}
		}

		private void addMapperValues(ExceptionStatusMapper mapper, Method method) {
			HttpStatus status;

			try {
				Object result = method.invoke(this.handler);
				if (result == null || !(result instanceof HttpStatus)) {
					return;
				}
				status = (HttpStatus)result;
			} catch (IllegalAccessException e) {
				return;
			} catch (InvocationTargetException e) {
				return;
			}

			Class<? extends Throwable>[] values = mapper.value();

			for (Class<? extends Throwable> value : values) {
				this.handler.addMapStatus(value, status);
			}
		}

	}

}
