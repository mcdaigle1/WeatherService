package com.weatherservice.controllers;

/*
 * Wind controller - returns weather data retrieved from a third party source.  The assumption is that
 * there will eventually be enough wind related data to warrant a dedicated controller and enough other
 * categories of weather information to justify a suite of other controllers.
 */

import com.weatherservice.exceptions.BaseException;
import com.weatherservice.services.weather.CachedWeatherService;
import com.weatherservice.services.weather.OwmWeatherService;
import com.weatherservice.services.weather.WeatherService;
import com.weatherservice.utilities.ApiResponseUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

@RestController
public class WindController extends BaseController {

  public WindController() {
    super();
  }

  /**
   * Get the wind data for a given zip code
   * @param zipcode - the zip code extracted from the URL path for which we want to get wind data
   * @return String with a status of "success" and a payload of wind data in JSON format or a status of "error" and
   * and a payload of error information.
   */
  @RequestMapping("/wind/{zipCode}")
  @GET
  @Produces({"application/json"})
  public String getWind(@PathVariable("zipCode") String zipcode) {
    String windDataJSON = "";

    try {
      valid.mustExist("zipcode", zipcode).is5DigitZipcode(zipcode);
      WeatherService weatherService = new CachedWeatherService(new OwmWeatherService());
      windDataJSON = weatherService.getWindInfoForZip(zipcode);
    } catch (BaseException be) {
      l.error("Base error when attmepting to get weather info for zipcode: " + zipcode, be);
      return ApiResponseUtil.getErrorJSON(be);
    } catch (Exception e) {
      String errMsg = "General error when attempting to get weather info for zipcode: " + zipcode;
      l.error(errMsg, e);
      return ApiResponseUtil.getErrorJSON(new BaseException(e, errMsg));
    }

    return ApiResponseUtil.getSuccessJSON(windDataJSON);
  }

  /**
   * Bust the zipcode cache, removing and resetting all stored values.  Note that while there is very little impact to
   * busting the cache, in general, I would move system administrative tasks into their own controllers and all actions
   * would live behind appropriate authentication and authorization safeguards.  That is, however, beyond the scope of
   * this project.
   */
  @RequestMapping("/bustCache")
  @GET
  @Produces({"application/json"})
  public String bustCache() {
    try {
      CachedWeatherService cachedWeatherService = new CachedWeatherService(new OwmWeatherService());
      cachedWeatherService.bustCache();
    } catch (Exception e) {
      l.error("General error when attempting to bust cache", e);
      return ApiResponseUtil.getErrorJSON(new BaseException(e, "General error when attempting to bust cache"));
    }

    return ApiResponseUtil.getSuccessJSON("message:cache_busted!");
  }

}

