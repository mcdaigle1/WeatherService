package com.weatherservice.services.caching;

import com.weatherservice.services.Service;

public interface CacheService extends Service {

  public void add(String key, String value);

  public String get(String key);

  public void remove(String key);

  public void bust();
}
