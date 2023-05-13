package com.devsuperior.movieflix.resources.exception;

import java.time.Instant;


import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.movieflix.exceptions.DatabaseException;
import com.devsuperior.movieflix.exceptions.ForbiddenException;
import com.devsuperior.movieflix.exceptions.ResourceNotFoundException;
import com.devsuperior.movieflix.exceptions.UnauthorizedException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
		StandardError error = new StandardError();
		HttpStatus status = HttpStatus.NOT_FOUND;
		error.setTimestamp(Instant.now());
		error.setError("Resource Not Found!");
		error.setMessage(e.getMessage());
		error.setStatus(status.value());
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> baseException(DatabaseException e, HttpServletRequest request){
		StandardError error = new StandardError();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		error.setTimestamp(Instant.now());
		error.setError("Data Base!");
		error.setMessage(e.getMessage());
		error.setStatus(status.value());
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<OAuthError> errorAuth(UnauthorizedException e, HttpServletRequest request){
		OAuthError error = new OAuthError();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		error.setError("Unauthorized!");
		error.setErrorDescription(e.getLocalizedMessage());
		return ResponseEntity.status(status.value()).body(error);
	}
	
	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<OAuthError> forbidden(ForbiddenException e, HttpServletRequest request){
		OAuthError error = new OAuthError();
		HttpStatus status = HttpStatus.FORBIDDEN;
		error.setError("Forbidden!");
		error.setErrorDescription(e.getLocalizedMessage());
		return ResponseEntity.status(status.value()).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validError(MethodArgumentNotValidException e, HttpServletRequest request){
		ValidationError validError = new ValidationError();
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		validError.setTimestamp(Instant.now());
		validError.setStatus(status.value());
		validError.setError("Validation Error!");
		validError.setMessage(e.getMessage());
		
		for(FieldError f: e.getBindingResult().getFieldErrors()) {
			validError.addErros(f.getField(),f.getDefaultMessage());
		}
		validError.setPath(request.getRequestURI());
		return ResponseEntity.status(status.value()).body(validError);
		
	}
	
}
