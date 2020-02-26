package com.yuhubs.ms.exceptions.api;

public final class ThrowsApiError extends ApiError {

	private final String message;

	private final String reason;

	private final ThrowsApiError cause;


	public ThrowsApiError(Throwable ex) {
		this.message = ex.getMessage();
		this.reason = ex.getClass().getName();

		if (ex.getCause() != null) {
			this.cause = new ThrowsApiError(ex.getCause());
		} else {
			this.cause = null;
		}
	}


	public String getMessage() {
		return message;
	}

	public String getReason() {
		return reason;
	}

	public ThrowsApiError getCause() {
		return cause;
	}

}
