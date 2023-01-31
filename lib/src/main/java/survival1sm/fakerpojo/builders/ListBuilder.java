package survival1sm.fakerpojo.builders;

import io.leangen.geantyref.AnnotationFormatException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface ListBuilder<T> {

  ListBuilder<T> withOverrides(Map<String, Object> overrides);

  ListBuilder<T> withRecords(Integer records);

  ListBuilder<T> withUniqueOnKey(String key);

  List<T> build()
      throws NoSuchFieldException, IllegalAccessException, ParseException, InvocationTargetException,
      NoSuchMethodException, InstantiationException, ClassNotFoundException, AnnotationFormatException;

}
