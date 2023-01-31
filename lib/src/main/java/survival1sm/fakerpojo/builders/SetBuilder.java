package survival1sm.fakerpojo.builders;

import io.leangen.geantyref.AnnotationFormatException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Map;
import java.util.Set;

public interface SetBuilder<T> {

  SetBuilder<T> withOverrides(Map<String, Object> overrides);

  SetBuilder<T> withRecords(Integer records);

  SetBuilder<T> withUniqueOnKey(String key);

  Set<T> build()
      throws NoSuchFieldException, IllegalAccessException, ParseException, InvocationTargetException,
      NoSuchMethodException, InstantiationException, ClassNotFoundException, AnnotationFormatException;

}
