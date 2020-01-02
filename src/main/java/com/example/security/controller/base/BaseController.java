package com.example.security.controller.base;

import com.example.security.util.Result;
import org.springframework.http.HttpStatus;

public abstract class BaseController {
	
	protected Object success() {
		return Result.success();
	}
	
	protected Object success(Object data) {
		Result result = Result.success();
		result.setData(data);
		return result;
	}
	
	protected Object failure(String message) {
		return Result.failure(message);
	}

	protected Object unauthorized() {
		return new Result(HttpStatus.UNAUTHORIZED.value(), "请前往登录");
	}

}
