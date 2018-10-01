package com.weatherservice.services.weather;

import com.weatherservice.exceptions.BaseException;
import com.weatherservice.services.BaseService;

public class WeatherServiceDecorator extends BaseService implements WeatherService {
  protected WeatherService weatherService;

  public WeatherServiceDecorator(WeatherService weatherService) {
    this.weatherService = weatherService;
  }

  @Override
  public String getWindInfoForZip(String zipcode) throws BaseException {
    return this.weatherService.getWindInfoForZip(zipcode);
  }
}
