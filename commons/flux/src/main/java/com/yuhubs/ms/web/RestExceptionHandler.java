package com.yuhubs.ms.web;

import com.yuhubs.ms.exceptions.BadRequestException;
import com.yuhubs.ms.web.annotation.ExceptionStatusMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.handler.WebFluxResponseStatusExceptionHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class RestExceptionHandler extends WebFluxResponseStatusExceptionHandler {

	private final Map<Class<? extends Throwable>, HttpStatus> statusMap;


	public RestExceptionHandler() {
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


	@Override
	public final HttpStatus determineStatus(Throwable ex) {
		HttpStatus status = mapHttpStatus(ex, true);
		if (status != null) {
			return status;
		}

		status = super.determineStatus(ex);
		if (status != null) {
			return status;
		}

		status = mapHttpStatus(ex, false);
		if (status != null) {
			return status;
		}

		if (ex instanceof RuntimeException) {
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
