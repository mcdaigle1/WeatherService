package com.weatherservice.services;

/*
 * Base class for services
 */
public class BaseService {
  protected LogService l = null;

  public BaseService() {
    l = LogService.instance();
  }
}