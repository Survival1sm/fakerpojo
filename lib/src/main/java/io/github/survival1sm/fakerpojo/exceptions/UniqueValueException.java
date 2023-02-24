package io.github.survival1sm.fakerpojo.exceptions;

/** Exception thrown when a unique value cannot be generated after 200 attempts. */
public class UniqueValueException extends InstantiationException {

  public UniqueValueException(String errorMessage) {
    super(errorMessage);
  }
}
