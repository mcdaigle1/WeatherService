package com.weatherservice.exceptions;

public class InvalidPropertyException extends ParamException {

  public InvalidPropertyException(String message) {
    super(message, message);
  }

  public InvalidPropertyException(String message, String userFriendlyMessage) {
    super(message, userFriendlyMessage);
  }

  public InvalidPropertyException(BaseException be) {
    super(be);
  }

  public InvalidPropertyException(Throwable t, String userFriendlyMessage) {
    super(t, userFriendlyMessage);
  }
}
