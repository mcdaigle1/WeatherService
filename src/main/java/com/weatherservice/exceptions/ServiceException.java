package com.weatherservice.exceptions;

public class ServiceException extends BaseException {

  public ServiceException(String message) {
    super(message, message);
  }

  public ServiceException(String message, String userFriendlyMessage) {
    super(message, userFriendlyMessage);
  }

  public ServiceException(BaseException be) {
    super(be);
  }

  public ServiceException(Throwable t, String userFriendlyMessage) {
    super(t, userFriendlyMessage);
  }
}
