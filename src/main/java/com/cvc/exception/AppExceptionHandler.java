package com.cvc.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cvc.util.Constants;

import cvc.com.error.ErrorCustom;
import cvc.com.error.ErrorDefault;
import cvc.com.error.ErrorDefaultParameter;

@RestControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<Object> handleAnyException(Exception e){
		
		ErrorDefault defaultError = new ErrorDefault(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
				LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.DD_MM_YYYY_HH_MM)), "Error during request processing", null);
		return new ResponseEntity<>(defaultError, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = {NotFoundException.class})
	public ResponseEntity<ErrorCustom> handleNotFoundException(Exception e){
		
		ErrorCustom error = new ErrorCustom(HttpStatus.NOT_FOUND.value(), e.getMessage());
		return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
	        HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<ErrorDefaultParameter> listErrorParameter = e.getBindingResult().getFieldErrors().stream()
	         .map(defaultErrorParameter -> new ErrorDefaultParameter(defaultErrorParameter.getField(),
												        		 		defaultErrorParameter.getRejectedValue()))
	         .collect(Collectors.toList());
		 
		ErrorDefault defaultError = new ErrorDefault(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
				LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.DD_MM_YYYY_HH_MM)), "Parameters entered not valid", listErrorParameter);
		
		return new ResponseEntity<>(defaultError, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
