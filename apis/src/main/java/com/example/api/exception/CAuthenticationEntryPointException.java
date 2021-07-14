package com.example.api.exception;

public class CAuthenticationEntryPointException extends RuntimeException {

	private static final long serialVersionUID = -3463547203225121234L;

	public CAuthenticationEntryPointException(String msg, Throwable t) {
        super(msg, t);
    }

    public CAuthenticationEntryPointException(String msg) {
        super(msg);
    }

    public CAuthenticationEntryPointException() {
        super();
    }
}