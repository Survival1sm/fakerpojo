package survival1sm.fakerpojo.service;


import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.datafaker.Faker;
import survival1sm.fakerpojo.domain.FakerFieldProps;
import survival1sm.fakerpojo.enums.NanoPrefix;
import survival1sm.fakerpojo.generators.FieldValueGenerator;

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
