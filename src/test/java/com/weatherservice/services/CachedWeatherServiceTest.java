package com.weatherservice.services;

import com.weatherservice.services.caching.StringCache;
import com.weatherservice.services.weather.CachedWeatherService;
import com.weatherservice.services.weather.OwmWeatherService;
import io.specto.hoverfly.junit.rule.HoverflyRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;

import static io.specto.hoverfly.junit.core.SimulationSource.dsl;
import static io.specto.hoverfly.junit.dsl.HoverflyDsl.service;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CachedWeatherServiceTest {

  static String owmTestResponse = "{\"coord\":{\"lon\":-119.7,\"lat\":34.42},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"base\":\"stations\",\"main\":{\"temp\":292.04,\"pressure\":1012,\"humidity\":72,\"temp_min\":290.15,\"temp_max\":294.25},\"visibility\":16093,\"wind\":{\"speed\":3.1,\"deg\":250},\"clouds\":{\"all\":1},\"dt\":1538361300,\"sys\":{\"type\":1,\"id\":492,\"message\":0.0041,\"country\":\"US\",\"sunrise\":1538402015,\"sunset\":1538444554},\"id\":420006298,\"name\":\"Santa Barbara\",\"cod\":200}";
  String cachedResponse = "{\"speed\":3.1,\"deg\":250}";
  String zipcode = "93101";

  @ClassRule
  public static HoverflyRule hoverflyRule = HoverflyRule.inSimulationMode(dsl(
          service("api.openweathermap.org")
          .get("/data/2.5/weather")
          .queryParam("zip", "93101,us")
          .queryParam("APPID", "49fb5044004b17ac55d35042110135cf")
          .willReturn(success(owmTestResponse, "application/json"))
  ));

  @Mock
  StringCache windCache;

  @InjectMocks
  private CachedWeatherService weatherService;

  @Before
  public void setUp() throws Exception {
    initMocks(this);
    weatherService = new CachedWeatherService(new OwmWeatherService());
  }

  @Test
  public void cachedWeatherServiceInfoIsRetrieved() throws Exception {
    Whitebox.setInternalState(weatherService, "windCache", windCache);
    when(windCache.get(zipcode)).thenReturn(cachedResponse);
    String windInfo = weatherService.getWindInfoForZip(zipcode);

    assertEquals(cachedResponse, windInfo);
    verify(windCache, times(1));
  }

  @Test
  public void uncachedWeatherServiceInfoIsRetrieved() throws Exception {
    String finalExpectedResponse = cachedResponse;

    Whitebox.setInternalState(weatherService, "windCache", windCache);
    when(windCache.get(zipcode)).thenReturn(null);
    String windInfo = weatherService.getWindInfoForZip(zipcode);

    assertEquals(finalExpectedResponse, windInfo);
  }
}
