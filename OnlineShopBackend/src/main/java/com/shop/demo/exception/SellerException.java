package com.shop.demo.exception;

public class SellerException extends Exception {

	private static final long serialVersionUID = 1L;

	public SellerException() {
		super();
	}

	public SellerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SellerException(String message, Throwable cause) {
		super(message, cause);
	}

	public SellerException(String message) {
		super(message);
	}

	public SellerException(Throwable cause) {
		super(cause);
	}
	
	

}
