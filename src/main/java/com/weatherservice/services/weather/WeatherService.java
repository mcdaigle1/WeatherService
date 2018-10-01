package com.weatherservice.services.weather;

/*
 * WeatherService - defines the weather service api calls for external weather service implementations.  All
 * weather service data should be retrieved via this interface.
 */

import com.weatherservice.exceptions.BaseException;

public interface WeatherService {

  public String getWindInfoForZip(String zipcode) throws BaseException;

}
