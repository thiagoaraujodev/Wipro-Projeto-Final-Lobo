package com.squadlobo.api.controller.exceptions;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.squadlobo.api.service.exceptions.ObjetoNaoEncontradoException;

@ControllerAdvice
public class ControllerException {

	@ExceptionHandler(ObjetoNaoEncontradoException.class)
	public ResponseEntity<StandardError> objetoNaoEncontradoException(ObjetoNaoEncontradoException obj,
			HttpServletRequest request) {

		StandardError error = new StandardError();
		error.setTimestamp((LocalDateTime.now()));
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setError("Not found");
		error.setMessage(obj.getMessage());
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}	
}
