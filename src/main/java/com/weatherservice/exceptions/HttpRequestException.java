package com.weatherservice.exceptions;

public class HttpRequestException extends BaseException {

  public HttpRequestException(String message) {
    super(message, message);
  }

  public HttpRequestException(String message, String userFriendlyMessage) {
    super(message, userFriendlyMessage);
  }

  public HttpRequestException(com.weatherservice.exceptions.BaseException be) {
    super(be);
  }

  public HttpRequestException(Throwable t, String userFriendlyMessage) {
    super(t, userFriendlyMessage);
  }
}
