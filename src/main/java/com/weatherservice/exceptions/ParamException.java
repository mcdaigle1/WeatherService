package com.weatherservice.exceptions;

public class ParamException extends BaseException {
  public ParamException(String message) {
    super(message, message);
  }

  public ParamException(String message, String userFriendlyMessage) {
    super(message, userFriendlyMessage);
  }

  public ParamException(BaseException be) {
    super(be);
  }

  public ParamException(Throwable t, String userFriendlyMessage) {
    super(t, userFriendlyMessage);
  }
}
