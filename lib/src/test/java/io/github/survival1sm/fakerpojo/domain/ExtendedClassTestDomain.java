package io.github.survival1sm.fakerpojo.domain;

import java.util.List;

public class ExtendedClassTestDomain extends ClassTestDomain {

  private List<Integer> extendedFieldList;
  private String extendedFieldString;
  private SetClassTestDomain extendedNestedField;

  public ExtendedClassTestDomain() {}

  public List<Integer> getExtendedFieldList() {
    return extendedFieldList;
  }

  public String getExtendedFieldString() {
    return extendedFieldString;
  }

  public SetClassTestDomain getExtendedNestedField() {
    return extendedNestedField;
  }
}
