package com.project.SupportDesk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handle(UserNotFoundException unf){
		return new ResponseEntity<>(unf.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(TicketNotFoundException.class)
	public ResponseEntity<String> handle(TicketNotFoundException tnf){
		return new ResponseEntity<>(tnf.getMessage(), HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(CommentNotFoundException.class)
	public ResponseEntity<String> handle(CommentNotFoundException cnf){
		return new ResponseEntity<>(cnf.getMessage(), HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(InvalidStatusTransitionException.class)
	public ResponseEntity<String> handle(InvalidStatusTransitionException ist){
		return new ResponseEntity<>(ist.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
