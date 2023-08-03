package org.in.com.exception;

import org.in.com.controller.io.ResponseIO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler()
	public ResponseEntity<Object> handleCustomException(CustomException ex, WebRequest webRequest){
		ResponseIO<Object> response; 
		response = ResponseIO.failure(ex.getMessage());
		return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.OK, webRequest);
//		return "database_error";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler()
	public ResponseEntity<Object> handleNullPointerException(NullPointerException ex, WebRequest webRequest){
//		return 404 error code
		ex.printStackTrace();
		ResponseIO<Object> response = ResponseIO.failure(ex.getMessage());
		return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.OK, webRequest);
	}
}
