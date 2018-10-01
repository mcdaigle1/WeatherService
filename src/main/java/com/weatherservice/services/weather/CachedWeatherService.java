package com.weatherservice.services.weather;

import com.google.common.annotations.VisibleForTesting;
import com.weatherservice.exceptions.BaseException;
import com.weatherservice.services.Service;
import com.weatherservice.services.caching.CacheService;
import com.weatherservice.services.caching.CacheServiceFactory;

public class CachedWeatherService extends WeatherServiceDecorator implements Service {
  private CacheService windCache;

  public CachedWeatherService(WeatherService weatherService) {
    super(weatherService);
    windCache = CacheServiceFactory.getService("windCache");
  }

  /**
   * Gets the CacheService instance for testing purposes
   * @return CacheService
   */
  @VisibleForTesting
  public CacheService getWindCache() {
    return windCache;
  }

  /**
   * Get the wind information from cache if possible, otherwise forward to parent
   * @param zipcode - String zipcode for which we'd like to find the wind data
   * @return - String JSON holding wind data
   * @throws BaseException
   */
  @Override
  public String getWindInfoForZip(String zipcode) throws BaseException {
    String responseValue = getWindCache().get(zipcode);

    if (responseValue == null) {
      l.info("++++++++++++ CACHE MISS");
      return super.getWindInfoForZip(zipcode);
    } else {
      l.info("++++++++++++ CACHE HIT");
      return responseValue;
    }
  }

  /**
   * Remove all data from the cache
   */
  public void bustCache() {
    windCache.bust();
  }
}
