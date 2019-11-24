package com.shop.demo.exception;

public class AlredySuchUserCreditCardException extends Exception {

  private static final long serialVersionUID = 6594973135244302344L;

  public AlredySuchUserCreditCardException() {
  }

  public AlredySuchUserCreditCardException(String message) {
    super(message);
  }

  public AlredySuchUserCreditCardException(String message, Throwable cause) {
    super(message, cause);
  }

  public AlredySuchUserCreditCardException(Throwable cause) {
    super(cause);
  }

  public AlredySuchUserCreditCardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
