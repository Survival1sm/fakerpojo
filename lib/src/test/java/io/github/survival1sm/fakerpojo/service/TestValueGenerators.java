package io.github.survival1sm.fakerpojo.service;


import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.github.survival1sm.fakerpojo.domain.FakerFieldProps;
import io.github.survival1sm.fakerpojo.enums.NanoPrefix;
import io.github.survival1sm.fakerpojo.generators.FieldValueGenerator;
import net.datafaker.Faker;

public class TestValueGenerators {

  private static final Faker faker = new Faker();

  public static FieldValueGenerator employeeNumberValueGenerator =
      (FakerFieldProps fieldProps) -> faker.number().numberBetween(20, 1200000);

  public static FieldValueGenerator employeeNumberStringValueGenerator =
      (FakerFieldProps fieldProps) -> String.valueOf(faker.number().numberBetween(20, 1200000));

  public static FieldValueGenerator typeNanoIdValueGenerator =
      (FakerFieldProps fieldProps) -> NanoIdService.getNanoId(NanoPrefix.type);

  public static FieldValueGenerator listProjectNanoIdValueGenerator =
      (FakerFieldProps fieldProps) -> {
        IntStream range = IntStream.range(0, fieldProps.getRecords());
        return range
            .mapToObj((int i) -> NanoIdService.getNanoId(NanoPrefix.project))
            .collect(Collectors.toList());
      };
}
