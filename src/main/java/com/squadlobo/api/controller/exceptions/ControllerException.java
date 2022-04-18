package com.squadlobo.api.controller.exceptions;

import java.time.ZonedDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.squadlobo.api.service.exceptions.NotFoundException;


public class ControllerException {
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(NotFoundException e, HttpServletRequest request){
		
		StandardError err = new StandardError();
		err.setTimestamp(ZonedDateTime.now());
		err.setStatus(HttpStatus.NOT_FOUND.value());
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);		
	}
}
