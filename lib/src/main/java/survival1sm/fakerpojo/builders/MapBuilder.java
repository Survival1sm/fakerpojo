package survival1sm.fakerpojo.builders;

import io.leangen.geantyref.AnnotationFormatException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Map;
import javax.naming.OperationNotSupportedException;

public interface MapBuilder<K, T> {

  MapBuilder<K, T> withOverrides(Map<String, Object> overrides);

  MapBuilder<K, T> withRecords(Integer records);

  MapBuilder<K, T> withUniqueOnKey(String key);

  Map<K, T> build()
      throws NoSuchFieldException, IllegalAccessException, ParseException, InvocationTargetException,
      NoSuchMethodException, InstantiationException, OperationNotSupportedException, ClassNotFoundException, AnnotationFormatException;

}
