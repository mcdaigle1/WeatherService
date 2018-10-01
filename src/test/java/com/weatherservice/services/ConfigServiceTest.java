package com.weatherservice.services;

import com.weatherservice.exceptions.InvalidParamException;
import com.weatherservice.exceptions.PropertyNotFoundException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ConfigServiceTest {
  @Rule
  public final ExpectedException exception = ExpectedException.none();

  @Test
  public void existingStringPropertyIsRetrieved() {
    String key = "service.owm-service.owm-url";
    String value;
    try {
      value = ConfigService.instance().getStringProp(key);
      assertEquals(value, "http://api.openweathermap.org/data/2.5/weather");
    } catch(PropertyNotFoundException pnfe) {
      fail("Unexpected failure to find property for key: " + key);
    }
  }

  @Test
  public void nonexistentStringPropertyThrowsPropertyNotFoundException() throws PropertyNotFoundException {
    String key = "some.random.key";
    exception.expect(PropertyNotFoundException.class);
    ConfigService.instance().getStringProp(key);
  }

  @Test
  public void existingIntPropertyIsRetrieved() {
    String key = "service.weather-service.cache-timeout-minutes";
    int value;
    try {
      value = ConfigService.instance().getIntegerProp(key);
      assertEquals(value, 15);
    } catch(PropertyNotFoundException pnfe) {
      fail("Unexpected failure to find integer property for key: " + key);
    } catch(InvalidParamException ipe) {
      fail("Unexpected invalid property for key: " + key);
    }
  }

  @Test
  public void nonexistentIntPropertyThrowsPropertyNotFoundException() throws PropertyNotFoundException, InvalidParamException {
    String key = "some.random.key";
    exception.expect(PropertyNotFoundException.class);
    ConfigService.instance().getIntegerProp(key);
  }

  @Test
  public void invalidIntPropertyThrowsPropertyNotFoundException() throws PropertyNotFoundException, InvalidParamException {
    String key = "service.owm-service.owm-url";
    exception.expect(InvalidParamException.class);
    ConfigService.instance().getIntegerProp(key);
  }
}
