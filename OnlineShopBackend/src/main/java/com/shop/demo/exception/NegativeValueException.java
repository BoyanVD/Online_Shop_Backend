package com.shop.demo.exception;

public class NegativeValueException extends Exception {

	private static final long serialVersionUID = 1L;

	public NegativeValueException() {
		super();
	}

	public NegativeValueException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public NegativeValueException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NegativeValueException(String arg0) {
		super(arg0);
	}

	public NegativeValueException(Throwable arg0) {
		super(arg0);
	}

	
}
