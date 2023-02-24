package io.github.survival1sm.fakerpojo.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.survival1sm.fakerpojo.FakerPojo;
import io.github.survival1sm.fakerpojo.domain.DefaultFakerFieldProps;
import io.github.survival1sm.fakerpojo.domain.ListClassTestDomain;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;

class ModifierTest {

  @Test
  void build_set_default_props_uses_new_props() throws Exception {

    ListClassTestDomain originalTestList =
        FakerPojo.Builder.fromPojo(ListClassTestDomain.class).getFirst().build();

    assertEquals(1, originalTestList.getTestString().size());
    assertEquals(600, originalTestList.getTestDuration().get(0).getSeconds());
    DefaultFakerFieldProps fakerFieldProps = new DefaultFakerFieldProps();

    fakerFieldProps.setRecords(10);
    fakerFieldProps.setChronoUnit(ChronoUnit.HOURS);

    FakerPojo.setDefaultFakerFieldProps(fakerFieldProps);

    ListClassTestDomain updatedTestList =
        FakerPojo.Builder.fromPojo(ListClassTestDomain.class).getFirst().build();

    assertEquals(10, updatedTestList.getTestString().size());
    assertEquals(36000, updatedTestList.getTestDuration().get(0).getSeconds());

    FakerPojo.resetDefaultFakerFieldProps();

    ListClassTestDomain originalTestListTwo =
        FakerPojo.Builder.fromPojo(ListClassTestDomain.class).getFirst().build();

    assertEquals(1, originalTestListTwo.getTestString().size());
    assertEquals(600, originalTestListTwo.getTestDuration().get(0).getSeconds());
  }
}
