package com.weatherservice.exceptions;

/**
 * BaseException is an extension of Exception, adding additional messages.  The goal is to expand this functionality to
 * include more data, like system state information, as well as some extended exception chaining functionality.
 * General exceptions should be coalesced into BaseExceptions whenever possible to make use of the eventual extended
 * functionality.
 */

public class BaseException extends Exception {

  private String userFriendlyMessage;

  public BaseException(String message, String userFriendlyMessage) {
    super(message);
    setUserFriendlyMessage(userFriendlyMessage);
  }

  public BaseException(Throwable t, String userFriendlyMessage) {
    super(t);
    this.userFriendlyMessage = userFriendlyMessage;
  }

  public BaseException(BaseException be) {
    super(be);
    this.userFriendlyMessage = be.userFriendlyMessage;
  }

  public String getUserFriendlyMessage() {
    return userFriendlyMessage;
  }

  public void setUserFriendlyMessage(String userFriendlyMessage) {
    this.userFriendlyMessage = userFriendlyMessage;
  }
}

