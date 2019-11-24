package com.shop.demo.exception;

public class NoSuchEmailException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoSuchEmailException() {
		super();
	}

	public NoSuchEmailException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public NoSuchEmailException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NoSuchEmailException(String arg0) {
		super(arg0);
	}

	public NoSuchEmailException(Throwable arg0) {
		super(arg0);
	}
	
	

}
