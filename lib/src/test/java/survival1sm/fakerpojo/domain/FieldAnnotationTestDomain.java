package survival1sm.fakerpojo.domain;

import survival1sm.fakerpojo.annotations.FakerField;

public class FieldAnnotationTestDomain {

  public FieldAnnotationTestDomain(String testParagraph, String testFullName, String testLastName,
      String testFirstName, String testUrl, String testPhoneNumber, String testEmail) {
    this.testParagraph = testParagraph;
    this.testFullName = testFullName;
    this.testLastName = testLastName;
    this.testFirstName = testFirstName;
    this.testUrl = testUrl;
    this.testPhoneNumber = testPhoneNumber;
    this.testEmail = testEmail;
  }

  @FakerField(type = Type.PARAGRAPH)
  private final String testParagraph;
  @FakerField(type = Type.FULL_NAME)
  private final String testFullName;
  @FakerField(type = Type.LAST_NAME)
  private final String testLastName;
  @FakerField(type = Type.FIRST_NAME)
  private final String testFirstName;
  @FakerField(type = Type.URL)
  private final String testUrl;
  @FakerField(type = Type.PHONE_NUMBER)
  private final String testPhoneNumber;
  @FakerField(type = Type.EMAIL)
  private final String testEmail;

  public String getTestParagraph() {
    return testParagraph;
  }

  public String getTestFullName() {
    return testFullName;
  }

  public String getTestLastName() {
    return testLastName;
  }

  public String getTestFirstName() {
    return testFirstName;
  }

  public String getTestUrl() {
    return testUrl;
  }

  public String getTestPhoneNumber() {
    return testPhoneNumber;
  }

  public String getTestEmail() {
    return testEmail;
  }
}
