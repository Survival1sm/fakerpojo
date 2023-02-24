package io.github.survival1sm.fakerpojo.exceptions;

import io.github.survival1sm.fakerpojo.domain.Type;
import io.github.survival1sm.fakerpojo.generators.FieldValueGenerator;

/** Exception thrown when a {@link Type} is used without a matching {@link FieldValueGenerator}. */
public class FieldGeneratorNotImplementedException extends IllegalArgumentException {

  public FieldGeneratorNotImplementedException(String errorMessage) {
    super(errorMessage);
  }
}
