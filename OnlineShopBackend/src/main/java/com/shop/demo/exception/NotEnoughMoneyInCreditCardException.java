package com.shop.demo.exception;

public class NotEnoughMoneyInCreditCardException extends Exception {


	private static final long serialVersionUID = 1L;

	public NotEnoughMoneyInCreditCardException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NotEnoughMoneyInCreditCardException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public NotEnoughMoneyInCreditCardException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NotEnoughMoneyInCreditCardException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NotEnoughMoneyInCreditCardException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
}
