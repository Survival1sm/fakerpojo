package io.github.survival1sm.fakerpojo;

import io.github.survival1sm.fakerpojo.annotations.FakerField;
import io.github.survival1sm.fakerpojo.domain.FakerFieldProps;
import io.github.survival1sm.fakerpojo.domain.Type;
import io.github.survival1sm.fakerpojo.exceptions.UniqueValueException;
import io.github.survival1sm.fakerpojo.generators.DefaultValueGenerators;
import io.github.survival1sm.fakerpojo.generators.FieldValueGenerator;
import io.github.survival1sm.fakerpojo.service.FieldValueService;
import io.github.survival1sm.fakerpojo.service.PojoDataService;
import io.github.survival1sm.fakerpojo.util.Utilities;
import io.leangen.geantyref.AnnotationFormatException;
import io.leangen.geantyref.TypeFactory;
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

public class FakerPojo {

  public static void setLocale(Locale locale) {
    PojoDataService.setFaker(new net.datafaker.Faker(locale));
  }

  public static void addFieldValuesGenerator(String type, FieldValueGenerator fieldValuesGenerator) {
    FieldValueService.fieldValuesGeneratorMap.putIfAbsent(type, fieldValuesGenerator);
  }

  public static class Builder {

    public static void resetUniqueOnKeyList() {
      Builder.uniqueOnKeyList = new ArrayList<>();
    }

    private static List<Integer> uniqueOnKeyList = new ArrayList<>();

    public Builder() {
      FieldValueService.fieldValuesGeneratorMap.put(Type.STRING, DefaultValueGenerators.stringGenerator);
      FieldValueService.fieldValuesGeneratorMap.put(Type.BOOLEAN, DefaultValueGenerators.booleanGenerator);
      FieldValueService.fieldValuesGeneratorMap.put(Type.DATE, DefaultValueGenerators.dateGenerator);
      FieldValueService.fieldValuesGeneratorMap.put(Type.LOCALDATE, DefaultValueGenerators.localDateGenerator);
      FieldValueService.fieldValuesGeneratorMap.put(Type.LOCALDATETIME, DefaultValueGenerators.localDateTimeGenerator);
      FieldValueService.fieldValuesGeneratorMap.put(Type.INSTANT, DefaultValueGenerators.instantGenerator);
      FieldValueService.fieldValuesGeneratorMap.put(Type.DURATION, DefaultValueGenerators.durationGenerator);
      FieldValueService.fieldValuesGeneratorMap.put(Type.PARAGRAPH, DefaultValueGenerators.paragraphGenerator);
      FieldValueService.fieldValuesGeneratorMap.put(Type.FULL_NAME, DefaultValueGenerators.fullNameGenerator);
      FieldValueService.fieldValuesGeneratorMap.put(Type.LAST_NAME, DefaultValueGenerators.lastNameGenerator);
      FieldValueService.fieldValuesGeneratorMap.put(Type.FIRST_NAME, DefaultValueGenerators.firstNameGenerator);
      FieldValueService.fieldValuesGeneratorMap.put(Type.URL, DefaultValueGenerators.urlGenerator);
      FieldValueService.fieldValuesGeneratorMap.put(Type.PHONE_NUMBER, DefaultValueGenerators.phoneNumberGenerator);
      FieldValueService.fieldValuesGeneratorMap.put(Type.EMAIL, DefaultValueGenerators.emailGenerator);
      FieldValueService.fieldValuesGeneratorMap.put(Type.UUID, DefaultValueGenerators.uuidGenerator);
      FieldValueService.fieldValuesGeneratorMap.put(Type.DOUBLE, DefaultValueGenerators.doubleGenerator);
      FieldValueService.fieldValuesGeneratorMap.put(Type.FLOAT, DefaultValueGenerators.floatGenerator);
      FieldValueService.fieldValuesGeneratorMap.put(Type.LONG, DefaultValueGenerators.longGenerator);
      FieldValueService.fieldValuesGeneratorMap.put(Type.INTEGER, DefaultValueGenerators.integerGenerator);
      FieldValueService.fieldValuesGeneratorMap.put(Type.CITY, DefaultValueGenerators.cityGenerator);
      FieldValueService.fieldValuesGeneratorMap.put(Type.STATE, DefaultValueGenerators.stateGenerator);
    }

    public static <T> ClassBuilder<T> fromClass(Class<T> baseClass) {
      return new ClassBuilder<>(baseClass);
    }

    public static class ClassBuilder<T> extends Builder implements io.github.survival1sm.fakerpojo.builders.ClassBuilder<T> {

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

      public static class DataBuilder<T> extends Builder implements
              io.github.survival1sm.fakerpojo.builders.DataBuilder<T> {

        private final Class<T> baseClass;

        protected DataBuilder(Class<T> baseClass) {
          this.baseClass = baseClass;
        }

        public DataBuilder<T> withOverrides(Map<String, Object> overrides) {
          throw new UnsupportedOperationException("This would override the single generated fake value");
        }

        public DataBuilder<T> withUniqueOnKey(String key) {
          throw new UnsupportedOperationException("This would ensure the single generated value is unique");
        }

        @Override
        public T build()
            throws NoSuchFieldException, IllegalAccessException, ParseException, InvocationTargetException, NoSuchMethodException, InstantiationException, ClassNotFoundException, AnnotationFormatException {
          String fakerType = Utilities.determineFakerValueTypeFromClass(this.baseClass);
          FakerField classAnnotation = TypeFactory.annotation(FakerField.class, Map.of("type", fakerType));
          FakerFieldProps fieldProps = Utilities.copyAnnotationToFieldProps(classAnnotation);

          return (T) PojoDataService.createFakeField(fieldProps, this.baseClass, null);
        }
      }

      public static class ListBuilder<T> extends Builder implements
              io.github.survival1sm.fakerpojo.builders.ListBuilder<T> {

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
          throw new UnsupportedOperationException("This operation doesn't make sense for this builder");
        }

        @Override
        public List<T> build()
            throws NoSuchFieldException, IllegalAccessException, ParseException, InvocationTargetException, NoSuchMethodException, InstantiationException, ClassNotFoundException, AnnotationFormatException {
          List<T> outputList = new ArrayList<>();

          String fakerType = Utilities.determineFakerValueTypeFromClass(this.baseClass);
          FakerField classAnnotation = TypeFactory.annotation(FakerField.class, Map.of("type", fakerType));
          FakerFieldProps fieldProps = Utilities.copyAnnotationToFieldProps(classAnnotation);

          int i = 0;
          while (i < this.records) {
            final T fakeData = (T) PojoDataService.createFakeField(fieldProps, this.baseClass, null);

            outputList.add(fakeData);
            i++;
          }

          return outputList;
        }
      }

      public static class SetBuilder<T> extends Builder implements
              io.github.survival1sm.fakerpojo.builders.SetBuilder<T> {

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
          throw new UnsupportedOperationException("This operation doesn't make sense for this builder");
        }

        @Override
        public Set<T> build()
            throws NoSuchFieldException, IllegalAccessException, ParseException, InvocationTargetException, NoSuchMethodException, InstantiationException, ClassNotFoundException, AnnotationFormatException {
          Set<T> outputList = new HashSet<>();

          String fakerType = Utilities.determineFakerValueTypeFromClass(this.baseClass);
          FakerField classAnnotation = TypeFactory.annotation(FakerField.class, Map.of("type", fakerType));
          FakerFieldProps fieldProps = Utilities.copyAnnotationToFieldProps(classAnnotation);

          int i = 0;
          while (i < this.records) {
            final T fakeData = (T) PojoDataService.createFakeField(fieldProps, this.baseClass, null);

            outputList.add(fakeData);
            i++;
          }

          return outputList;
        }
      }
    }

    public static <T> PojoBuilder<T> fromPojo(Class<T> baseClass) {
      return new PojoBuilder<>(baseClass);
    }

    public static class PojoBuilder<T> extends Builder implements
            io.github.survival1sm.fakerpojo.builders.PojoBuilder<T> {

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
            throws NoSuchFieldException, IllegalAccessException, ParseException, InvocationTargetException, NoSuchMethodException, InstantiationException, OperationNotSupportedException, ClassNotFoundException, AnnotationFormatException {
          validate();

          final Map<K, T> outputMap = new HashMap<>();

          int i = 0;
          int attempt = 0;
          while (i < this.records) {
            T fakeData = PojoDataService.createFakePojo(this.baseClass, this.overrides);

            final Field fieldFromKey = fakeData.getClass().getDeclaredField(mapKey);
            fieldFromKey.trySetAccessible();

            if (uniqueOnKey != null) {
              final int hash = Utilities.getHashForUniqueField(fakeData, this.baseClass, uniqueOnKey);

              if (!uniqueOnKeyList.contains(hash)) {
                uniqueOnKeyList.add(hash);

                outputMap.putIfAbsent((K) fieldFromKey.getType().cast(fieldFromKey.get(fakeData)), fakeData);
                i++;
              } else {
                attempt++;
                if (attempt > 199) {
                  throw new UniqueValueException(
                      "Unable to generate unique value for key %s after %s attempts".formatted(uniqueOnKey, attempt));
                }
              }
            } else {
              outputMap.putIfAbsent((K) fieldFromKey.getType().cast(fieldFromKey.get(fakeData)), fakeData);
              i++;
            }
          }

          return outputMap;
        }

        /**
         * Validate input
         */
        private void validate() throws OperationNotSupportedException {
          if (this.overrides.containsKey(this.mapKey)) {
            throw new OperationNotSupportedException("You cannot override the map key to be a non-unique value");
          }
        }
      }

      public static class SetBuilder<T> extends Builder implements
              io.github.survival1sm.fakerpojo.builders.SetBuilder<T> {

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
            throws NoSuchFieldException, IllegalAccessException, ParseException, InvocationTargetException, NoSuchMethodException, InstantiationException, ClassNotFoundException, AnnotationFormatException {
          Set<T> outputList = new HashSet<>();

          int i = 0;
          int attempt = 0;
          while (i < this.records) {
            final T fakeData = PojoDataService.createFakePojo(this.baseClass, this.overrides);

            if (uniqueOnKey != null) {
              final int hash = Utilities.getHashForUniqueField(fakeData, this.baseClass, uniqueOnKey);

              if (!uniqueOnKeyList.contains(hash)) {
                uniqueOnKeyList.add(hash);

                outputList.add(fakeData);
                i++;
              } else {
                attempt++;
                if (attempt > 199) {
                  throw new UniqueValueException(
                      "Unable to generate unique value for key %s after %s attempts".formatted(uniqueOnKey, attempt));
                }
              }
            } else {
              outputList.add(fakeData);
              i++;
            }
          }

          return outputList;
        }
      }

      public static class ValueMapListBuilder<T> extends Builder implements
              io.github.survival1sm.fakerpojo.builders.ListBuilder<T> {

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
          throw new UnsupportedOperationException("Overrides are already provided in the valueMapList!");
        }

        @Override
        public ValueMapListBuilder<T> withUniqueOnKey(String key) {
          this.uniqueOnKey = key;
          return this;
        }

        @Override
        public List<T> build()
            throws NoSuchFieldException, IllegalAccessException, ParseException, InvocationTargetException, NoSuchMethodException, InstantiationException, ClassNotFoundException, AnnotationFormatException {
          List<T> outputList = new ArrayList<>();

          int i = 0;
          int attempt = 0;
          while (i < this.valueMapList.size()) {
            final T fakeData = PojoDataService.createFakePojo(this.baseClass, this.valueMapList.get(i));

            if (uniqueOnKey != null) {
              final int hash = Utilities.getHashForUniqueField(fakeData, this.baseClass, uniqueOnKey);

              if (!uniqueOnKeyList.contains(hash)) {
                uniqueOnKeyList.add(hash);

                outputList.add(fakeData);
                i++;
              } else {
                attempt++;
                if (attempt > 199) {
                  throw new UniqueValueException(
                      "Unable to generate unique value for key %s after %s attempts".formatted(uniqueOnKey, attempt));
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

          return outputList;
        }
      }

      public static class ListBuilder<T> extends Builder implements
              io.github.survival1sm.fakerpojo.builders.ListBuilder<T> {

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
            throws NoSuchFieldException, IllegalAccessException, ParseException, InvocationTargetException, NoSuchMethodException, InstantiationException, ClassNotFoundException, AnnotationFormatException {
          List<T> outputList = new ArrayList<>();

          int i = 0;
          int attempt = 0;
          while (i < this.records) {
            final T fakeData = PojoDataService.createFakePojo(this.baseClass, this.overrides);

            if (uniqueOnKey != null) {
              final int hash = Utilities.getHashForUniqueField(fakeData, this.baseClass, uniqueOnKey);

              if (!uniqueOnKeyList.contains(hash)) {
                uniqueOnKeyList.add(hash);

                outputList.add(fakeData);
                i++;
              } else {
                attempt++;
                if (attempt > 199) {
                  throw new UniqueValueException(
                      "Unable to generate unique value for key %s after %s attempts".formatted(uniqueOnKey, attempt));
                }
              }
            } else {
              outputList.add(fakeData);
              i++;
            }
          }

          return outputList;
        }
      }

      public static class DataBuilder<T> extends Builder implements
              io.github.survival1sm.fakerpojo.builders.DataBuilder<T> {

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

        /**
         * Generate an instance of {@link T} with property values backed by {@link net.datafaker.Faker}
         *
         * @return A single {@link T}
         * @throws NoSuchFieldException
         * @throws IllegalAccessException
         * @throws ParseException
         * @throws InvocationTargetException
         * @throws NoSuchMethodException
         * @throws InstantiationException
         * @throws ClassNotFoundException
         * @throws AnnotationFormatException
         */
        @Override
        public T build()
            throws NoSuchFieldException, IllegalAccessException, ParseException, InvocationTargetException, NoSuchMethodException, InstantiationException, ClassNotFoundException, AnnotationFormatException {
          final T fakeData = PojoDataService.createFakePojo(this.baseClass, this.overrides);

          if (uniqueOnKey != null) {
            final Field fieldFromClass = fakeData.getClass().getDeclaredField(uniqueOnKey);
            fieldFromClass.trySetAccessible();

            final int hash = Utilities.getHashForUniqueField(fakeData, this.baseClass, uniqueOnKey);

            if (!uniqueOnKeyList.contains(hash)) {
              uniqueOnKeyList.add(hash);

              return fakeData;
            } else {
              return this.build(1);
            }
          }

          return fakeData;
        }

        private T build(Integer attempt)
            throws NoSuchFieldException, IllegalAccessException, ParseException, InvocationTargetException, NoSuchMethodException, InstantiationException, ClassNotFoundException, AnnotationFormatException {
          final T fakeData = PojoDataService.createFakePojo(this.baseClass, this.overrides);

          if (uniqueOnKey != null) {
            final Field fieldFromClass = fakeData.getClass().getDeclaredField(uniqueOnKey);
            fieldFromClass.trySetAccessible();

            final int hash = Utilities.getHashForUniqueField(fakeData, this.baseClass, uniqueOnKey);

            if (!uniqueOnKeyList.contains(hash)) {
              uniqueOnKeyList.add(hash);

              return fakeData;
            } else {
              if (attempt > 199) {
                throw new UniqueValueException(
                    "Unable to generate unique value for key %s after %s attempts".formatted(uniqueOnKey, attempt));
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
