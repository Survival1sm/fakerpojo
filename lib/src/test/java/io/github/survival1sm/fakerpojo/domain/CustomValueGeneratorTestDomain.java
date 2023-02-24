package io.github.survival1sm.fakerpojo.domain;

import io.github.survival1sm.fakerpojo.annotations.FakerField;
import java.util.List;

public class CustomValueGeneratorTestDomain {

  @FakerField(type = TestTypes.EMPLOYEE_NUMBER)
  private Integer testEmployeeNumber;

  @FakerField(type = TestTypes.EMPLOYEE_NUMBER_STRING)
  private String testEmployeeNumberString;

  @FakerField(type = TestTypes.TYPE_NANOID)
  private String testNanoId;

  @FakerField(type = TestTypes.LIST_PROJECT_NANOID)
  private List<String> testNanoIdList;

  public CustomValueGeneratorTestDomain() {}

  public Integer getTestEmployeeNumber() {
    return testEmployeeNumber;
  }

  public String getTestEmployeeNumberString() {
    return testEmployeeNumberString;
  }

  public String getTestNanoId() {
    return testNanoId;
  }

  public List<String> getTestNanoIdList() {
    return testNanoIdList;
  }

  @Override
  public String toString() {
    return "CustomValueGeneratorTestDomain{"
        + "testEmployeeNumber="
        + testEmployeeNumber
        + ", testEmployeeNumberString='"
        + testEmployeeNumberString
        + '\''
        + ", testNanoId='"
        + testNanoId
        + '\''
        + ", testNanoIdList="
        + testNanoIdList
        + '}';
  }
}
