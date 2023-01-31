package io.github.survival1sm.fakerpojo.domain;

import java.util.List;
import java.util.Map;

public class ListMapTestDomain {

  private final List<Map<String, ClassTestDomain>> mapStringDomainList;

  public ListMapTestDomain(
      List<Map<String, ClassTestDomain>> mapStringDomainList) {
    this.mapStringDomainList = mapStringDomainList;
  }

  public List<Map<String, ClassTestDomain>> getMapStringDomainList() {
    return mapStringDomainList;
  }
}
