package com.kimscooperation.kimsboard.advice.exception;

public class CNotOwnerException extends RuntimeException {
	
	public CNotOwnerException(String msg, Throwable t) {
		super(msg, t);
	}

	public CNotOwnerException(String msg) {
		super(msg);
	}

	public CNotOwnerException() {
		super();	
	}
}