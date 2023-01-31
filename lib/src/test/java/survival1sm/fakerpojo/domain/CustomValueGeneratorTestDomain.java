package survival1sm.fakerpojo.domain;

import java.util.List;
import survival1sm.fakerpojo.annotations.FakerField;

public class CustomValueGeneratorTestDomain {

  public CustomValueGeneratorTestDomain(Integer testEmployeeNumber, String testEmployeeNumberString,
      String testNanoId, List<String> testNanoIdList) {
    this.testEmployeeNumber = testEmployeeNumber;
    this.testEmployeeNumberString = testEmployeeNumberString;
    this.testNanoId = testNanoId;
    this.testNanoIdList = testNanoIdList;
  }

  @FakerField(type = TestTypes.EMPLOYEE_NUMBER)
  private final Integer testEmployeeNumber;
  @FakerField(type = TestTypes.EMPLOYEE_NUMBER_STRING)
  private final String testEmployeeNumberString;
  @FakerField(type = TestTypes.TYPE_NANOID)
  private final String testNanoId;
  @FakerField(type = TestTypes.LIST_PROJECT_NANOID)
  private final List<String> testNanoIdList;

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
    return "CustomValueGeneratorTestDomain{" +
           "testEmployeeNumber=" + testEmployeeNumber +
           ", testEmployeeNumberString='" + testEmployeeNumberString + '\'' +
           ", testNanoId='" + testNanoId + '\'' +
           ", testNanoIdList=" + testNanoIdList +
           '}';
  }
}
