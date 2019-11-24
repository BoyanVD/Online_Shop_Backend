package com.shop.demo.exception;

public class NoSuchProductException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoSuchProductException() {
		super();
	}

	public NoSuchProductException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public NoSuchProductException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NoSuchProductException(String arg0) {
		super(arg0);
	}

	public NoSuchProductException(Throwable arg0) {
		super(arg0);
	}
	
	

}
