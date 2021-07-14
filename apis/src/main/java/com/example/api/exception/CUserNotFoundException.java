package com.example.api.exception;

public class CUserNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2301286174257811685L;

	public CUserNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}

	public CUserNotFoundException(String msg) {
		super(msg);
	}

	public CUserNotFoundException() {
		super();
	}
}
