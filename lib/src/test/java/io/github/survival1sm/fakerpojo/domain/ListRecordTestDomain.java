package io.github.survival1sm.fakerpojo.domain;

import java.util.List;

public class ListRecordTestDomain {

  private final List<RecordTestDomain> recordTestDomainList;

  public ListRecordTestDomain(
      List<RecordTestDomain> recordTestDomainList) {
    this.recordTestDomainList = recordTestDomainList;
  }

  public List<RecordTestDomain> getRecordTestDomainList() {
    return recordTestDomainList;
  }
}
