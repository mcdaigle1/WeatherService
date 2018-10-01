package com.weatherservice.exceptions;

public class PropertyNotFoundException extends ParamException {

  public PropertyNotFoundException(String message) {
    super(message, message);
  }

  public PropertyNotFoundException(String message, String userFriendlyMessage) {
    super(message, userFriendlyMessage);
  }

  public PropertyNotFoundException(BaseException be) {
    super(be);
  }

  public PropertyNotFoundException(Throwable t, String userFriendlyMessage) {
    super(t, userFriendlyMessage);
  }
}
