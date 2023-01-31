package survival1sm.fakerpojo.generators;

import io.leangen.geantyref.AnnotationFormatException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import survival1sm.fakerpojo.domain.FakerFieldProps;

@FunctionalInterface
public interface FieldValueGenerator {

  Object createFakeData(FakerFieldProps fieldProps)
      throws ParseException, NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, ClassNotFoundException, AnnotationFormatException;
}
