package com.weatherservice.services;

/*
 * ValidationService is a singleton (like most internal services) that does basic parameter verification.  The methods
 * follow a basic stream pattern, returning an instance of the validation object on success and throwing
 * InvalidParamException on validation failure.  In this way, the parameter validations can be chained.
 *
 * MCD TODO - look into making this a utility (static methods) as opposed to a service (singleton).
 */

import com.weatherservice.exceptions.InvalidParamException;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationService {
  private static final ValidationService _instance = new ValidationService();
  private EmailValidator emailValidator = EmailValidator.getInstance();

  private ValidationService() {

  }

  public static ValidationService instance() {
    return _instance;
  }

  /**
   * Verfifies the given parameter exists
   * @param paramName String name of the parameter for messaging purposes
   * @param param Generic object holding parameter
   * @return this ValidationService, which allows us to chain these validations
   * @throws InvalidParamException if param is null
   */
  public <T> ValidationService mustExist(String paramName, T param) throws InvalidParamException {
    if (param == null)
      throw new InvalidParamException("Required parameter " + paramName + " does not exist.");
    return this;
  }

  /**
   * Verifies that the given string is not empty.
   * @param paramName String name of the parameter for messaging purposes
   * @param param Generic object holding parameter
   * @return this ValidationService, which allows us to chain these validations
   * @throws InvalidParamException if param is emtpy string
   */
  public ValidationService notEmpty(String paramName, String param) throws InvalidParamException {
    if (param.isEmpty())
      throw new InvalidParamException("Required parameter " + paramName + " is emtpy.");
    return this;
  }

  /**
   * Verifies that the email format is valid
   * @param email String holding email address
   * @return this ValidationService, which allows us to chain these validations
   * @throws InvalidParamException if email format is invalid
   */
  public ValidationService properEmailFormat(String email) throws InvalidParamException {
    if (!emailValidator.isValid(email))
      throw new InvalidParamException("Email " + email + " is not valid.");
    return this;
  }

  /**
   * This is the simplest zip code validator, checking that the code is five digits and numeric.
   * @param zipcode String holding five digit US zip code
   * @return this ValidationService, which allows us to chain these validations
   * @throws InvalidParamException if zip code is not five digits
   */
  public ValidationService is5DigitZipcode(String zipcode) throws InvalidParamException {
    String regex = "^[0-9]{5}$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(zipcode);
    if(!matcher.matches()) {
      throw new InvalidParamException("Zipcode " + zipcode + " is invalid.");
    }
    return this;
  }

  /**
   * Verify the provided string is at least the provided min length
   * @param paramName - String paramter name.  This is used for display purposes only
   * @param param     - String param whose min length we want to verify
   * @param minLength - Integer value holding the min length to verify
   * @return this ValidationService, which allows us to chain these validations
   * @throws InvalidParamException if invalid
   */
  public ValidationService minLength(String paramName, String param, Integer minLength) throws InvalidParamException {
    if (param.length() < minLength)
      throw new InvalidParamException("Parameter " + paramName + " is shorter than the required " + minLength + " characters.");
    return this;
  }

  /**
   * Verify latitude is between -90 and 90
   * @param latitude - Double latitude value
   * @return this ValidationService, which allows us to chain these validations
   * @throws InvalidParamException if invalid
   */
  public ValidationService isLatititude(Double latitude) throws InvalidParamException {
    if (latitude > 90 || latitude < -90)
      throw new InvalidParamException("Parameter " + latitude + " is an out of bounds latitude.");
    return this;
  }

  /**
   * Verify longitude is between -180 and 180
   * @param longitude - Double latitude value
   * @return this ValidationService, which allows us to chain these validations
   * @throws InvalidParamException if invalid
   */
  public ValidationService isLongitude(Double longitude) throws InvalidParamException {
    if (longitude > 180 || longitude < -180)
      throw new InvalidParamException("Parameter " + longitude + " is an out of bounds longitude.");
    return this;
  }
}
