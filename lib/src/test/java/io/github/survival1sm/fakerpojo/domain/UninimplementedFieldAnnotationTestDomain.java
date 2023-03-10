package io.github.survival1sm.fakerpojo.domain;

import io.github.survival1sm.fakerpojo.annotations.FakerField;

public class UninimplementedFieldAnnotationTestDomain {

  @FakerField(type = TestTypes.UNIMPLEMENTED_TEST_TYPE)
  private String unimplementedGeneratorField;

  public UninimplementedFieldAnnotationTestDomain(String unimplementedGeneratorField) {
    this.unimplementedGeneratorField = unimplementedGeneratorField;
  }

  public String getUnimplementedGeneratorField() {
    return unimplementedGeneratorField;
  }
}
