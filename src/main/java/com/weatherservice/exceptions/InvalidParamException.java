package com.weatherservice.exceptions;

public class InvalidParamException extends ParamException {

  public InvalidParamException(String message) {
    super(message, message);
  }

  public InvalidParamException(String message, String userFriendlyMessage) {
    super(message, userFriendlyMessage);
  }

  public InvalidParamException(BaseException be) {
    super(be);
  }

  public InvalidParamException(Throwable t, String userFriendlyMessage) {
    super(t, userFriendlyMessage);
  }
}
