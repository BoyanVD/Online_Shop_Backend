package com.shop.demo.exception;

public class CreditCardException extends Exception {

	private static final long serialVersionUID = 1L;

	public CreditCardException() {
		super();
	}

	public CreditCardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CreditCardException(String message, Throwable cause) {
		super(message, cause);
	}

	public CreditCardException(String message) {
		super(message);
	}

	public CreditCardException(Throwable cause) {
		super(cause);
	}
	
	

}
