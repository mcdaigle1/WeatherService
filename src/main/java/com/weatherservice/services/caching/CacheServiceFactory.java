package com.weatherservice.services.caching;

import java.util.concurrent.ConcurrentHashMap;

public class CacheServiceFactory {
  // We only want a single cache service with any given name.  Store existing caches here and
  // re-serve
  private static ConcurrentHashMap<String, CacheService> serviceMap = new ConcurrentHashMap<>();

  public static CacheService getService(String serviceName) {
    CacheService cacheService = serviceMap.get(serviceName);
    if (cacheService == null) {
      cacheService = new StringCache();
      serviceMap.put(serviceName, cacheService);
    }
    return cacheService;
  }
}
