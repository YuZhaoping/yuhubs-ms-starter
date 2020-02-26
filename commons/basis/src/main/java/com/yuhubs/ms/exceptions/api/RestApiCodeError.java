package com.yuhubs.ms.exceptions.api;

import java.util.List;

public interface RestApiCodeError {

	int getCode();

	List<ApiError> getErrors();

}
