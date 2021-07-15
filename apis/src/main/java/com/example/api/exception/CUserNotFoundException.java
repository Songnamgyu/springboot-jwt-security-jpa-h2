package com.example.api.exception;

public class CUserNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2301286174257811685L;

	public CUserNotFoundException(String msg, Throwable t) {
		super("회원정보를 찾지 못하였습니다", t);
	}

	public CUserNotFoundException(String msg) {
		super("회원정보를 찾지 못하였습니다");
	}

	public CUserNotFoundException() {
		super();
	}
}
