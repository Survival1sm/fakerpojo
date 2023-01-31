package survival1sm.fakerpojo.builders;

import io.leangen.geantyref.AnnotationFormatException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Map;

public interface DataBuilder<T> {


  DataBuilder<T> withOverrides(Map<String, Object> overrides);

  DataBuilder<T> withUniqueOnKey(String key);

  T build()
      throws NoSuchFieldException, IllegalAccessException, ParseException, InvocationTargetException,
      NoSuchMethodException, InstantiationException, ClassNotFoundException, AnnotationFormatException;
}
