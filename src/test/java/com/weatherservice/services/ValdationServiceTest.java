package com.weatherservice.services;

import com.weatherservice.exceptions.InvalidParamException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ValdationServiceTest {
  @Rule
  public final ExpectedException exception = ExpectedException.none();

  @Test
  public void existingValueReturnsValidationServiceClass() {
    String strValue = "someValue";
    try {
      ValidationService validationService = ValidationService.instance().mustExist("Some Value", strValue);
      assertEquals(validationService.getClass(), ValidationService.class);
    } catch(InvalidParamException ipe) {
      fail("Unexpected failure to validate existing value");
    }
  }

  @Test
  public void nonexistantValueThrowsInvalidParamException() throws InvalidParamException {
    String strValue = null;
    exception.expect(InvalidParamException.class);
    ValidationService.instance().mustExist("Some Value", strValue);
  }

  @Test
  public void populatedStringReturnsValidationServiceClass() {
    String strValue = "someValue";
    try {
      ValidationService validationService = ValidationService.instance().notEmpty("Some Value", strValue);
      assertEquals(validationService.getClass(), ValidationService.class);
    } catch(InvalidParamException ipe) {
      fail("Unexpected failure to validate populated string");
    }
  }

  @Test
  public void emptyStringThrowsInvalidParamException() throws InvalidParamException {
    String strValue = "";
    exception.expect(InvalidParamException.class);
    ValidationService.instance().notEmpty("Some Value", strValue);
  }

  @Test
  public void validEmailReturnsValidationServiceClass() {
    String email = "joeblow@somedomain.com";
    try {
      ValidationService validationService = ValidationService.instance().properEmailFormat(email);
      assertEquals(validationService.getClass(), ValidationService.class);
    } catch(InvalidParamException ipe) {
      fail("Unexpected failure to validate valid email format");
    }
  }

  @Test
  public void invalidEmailThrowsInvalidParamException() throws InvalidParamException {
    String email = "joeblow@somedomaincom";
    exception.expect(InvalidParamException.class);
    ValidationService.instance().properEmailFormat(email);
  }

  @Test
  public void validZipReturnsValidationServiceClass() {
    try {
      ValidationService validationService = ValidationService.instance().is5DigitZipcode("93101");
      assertEquals(validationService.getClass(), ValidationService.class);
    } catch(InvalidParamException ipe) {
      fail("Unexpected failure to validate zipcode 93101");
    }
  }

  @Test
  public void inalidZipThrowsInvalidParamException() throws InvalidParamException {
    exception.expect(InvalidParamException.class);
    ValidationService.instance().is5DigitZipcode("9310X");
  }

  @Test
  public void minLengthStringReturnsValidationServiceClass() {
    String strValue = "hello";
    try {
      ValidationService validationService = ValidationService.instance().minLength("Some String", strValue, 3);
      assertEquals(validationService.getClass(), ValidationService.class);
    } catch(InvalidParamException ipe) {
      fail("Unexpected failure to validate string 'hello' with min leghth 3");
    }
  }

  @Test
  public void oversizedStringThrowsInvalidParamException() throws InvalidParamException {
    String strValue = "hello";
    exception.expect(InvalidParamException.class);
    ValidationService.instance().minLength("Some String", strValue, 10);
  }

  @Test
  public void validLatitudeReturnsValidationServiceClass() {
    try {
      ValidationService validationService = ValidationService.instance().isLatititude(45.2);
      assertEquals(validationService.getClass(), ValidationService.class);
    } catch(InvalidParamException ipe) {
      fail("Unexpected failure to validate latitude of 45.2");
    }
  }

  @Test
  public void invalidLatitudeThrowsInvalidParamException() throws InvalidParamException {
    exception.expect(InvalidParamException.class);
    ValidationService.instance().isLatititude(103.45);
  }

  @Test
  public void validLongitudeReturnsValidationServiceClass() {
    try {
      ValidationService validationService = ValidationService.instance().isLongitude(-120.2);
      assertEquals(validationService.getClass(), ValidationService.class);
    } catch(InvalidParamException ipe) {
      fail("Unexpected failure to validate longitude of -120.2");
    }
  }

  @Test
  public void invalidLongitudeThrowsInvalidParamException() throws InvalidParamException {
    exception.expect(InvalidParamException.class);
    ValidationService.instance().isLatititude(303.45);
  }
}