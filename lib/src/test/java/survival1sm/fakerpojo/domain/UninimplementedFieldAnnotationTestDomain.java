package survival1sm.fakerpojo.domain;


import survival1sm.fakerpojo.annotations.FakerField;

public class UninimplementedFieldAnnotationTestDomain {

  public UninimplementedFieldAnnotationTestDomain(String unimplementedGeneratorField) {
    this.unimplementedGeneratorField = unimplementedGeneratorField;
  }

  @FakerField(type = TestTypes.UNIMPLEMENTED_TEST_TYPE)
  private final String unimplementedGeneratorField;

  public String getUnimplementedGeneratorField() {
    return unimplementedGeneratorField;
  }
}
