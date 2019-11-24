package com.shop.demo.exception;

public class EmptyBasketException extends Exception {

	private static final long serialVersionUID = 1L;

	public EmptyBasketException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmptyBasketException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public EmptyBasketException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public EmptyBasketException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public EmptyBasketException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
}
