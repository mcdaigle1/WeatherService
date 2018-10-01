package com.weatherservice.controllers;

import com.weatherservice.services.LogService;
import com.weatherservice.services.ValidationService;

/**
 * Base controller class.  This provides some core utility functions to
 * controllers, like logging and validation services
 */
public class BaseController  {

  // MCD TODO - implement log service
  protected LogService l = null;
  protected ValidationService valid = ValidationService.instance();

  /**
   * Create a LogService singleton object which is available to every subclass
   */
  public BaseController() {
    l = LogService.instance();
  }
}
