package io.github.survival1sm.fakerpojo.domain;

import io.github.survival1sm.fakerpojo.annotations.FakerField;

public class FieldAnnotationTestDomain {

	public FieldAnnotationTestDomain() {
	}

	@FakerField(type = Type.PARAGRAPH)
	private String testParagraph;
	@FakerField(type = Type.FULL_NAME)
	private String testFullName;
	@FakerField(type = Type.LAST_NAME)
	private String testLastName;
	@FakerField(type = Type.FIRST_NAME)
	private String testFirstName;
	@FakerField(type = Type.URL)
	private String testUrl;
	@FakerField(type = Type.PHONE_NUMBER)
	private String testPhoneNumber;
	@FakerField(type = Type.EMAIL)
	private String testEmail;

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
