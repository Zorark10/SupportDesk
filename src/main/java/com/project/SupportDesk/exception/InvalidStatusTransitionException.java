package com.project.SupportDesk.exception;

public class InvalidStatusTransitionException extends RuntimeException {
	public InvalidStatusTransitionException(String msg) {
		super(msg);
	}
}
