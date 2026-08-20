package com.project.SupportDesk.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class TicketNotFoundException extends RuntimeException{
	public TicketNotFoundException(String msg) {
		super(msg);
	}
}
