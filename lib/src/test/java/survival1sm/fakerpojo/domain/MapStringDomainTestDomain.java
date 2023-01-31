package survival1sm.fakerpojo.domain;

import java.util.Map;

public class MapStringDomainTestDomain {

  private final Map<String, ClassTestDomain> testDomainMap;

  public MapStringDomainTestDomain(
      Map<String, ClassTestDomain> testDomainMap) {
    this.testDomainMap = testDomainMap;
  }

  public Map<String, ClassTestDomain> getTestDomainMap() {
    return testDomainMap;
  }

}
