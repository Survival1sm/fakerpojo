# Faker Pojo

This library uses [datafaker](https://github.com/datafaker-net/datafaker) to create Java Objects populated with 
fake data.

This library has been developed for use in testing environments, though, some may find use for it elsewhere.

### Features

* Automatically populate objects by detecting the underlying field data type using reflection
* Support for Records, Lists, Sets, Maps, and Nested objects
* Field level annotation allowing for custom data generation
* Built for Java17

## Usage

In the pom.xml, add the following fragment to the `dependencies` section:

```xml

<dependency>
  <groupId>survival1sm</groupId>
  <artifactId>fakerpojo</artifactId>
  <version>0.0.1</version>
</dependency>
```

For Gradle users, add the following to your build.gradle file.

```groovy
dependencies {
    implementation 'survival1sm:fakerpojo:0.0.1'
}

```

### Get started

In your Java code:

```java
public class FakerPojo {

  public FakerPojo(String someStringProperty, String firstName, Instant lastUpdatedAt, List<Integer> projectIdList) {
    this.someStringProperty = someStringProperty;
    this.firstName = firstName;
    this.lastUpdatedAt = lastUpdatedAt;
    this.projectIdList = projectIdList;
  }

  private final String someStringProperty;
  @FakerField(type = Type.FIRST_NAME)
  private final String firstName;
  private final Instant lastUpdatedAt;
  private final List<Integer> projectIdList;

  //...getters
}
```

```java
@Test
void someTest() throws Exception{
    MyPojo myPojo = Faker.Builder
      .fromPojo(MyPojo.class)
      .getFirst()
      .build();

    System.out.println(myPojo.toString());

// FakerPojo{someStringProperty='Dolore et ', firstName='Rigoberto', lastUpdatedAt=2019-07-05T02:37:58.910Z, projectIdList=[228099, 443978]}
}
```

### Builder Options

* getFirst()
* toSet()
* toList()
* toMapOnKey(String key)
* usingValueMapList(List<Map<String, Object>> valueMapList)

### Builder Modifiers

* withOverrides(Map<String, Object> overrides)
* withRecords()
* withUniqueOnKey()

#### Note:

Not all builder options/modifiers are available for all builders, some may throw an UnsupportedOperationException 
exception

### Custom Data Generators

#####You can add your own custom data generators in a few steps:

Add your custom types to the existing types:

```java
public class TestTypes extends Type {

  public static final String EMPLOYEE_NUMBER = "EMPLOYEE_NUMBER";
  public static final String EMPLOYEE_NUMBER_STRING = "EMPLOYEE_NUMBER_STRING";
  public static final String TYPE_NANOID = "TYPE_NANOID";
  public static final String LIST_PROJECT_NANOID = "LIST_PROJECT_NANOID";
}
```

Provide an implementation of FieldValueGenerator for your new types:

```java
public class TestValueGenerators {

  private static final Faker faker = new Faker();

  public static FieldValueGenerator employeeNumberValueGenerator = (FakerFieldProps fieldProps) -> faker.number().numberBetween(20, 1200000);

  public static FieldValueGenerator employeeNumberStringValueGenerator = (FakerFieldProps fieldProps) -> String.valueOf(faker.number().numberBetween(20, 1200000));

  public static FieldValueGenerator typeNanoIdValueGenerator = (FakerFieldProps fieldProps) -> NanoIdService.getNanoId(NanoPrefix.type);

  public static FieldValueGenerator listProjectNanoIdValueGenerator = (FakerFieldProps fieldProps) -> {
        IntStream range = IntStream.range(0, fieldProps.getRecords());
        
        return range
            .mapToObj((int i) -> NanoIdService.getNanoId(NanoPrefix.project))
            .collect(Collectors.toList());
      };
}
```

Register your new types:

```java
  @BeforeAll
static void addTestValueGenerator() {
    FakerPojo.addFieldValuesGenerator(TestTypes.EMPLOYEE_NUMBER, TestValueGenerators.employeeNumberValueGenerator);
    FakerPojo.addFieldValuesGenerator(TestTypes.EMPLOYEE_NUMBER_STRING,TestValueGenerators.employeeNumberStringValueGenerator);
    FakerPojo.addFieldValuesGenerator(TestTypes.TYPE_NANOID, TestValueGenerators.typeNanoIdValueGenerator);
    FakerPojo.addFieldValuesGenerator(TestTypes.LIST_PROJECT_NANOID,TestValueGenerators.listProjectNanoIdValueGenerator);
}
```

Use your custom types

```java
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

  // .. getters
}
```

```java
@Test
void build_custom_value_generator_returns_expected_values() throws Exception {
    CustomValueGeneratorTestDomain test = Builder.fromPojo(CustomValueGeneratorTestDomain.class).getFirst().build();
    
    System.out.println(test.toString());
}

// CustomValueGeneratorTestDomain{testEmployeeNumber=1038479, testEmployeeNumberString='651836', testNanoId='TYP-ltwtfbRFst', testNanoIdList=[PRJ-vNB8cnxlPk, PRJ-5CG0RSpDx4]}
```

Usage with Locales
-----

```java
@BeforeAll
static void setFakerLocale(){
    FakerPojo.setLocale(new Locale("en","US"));
}
```

Types
-----

A complete list of default types:

* Full Name
* Last Name
* First Name
* Paragraph
* URL
* Phone Number
* Email
* City
* State
* Integer
* Boolean
* Double
* String
* Date
* Local Date
* Local Datetime
* Float
* Long
* Instant
* Enum
* UUID
* Duration
* Class (POJO)
* List
* Map
* Set

Contributing
-------------
Thank you for your interest! PR's welcome!

LICENSE
-------
Licensed under the Apache License, Version 2.0