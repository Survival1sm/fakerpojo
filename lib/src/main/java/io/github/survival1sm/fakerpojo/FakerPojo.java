package io.github.survival1sm.fakerpojo;

import io.github.survival1sm.fakerpojo.FakerPojo.Builder.PojoBuilder.DataBuilder;
import io.github.survival1sm.fakerpojo.FakerPojo.Builder.PojoBuilder.ListBuilder;
import io.github.survival1sm.fakerpojo.FakerPojo.Builder.PojoBuilder.MapBuilder;
import io.github.survival1sm.fakerpojo.FakerPojo.Builder.PojoBuilder.SetBuilder;
import io.github.survival1sm.fakerpojo.annotations.FakerField;
import io.github.survival1sm.fakerpojo.domain.DefaultFakerFieldProps;
import io.github.survival1sm.fakerpojo.domain.FakerFieldProps;
import io.github.survival1sm.fakerpojo.domain.Type;
import io.github.survival1sm.fakerpojo.exceptions.UniqueValueException;
import io.github.survival1sm.fakerpojo.generators.DefaultValueGenerators;
import io.github.survival1sm.fakerpojo.generators.FieldValueGenerator;
import io.github.survival1sm.fakerpojo.service.FieldValueService;
import io.github.survival1sm.fakerpojo.service.PojoDataService;
import io.github.survival1sm.fakerpojo.util.Utilities;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.naming.OperationNotSupportedException;

/** The root class containing all {@link Builder} implementations and global configurations. */
public class FakerPojo {

  private static final String UNIQUE_VALUE_EXCEPTION_TEMPLATE =
      "Unable to generate unique value for key %s after %s attempts";

  private FakerPojo() {
    throw new UnsupportedOperationException("FakerPojo should not be instantiated");
  }

  /**
   * Return the global {@link FakerFieldProps} to the default values of {@link
   * DefaultFakerFieldProps}.
   */
  public static void resetDefaultFakerFieldProps() {
    PojoDataService.setDefaultFakerFieldProps(new DefaultFakerFieldProps());
  }

  /**
   * Apply a custom {@link FakerFieldProps} globally for all value generation.
   *
   * @param fakerFieldProps A {@link FakerFieldProps} with customized values
   */
  public static void setDefaultFakerFieldProps(FakerFieldProps fakerFieldProps) {
    PojoDataService.getDefaultFakerFieldProps().setLength(fakerFieldProps.getLength());
    PojoDataService.getDefaultFakerFieldProps().setDecimals(fakerFieldProps.getDecimals());
    PojoDataService.getDefaultFakerFieldProps().setMin(fakerFieldProps.getMin());
    PojoDataService.getDefaultFakerFieldProps().setMax(fakerFieldProps.getMax());
    PojoDataService.getDefaultFakerFieldProps().setRecords(fakerFieldProps.getRecords());
    PojoDataService.getDefaultFakerFieldProps().setFrom(fakerFieldProps.getFrom());
    PojoDataService.getDefaultFakerFieldProps().setTo(fakerFieldProps.getTo());
    PojoDataService.getDefaultFakerFieldProps().setChronoUnit(fakerFieldProps.getChronoUnit());
  }

  /**
   * Set the {@link Locale} for the underlying {@link net.datafaker.Faker}.
   *
   * @param locale the {@link Locale}
   */
  public static void setLocale(Locale locale) {
    PojoDataService.setFaker(new net.datafaker.Faker(locale));
  }

  /**
   * Add a {@link Type} and corresponding {@link FieldValueGenerator} to be used during data
   * generation.
   *
   * @param type The {@link Type} of the data being generated
   * @param fieldValuesGenerator An implementation of {@link FieldValueGenerator} that will create
   *     the expected data
   */
  public static void addFieldValuesGenerator(
      String type, FieldValueGenerator fieldValuesGenerator) {
    FieldValueService.getFieldValuesGeneratorMap().putIfAbsent(type, fieldValuesGenerator);
  }

  /**
   * The root Builder containing implementations for {@link ClassBuilder}, {@link DataBuilder},
   * {@link ListBuilder}, {@link MapBuilder}, {@link PojoBuilder} and {@link SetBuilder}.
   */
  public static class Builder {

    private static List<Integer> uniqueOnKeyList = new ArrayList<>();

    private Builder() {
      FieldValueService.getFieldValuesGeneratorMap()
          .put(Type.STRING, DefaultValueGenerators.stringGenerator);
      FieldValueService.getFieldValuesGeneratorMap()
          .put(Type.BOOLEAN, DefaultValueGenerators.booleanGenerator);
      FieldValueService.getFieldValuesGeneratorMap()
          .put(Type.DATE, DefaultValueGenerators.dateGenerator);
      FieldValueService.getFieldValuesGeneratorMap()
          .put(Type.LOCALDATE, DefaultValueGenerators.localDateGenerator);
      FieldValueService.getFieldValuesGeneratorMap()
          .put(Type.LOCALDATETIME, DefaultValueGenerators.localDateTimeGenerator);
      FieldValueService.getFieldValuesGeneratorMap()
          .put(Type.OFFSETDATETIME, DefaultValueGenerators.offsetDateTimeValueGenerator);
      FieldValueService.getFieldValuesGeneratorMap()
          .put(Type.INSTANT, DefaultValueGenerators.instantGenerator);
      FieldValueService.getFieldValuesGeneratorMap()
          .put(Type.DURATION, DefaultValueGenerators.durationGenerator);
      FieldValueService.getFieldValuesGeneratorMap()
          .put(Type.PARAGRAPH, DefaultValueGenerators.paragraphGenerator);
      FieldValueService.getFieldValuesGeneratorMap()
          .put(Type.FULL_NAME, DefaultValueGenerators.fullNameGenerator);
      FieldValueService.getFieldValuesGeneratorMap()
          .put(Type.LAST_NAME, DefaultValueGenerators.lastNameGenerator);
      FieldValueService.getFieldValuesGeneratorMap()
          .put(Type.FIRST_NAME, DefaultValueGenerators.firstNameGenerator);
      FieldValueService.getFieldValuesGeneratorMap()
          .put(Type.URL, DefaultValueGenerators.urlGenerator);
      FieldValueService.getFieldValuesGeneratorMap()
          .put(Type.PHONE_NUMBER, DefaultValueGenerators.phoneNumberGenerator);
      FieldValueService.getFieldValuesGeneratorMap()
          .put(Type.EMAIL, DefaultValueGenerators.emailGenerator);
      FieldValueService.getFieldValuesGeneratorMap()
          .put(Type.UUID, DefaultValueGenerators.uuidGenerator);
      FieldValueService.getFieldValuesGeneratorMap()
          .put(Type.DOUBLE, DefaultValueGenerators.doubleGenerator);
      FieldValueService.getFieldValuesGeneratorMap()
          .put(Type.FLOAT, DefaultValueGenerators.floatGenerator);
      FieldValueService.getFieldValuesGeneratorMap()
          .put(Type.LONG, DefaultValueGenerators.longGenerator);
      FieldValueService.getFieldValuesGeneratorMap()
          .put(Type.INTEGER, DefaultValueGenerators.integerGenerator);
      FieldValueService.getFieldValuesGeneratorMap()
          .put(Type.INT, DefaultValueGenerators.integerGenerator);
      FieldValueService.getFieldValuesGeneratorMap()
          .put(Type.CITY, DefaultValueGenerators.cityGenerator);
      FieldValueService.getFieldValuesGeneratorMap()
          .put(Type.STATE, DefaultValueGenerators.stateGenerator);
    }

    public static void resetUniqueOnKeyList() {
      Builder.uniqueOnKeyList = new ArrayList<>();
    }

    public static <T> ClassBuilder<T> fromClass(Class<T> baseClass) {
      return new ClassBuilder<>(baseClass);
    }

    public static <T> PojoBuilder<T> fromPojo(Class<T> baseClass) {
      return new PojoBuilder<>(baseClass);
    }

    /**
     * Generate fake data for simple Wrapper style classes, IE, {@link Integer}, {@link Float},
     * {@link String}, etc.
     *
     * @param <T> The {@link Class} for the generated data
     */
    public static class ClassBuilder<T> extends Builder
        implements io.github.survival1sm.fakerpojo.builders.ClassBuilder<T> {

      private final Class<T> baseClass;

      public ClassBuilder(Class<T> baseClass) {
        this.baseClass = baseClass;
      }

      @Override
      public SetBuilder<T> toSet() {
        return new SetBuilder<>(baseClass);
      }

      @Override
      public ListBuilder<T> toList() {
        return new ListBuilder<>(baseClass);
      }

      @Override
      public DataBuilder<T> getFirst() {
        return new DataBuilder<>(this.baseClass);
      }

      /**
       * Generate a single instance of a fake pojo.
       *
       * @param <T> The {@link Class} for the generated data
       */
      public static class DataBuilder<T> extends Builder
          implements io.github.survival1sm.fakerpojo.builders.DataBuilder<T> {

        private final Class<T> baseClass;

        protected DataBuilder(Class<T> baseClass) {
          this.baseClass = baseClass;
        }

        public DataBuilder<T> withOverrides(Map<String, Object> overrides) {
          throw new UnsupportedOperationException(
              "This would override the single generated fake value");
        }

        public DataBuilder<T> withUniqueOnKey(String key) {
          throw new UnsupportedOperationException(
              "This would ensure the single generated value is unique");
        }

        @Override
        public T build()
            throws NoSuchFieldException, InvocationTargetException, InstantiationException,
                NoSuchMethodException, IllegalAccessException, ParseException,
                ClassNotFoundException {
          String fakerType = Utilities.determineFakerValueTypeFromClass(this.baseClass);
          FakerFieldProps fieldProps =
              PojoDataService.getDefaultFakerFieldProps().withType(fakerType);

          PojoDataService.getRecursiveFieldMap().clear();

          return (T) PojoDataService.createFakeField(fieldProps, this.baseClass, null);
        }
      }

      /**
       * Generate a {@link List} of fake pojo.
       *
       * @param <T> The {@link Class} for the generated data
       */
      public static class ListBuilder<T> extends Builder
          implements io.github.survival1sm.fakerpojo.builders.ListBuilder<T> {

        private final Class<T> baseClass;
        private Integer records = 1;

        public ListBuilder(Class<T> baseClass) {
          this.baseClass = baseClass;
        }

        @Override
        public ListBuilder<T> withRecords(Integer records) {
          this.records = records;
          return this;
        }

        @Override
        public ListBuilder<T> withOverrides(Map<String, Object> overrides) {
          throw new UnsupportedOperationException("Nothing to override when using this builder..");
        }

        @Override
        public ListBuilder<T> withUniqueOnKey(String key) {
          throw new UnsupportedOperationException(
              "This operation doesn't make sense for this builder");
        }

        @Override
        public List<T> build()
            throws NoSuchFieldException, InvocationTargetException, InstantiationException,
                NoSuchMethodException, IllegalAccessException, ParseException,
                ClassNotFoundException {
          List<T> outputList = new ArrayList<>();

          String fakerType = Utilities.determineFakerValueTypeFromClass(this.baseClass);
          FakerFieldProps fieldProps =
              PojoDataService.getDefaultFakerFieldProps().withType(fakerType);

          int i = 0;
          while (i < this.records) {
            final T fakeData =
                (T) PojoDataService.createFakeField(fieldProps, this.baseClass, null);

            outputList.add(fakeData);
            i++;
          }

          PojoDataService.getRecursiveFieldMap().clear();

          return outputList;
        }
      }

      /**
       * Generate a {@link Set} of fake pojo.
       *
       * @param <T> The {@link Class} for the generated data
       */
      public static class SetBuilder<T> extends Builder
          implements io.github.survival1sm.fakerpojo.builders.SetBuilder<T> {

        private final Class<T> baseClass;
        private Integer records = 1;

        public SetBuilder(Class<T> baseClass) {
          this.baseClass = baseClass;
        }

        @Override
        public SetBuilder<T> withOverrides(Map<String, Object> overrides) {
          throw new UnsupportedOperationException("Nothing to override when using this builder..");
        }

        @Override
        public SetBuilder<T> withRecords(Integer records) {
          this.records = records;
          return this;
        }

        @Override
        public SetBuilder<T> withUniqueOnKey(String key) {
          throw new UnsupportedOperationException(
              "This operation doesn't make sense for this builder");
        }

        @Override
        public Set<T> build()
            throws NoSuchFieldException, InvocationTargetException, InstantiationException,
                NoSuchMethodException, IllegalAccessException, ParseException,
                ClassNotFoundException {
          Set<T> outputList = new HashSet<>();

          String fakerType = Utilities.determineFakerValueTypeFromClass(this.baseClass);
          FakerFieldProps fieldProps =
              PojoDataService.getDefaultFakerFieldProps().withType(fakerType);

          int i = 0;
          while (i < this.records) {
            final T fakeData =
                (T) PojoDataService.createFakeField(fieldProps, this.baseClass, null);

            outputList.add(fakeData);
            i++;
          }

          PojoDataService.getRecursiveFieldMap().clear();

          return outputList;
        }
      }
    }

    /**
     * Populate a Plain Old Java Object with fake data using reflection per property to identify its
     * value or using {@link FakerField} annotation to provide custom configuration.
     *
     * @param <T> The {@link Class} for the generated data
     */
    public static class PojoBuilder<T> extends Builder
        implements io.github.survival1sm.fakerpojo.builders.PojoBuilder<T> {

      private final Class<T> baseClass;

      public PojoBuilder(Class<T> baseClass) {
        this.baseClass = baseClass;
      }

      @Override
      public SetBuilder<T> toSet() {
        return new SetBuilder<>(this.baseClass);
      }

      @Override
      public ListBuilder<T> toList() {
        return new ListBuilder<>(this.baseClass);
      }

      @Override
      public DataBuilder<T> getFirst() {
        return new DataBuilder<>(this.baseClass);
      }

      @Override
      public <K> MapBuilder<K, T> toMapOnKey(String key, Class<K> keyClass) {
        return new MapBuilder<>(this.baseClass, key);
      }

      @Override
      public ValueMapListBuilder<T> usingValueMapList(List<Map<String, Object>> valueMapList) {
        return new ValueMapListBuilder<>(this.baseClass, valueMapList);
      }

      /**
       * Generate a {@link Map} of fake pojo.
       *
       * @param <K> The {@link Class} for the map key
       * @param <T> The {@link Class} for the generated data
       */
      public static class MapBuilder<K, T> extends Builder
          implements io.github.survival1sm.fakerpojo.builders.MapBuilder<K, T> {

        private final Class<T> baseClass;
        private final String mapKey;
        private String uniqueOnKey;
        private Map<String, Object> overrides = new HashMap<>();
        private Integer records = 1;

        public MapBuilder(Class<T> baseClass, String mapKey) {
          this.baseClass = baseClass;
          this.mapKey = mapKey;
        }

        @Override
        public MapBuilder<K, T> withOverrides(Map<String, Object> overrides) {
          this.overrides = overrides;
          return this;
        }

        @Override
        public MapBuilder<K, T> withRecords(Integer records) {
          this.records = records;
          return this;
        }

        @Override
        public MapBuilder<K, T> withUniqueOnKey(String key) {
          this.uniqueOnKey = key;
          return this;
        }

        @Override
        public Map<K, T> build()
            throws NoSuchFieldException, InstantiationException, IllegalAccessException,
                OperationNotSupportedException, InvocationTargetException, NoSuchMethodException,
                ParseException, ClassNotFoundException {
          validate();

          final Map<K, T> outputMap = new HashMap<>();

          int i = 0;
          int attempt = 0;
          while (i < this.records) {
            T fakeData = PojoDataService.createFakePojo(this.baseClass, this.overrides);

            final Field fieldFromKey =
                Utilities.getFieldFromClassOrSuperclass(fakeData.getClass(), mapKey);
            fieldFromKey.trySetAccessible();

            if (uniqueOnKey != null) {
              final int hash =
                  Utilities.getHashForUniqueField(fakeData, this.baseClass, uniqueOnKey);

              if (!uniqueOnKeyList.contains(hash)) {
                uniqueOnKeyList.add(hash);

                outputMap.putIfAbsent(
                    (K) fieldFromKey.getType().cast(fieldFromKey.get(fakeData)), fakeData);
                i++;
              } else {
                attempt++;
                if (attempt > 199) {
                  throw new UniqueValueException(
                      UNIQUE_VALUE_EXCEPTION_TEMPLATE.formatted(uniqueOnKey, attempt));
                }
              }
            } else {
              outputMap.putIfAbsent(
                  (K) fieldFromKey.getType().cast(fieldFromKey.get(fakeData)), fakeData);
              i++;
            }
          }

          PojoDataService.getRecursiveFieldMap().clear();

          return outputMap;
        }

        /** Validate input. */
        private void validate() throws OperationNotSupportedException {
          if (this.overrides.containsKey(this.mapKey)) {
            throw new OperationNotSupportedException(
                "You cannot override the map key to be a non-unique value");
          }
        }
      }

      /**
       * Generate a {@link Set} of fake pojo.
       *
       * @param <T> The {@link Class} for the generated data
       */
      public static class SetBuilder<T> extends Builder
          implements io.github.survival1sm.fakerpojo.builders.SetBuilder<T> {

        private final Class<T> baseClass;
        private String uniqueOnKey;
        private Map<String, Object> overrides = new HashMap<>();
        private Integer records = 1;

        public SetBuilder(Class<T> baseClass) {
          this.baseClass = baseClass;
        }

        @Override
        public SetBuilder<T> withOverrides(Map<String, Object> overrides) {
          this.overrides = overrides;
          return this;
        }

        @Override
        public SetBuilder<T> withRecords(Integer records) {
          this.records = records;
          return this;
        }

        @Override
        public SetBuilder<T> withUniqueOnKey(String key) {
          this.uniqueOnKey = key;
          return this;
        }

        @Override
        public Set<T> build()
            throws InstantiationException, InvocationTargetException, NoSuchMethodException,
                IllegalAccessException, NoSuchFieldException, ParseException,
                ClassNotFoundException {
          Set<T> outputList = new HashSet<>();

          int i = 0;
          int attempt = 0;
          while (i < this.records) {
            final T fakeData = PojoDataService.createFakePojo(this.baseClass, this.overrides);

            if (uniqueOnKey != null) {
              final int hash =
                  Utilities.getHashForUniqueField(fakeData, this.baseClass, uniqueOnKey);

              if (!uniqueOnKeyList.contains(hash)) {
                uniqueOnKeyList.add(hash);

                outputList.add(fakeData);
                i++;
              } else {
                attempt++;
                if (attempt > 199) {
                  throw new UniqueValueException(
                      UNIQUE_VALUE_EXCEPTION_TEMPLATE.formatted(uniqueOnKey, attempt));
                }
              }
            } else {
              outputList.add(fakeData);
              i++;
            }
          }

          PojoDataService.getRecursiveFieldMap().clear();

          return outputList;
        }
      }

      /**
       * Use the provided {@link List} of {@link Map} to generate a list of fake pojo with each
       * record of the generated data using the overrides provided from the {@link
       * ValueMapListBuilder#valueMapList}.
       *
       * @param <T> The {@link Class} for the generated data
       */
      public static class ValueMapListBuilder<T> extends Builder
          implements io.github.survival1sm.fakerpojo.builders.ListBuilder<T> {

        private final Class<T> baseClass;
        private final List<Map<String, Object>> valueMapList;
        private String uniqueOnKey;
        private Integer records = 1;

        public ValueMapListBuilder(Class<T> baseClass, List<Map<String, Object>> valueMapList) {
          this.baseClass = baseClass;
          this.valueMapList = valueMapList;
        }

        @Override
        public ValueMapListBuilder<T> withRecords(Integer records) {
          this.records = records;
          return this;
        }

        @Override
        public ValueMapListBuilder<T> withOverrides(Map<String, Object> overrides) {
          throw new UnsupportedOperationException(
              "Overrides are already provided in the valueMapList!");
        }

        @Override
        public ValueMapListBuilder<T> withUniqueOnKey(String key) {
          this.uniqueOnKey = key;
          return this;
        }

        @Override
        public List<T> build()
            throws InstantiationException, InvocationTargetException, NoSuchMethodException,
                IllegalAccessException, NoSuchFieldException, ParseException,
                ClassNotFoundException {
          List<T> outputList = new ArrayList<>();

          int i = 0;
          int attempt = 0;
          while (i < this.valueMapList.size()) {
            final T fakeData =
                PojoDataService.createFakePojo(this.baseClass, this.valueMapList.get(i));

            if (uniqueOnKey != null) {
              final int hash =
                  Utilities.getHashForUniqueField(fakeData, this.baseClass, uniqueOnKey);

              if (!uniqueOnKeyList.contains(hash)) {
                uniqueOnKeyList.add(hash);

                outputList.add(fakeData);
                i++;
              } else {
                attempt++;
                if (attempt > 199) {
                  throw new UniqueValueException(
                      UNIQUE_VALUE_EXCEPTION_TEMPLATE.formatted(uniqueOnKey, attempt));
                }
              }
            } else {
              outputList.add(fakeData);
              i++;
            }
          }

          if (this.records > valueMapList.size()) {
            List<T> additionalData =
                Builder.fromPojo(this.baseClass)
                    .toList()
                    .withRecords(this.records - this.valueMapList.size())
                    .withUniqueOnKey(uniqueOnKey)
                    .build();

            outputList.addAll(additionalData);
          }

          PojoDataService.getRecursiveFieldMap().clear();

          return outputList;
        }
      }

      /**
       * Generate a {@link List} of fake pojo.
       *
       * @param <T> The {@link Class} for the generated data
       */
      public static class ListBuilder<T> extends Builder
          implements io.github.survival1sm.fakerpojo.builders.ListBuilder<T> {

        private final Class<T> baseClass;
        private String uniqueOnKey;
        private Map<String, Object> overrides = new HashMap<>();
        private Integer records = 1;

        public ListBuilder(Class<T> baseClass) {
          this.baseClass = baseClass;
        }

        @Override
        public ListBuilder<T> withRecords(Integer records) {
          this.records = records;
          return this;
        }

        @Override
        public ListBuilder<T> withOverrides(Map<String, Object> overrides) {
          this.overrides = overrides;
          return this;
        }

        @Override
        public ListBuilder<T> withUniqueOnKey(String key) {
          this.uniqueOnKey = key;
          return this;
        }

        @Override
        public List<T> build()
            throws InstantiationException, InvocationTargetException, NoSuchMethodException,
                IllegalAccessException, NoSuchFieldException, ParseException,
                ClassNotFoundException {
          List<T> outputList = new ArrayList<>();

          int i = 0;
          int attempt = 0;
          while (i < this.records) {
            final T fakeData = PojoDataService.createFakePojo(this.baseClass, this.overrides);

            if (uniqueOnKey != null) {
              final int hash =
                  Utilities.getHashForUniqueField(fakeData, this.baseClass, uniqueOnKey);

              if (!uniqueOnKeyList.contains(hash)) {
                uniqueOnKeyList.add(hash);

                outputList.add(fakeData);
                i++;
              } else {
                attempt++;
                if (attempt > 199) {
                  throw new UniqueValueException(
                      UNIQUE_VALUE_EXCEPTION_TEMPLATE.formatted(uniqueOnKey, attempt));
                }
              }
            } else {
              outputList.add(fakeData);
              i++;
            }
          }

          PojoDataService.getRecursiveFieldMap().clear();

          return outputList;
        }
      }

      /**
       * Generate a single instance of a fake pojo.
       *
       * @param <T> The {@link Class} for the generated data
       */
      public static class DataBuilder<T> extends Builder
          implements io.github.survival1sm.fakerpojo.builders.DataBuilder<T> {

        private final Class<T> baseClass;
        private String uniqueOnKey;
        private Map<String, Object> overrides = new HashMap<>();

        protected DataBuilder(Class<T> baseClass) {
          this.baseClass = baseClass;
        }

        public DataBuilder<T> withOverrides(Map<String, Object> overrides) {
          this.overrides = overrides;
          return this;
        }

        public DataBuilder<T> withUniqueOnKey(String key) {
          this.uniqueOnKey = key;
          return this;
        }

        @Override
        public T build()
            throws NoSuchFieldException, InstantiationException, InvocationTargetException,
                NoSuchMethodException, IllegalAccessException, ParseException,
                ClassNotFoundException {
          final T fakeData = PojoDataService.createFakePojo(this.baseClass, this.overrides);

          if (uniqueOnKey != null) {
            final int hash = Utilities.getHashForUniqueField(fakeData, this.baseClass, uniqueOnKey);

            if (!uniqueOnKeyList.contains(hash)) {
              uniqueOnKeyList.add(hash);

              return fakeData;
            } else {
              return this.build(1);
            }
          }

          PojoDataService.getRecursiveFieldMap().clear();

          return fakeData;
        }

        private T build(Integer attempt)
            throws NoSuchFieldException, InstantiationException, InvocationTargetException,
                NoSuchMethodException, IllegalAccessException, ParseException,
                ClassNotFoundException {
          final T fakeData = PojoDataService.createFakePojo(this.baseClass, this.overrides);

          if (uniqueOnKey != null) {
            final int hash = Utilities.getHashForUniqueField(fakeData, this.baseClass, uniqueOnKey);

            if (!uniqueOnKeyList.contains(hash)) {
              uniqueOnKeyList.add(hash);

              return fakeData;
            } else {
              if (attempt > 199) {
                throw new UniqueValueException(
                    UNIQUE_VALUE_EXCEPTION_TEMPLATE.formatted(uniqueOnKey, attempt));
              }
              attempt++;
              return this.build(attempt);
            }
          }

          return fakeData;
        }
      }
    }
  }
}
