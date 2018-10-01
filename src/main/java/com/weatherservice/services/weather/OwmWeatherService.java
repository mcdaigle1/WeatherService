package com.weatherservice.services.weather;

/**
 * OwmWeatherService - implements weather data functions defined in the WeatherService for the
 * openweathermap.org external service.
 */

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.weatherservice.exceptions.BaseException;
import com.weatherservice.exceptions.HttpRequestException;
import com.weatherservice.exceptions.PropertyNotFoundException;
import com.weatherservice.services.BaseService;
import com.weatherservice.services.ConfigService;
import com.weatherservice.services.caching.CacheServiceFactory;
import com.weatherservice.utilities.HttpRequestUtil;

import java.util.HashMap;

public class OwmWeatherService extends BaseService implements WeatherService {
  private String apiKey;
  private String owmUrl;

  public OwmWeatherService() throws BaseException {
    super();
    try {
      ConfigService configService = ConfigService.instance();
      owmUrl = configService.getStringProp("service.owm-service.owm-url");
      apiKey = configService.getStringProp("service.owm-service.api-key");
    } catch(PropertyNotFoundException pnfe) {
      l.error("Property could not be found when initializing OwmWeatherService", pnfe);
      throw pnfe;
    }
  }

  /**
   * This queries the openweathermap.com for wind information for any given US zip code.
   * @param zipcode - String holding five digit US zip code
   * @return - String holding JSON wind data
   * @throws HttpRequestException
   */
  public String getWindInfoForZip(String zipcode) throws HttpRequestException {
    String responseValue = null;

    HashMap<String, String> params = new HashMap<>();
    params.put("zip", zipcode + ",us");
    params.put("APPID", apiKey);
    String response = HttpRequestUtil.doQuery(owmUrl, params);

    JsonParser jsonParser = new JsonParser();
    JsonObject responseObject = jsonParser.parse(response).getAsJsonObject();

    System.out.println("++++++++++++++++ Response");
    System.out.println(responseObject.toString());

    responseValue = responseObject.getAsJsonObject("wind").toString();

    CacheServiceFactory.getService("windCache").add(zipcode, responseValue);

    return responseValue;
  }
}
