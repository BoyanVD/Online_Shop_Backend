package com.shop.demo.exception;

public class BankAccountException extends Exception {

	private static final long serialVersionUID = 1L;

	public BankAccountException() {
		super();
	}

	public BankAccountException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BankAccountException(String message, Throwable cause) {
		super(message, cause);
	}

	public BankAccountException(String message) {
		super(message);
	}

	public BankAccountException(Throwable cause) {
		super(cause);
	}
	
	

}
