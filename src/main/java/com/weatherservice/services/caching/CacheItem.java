package com.weatherservice.services.caching;

/*
 * CacheItem is a simple object that holds the String value to be stored in a cache along with its add time.
 */

public class CacheItem {
  private String value;
  private Long timeEntered;

  /**
   * CacheItem can only be populated at construction time
   * @param value - String value to be stored
   * @param timeEntered - Long holding the time entered in milliseconds
   */
  CacheItem(String value, Long timeEntered) {
    this.value = value;
    this.timeEntered = timeEntered;
  }

  /**
   * Get the cached value
   */
  public String getValue() {
    return value;
  }

  /**
   * Get the cached value's time entered
   */
  public Long getTimeEntered() {
    return timeEntered;
  }
}
