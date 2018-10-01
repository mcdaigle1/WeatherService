package com.weatherservice.utilities;

import com.weatherservice.services.caching.CacheService;
import com.weatherservice.services.caching.CacheServiceFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CacheUtilTest {
  CacheService windCache;

  @Before
  public void initialize() {
    windCache = CacheServiceFactory.getService("windCache");
  }

  @Test
  public void GetCachedValue() {
    String key = "key1";
    String value = "value1";

    windCache.add(key, value);
    String newValue = windCache.get(key);
    assertEquals(value, newValue);
  }

  @Test
  public void GetUnCachedValue() {
    String key = "key1";
    String value = "value1";

    windCache.add(key, value);
    windCache.bust();
    String newValue = windCache.get(key);
    assertNull(newValue);
  }
}
