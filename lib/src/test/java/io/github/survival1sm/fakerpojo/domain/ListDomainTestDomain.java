package io.github.survival1sm.fakerpojo.domain;

import java.util.List;

public class ListDomainTestDomain {

  private final List<ClassTestDomain> classTestDomainList;

  public ListDomainTestDomain(
      List<ClassTestDomain> classTestDomainList) {
    this.classTestDomainList = classTestDomainList;
  }

  public List<ClassTestDomain> getClassTestDomainList() {
    return classTestDomainList;
  }

}
