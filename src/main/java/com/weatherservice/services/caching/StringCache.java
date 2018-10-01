package com.weatherservice.services.caching;

/**
 * StringCache is a simple caching utility for a string key/value pair.  The value is stored along with its time added
 * in a CacheItem object
 */

import com.weatherservice.exceptions.BaseException;
import com.weatherservice.exceptions.InvalidParamException;
import com.weatherservice.exceptions.PropertyNotFoundException;
import com.weatherservice.services.BaseService;
import com.weatherservice.services.ConfigService;

import java.util.concurrent.ConcurrentHashMap;

public class StringCache extends BaseService implements CacheService {
  private static Long cacheTimeoutMillis = 0L; // 0 cache timeout minutes means there is no timeout
  private static ConcurrentHashMap<String, CacheItem> cache = new ConcurrentHashMap<>(); // Thread safe
  private boolean initialized = false;

//  private static StringCache instance = new StringCache();

  StringCache() {
    super();
    try {
      ConfigService configService = ConfigService.instance();
      Integer cacheTimeoutMinutes = configService.getIntegerProp("service.weather-service.cache-timeout-minutes");
      setTimeoutMinutes(cacheTimeoutMinutes);
    } catch (PropertyNotFoundException pnfe) {
      l.error("Could not find property: service.weather-service.cache-timeout-minutes.  Cache will have no timeout");
      setTimeoutMinutes(0);
    } catch (InvalidParamException ipe) {
      l.error("Invalid value for property: service.weather-service.cache-timeout-minutes.  Cache will have no timeout");
      setTimeoutMinutes(0);
    }
  }

  public boolean isInitialized() {
    return false;
  }

  public void initialize() throws BaseException {

  }

  //  public static StringCache instance() {
//    return instance;
//  }

  /**
   * Add a key/value pair to add to the cache.  The value will be stored in a CacheItem object along with the add time.
   * @param key - String key of the entry we want to add
   * @param value - String vaule of the entry we want to add
   */
  public void add(String key, String value) {
    cache.put(key, new CacheItem(value, System.currentTimeMillis()));
  }

  public String get(String key) {
    CacheItem cacheItem = cache.get(key);
    if (cacheItem != null) {
      if ((cacheItem.getTimeEntered() + cacheTimeoutMillis > System.currentTimeMillis()) || cacheTimeoutMillis == 0L) {
        // cached item exists and is not timed out, so return it.
        return cacheItem.getValue();
      } else {
        // cached item exists but was timed out, so remove it from cache
        remove(key);
      }
    }
    return null;
  }

  /**
   * Remove an individual entry from the cache
   * @param key - String key of the entry we want to remove
   */
  public void remove(String key) {
    cache.remove(key);
  }

  /**
   * Bust the cache, removing all stored values
   */
  public void bust() {
    cache = new ConcurrentHashMap<>();
  }

  /**
   * Save the timeout value as millis
   * @param timeoutMinutes - the time in minutes before any entry in the cache expired
   */
  public static void setTimeoutMinutes(Integer timeoutMinutes ) {
    cacheTimeoutMillis = new Long(timeoutMinutes * 60 * 1000);
  }
}
