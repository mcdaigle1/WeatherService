package com.weatherservice.services;

/**
 * ConfigService accesses the application.properties file
 * MCD TODO - replace with one of the Spring injection methods
 */

import com.weatherservice.controllers.App;
import com.weatherservice.exceptions.InvalidParamException;
import com.weatherservice.exceptions.PropertyNotFoundException;

import java.io.IOException;
import java.util.Properties;

public class ConfigService {
  private Properties props;

  private static final ConfigService _instance = new ConfigService();

  /**
   * Load the properties file.
   */
  private ConfigService() {
    try {
      props = new Properties();
      props.load(App.class.getClassLoader().getResourceAsStream("application.properties"));
    } catch (IOException ioe) {
      System.out.println(ioe);
    }
  }

  /**
   * Get the ConfigService singleton
   * @return ConfigService singleton
   */
  public static ConfigService instance() {
    return _instance;
  }

  /**
   * Get a single property from the properties file
   * @param key String key of the property we want to retrieve
   * @return String holding property value
   */
  public String getStringProp(String key) throws PropertyNotFoundException {
    String value = props.getProperty(key);
    if (value == null) {
      throw new PropertyNotFoundException("String property " + key + " was not found in property file");
    }
    return props.getProperty(key);
  }

  /**
   * Get a single integer property from the properties file
   * @param key String key of the property we want to retrieve
   * @return int holding property value
   */
  public int getIntegerProp(String key) throws PropertyNotFoundException, InvalidParamException {
    String valueStr = props.getProperty(key);
    if (valueStr == null) {
      throw new PropertyNotFoundException("Integer property " + key + " was not found in property file");
    }
    try {
      return Integer.parseInt(valueStr);
    } catch(NumberFormatException nfe) {
      throw new InvalidParamException(nfe, "Returned value for key " + key + " is not a valid integer: " + valueStr);
    }
  }
}
