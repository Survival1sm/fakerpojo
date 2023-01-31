package survival1sm.fakerpojo.builders;

import java.util.List;
import java.util.Map;

public interface PojoBuilder<T> {

  SetBuilder<T> toSet();

  ListBuilder<T> toList();

  ListBuilder<T> usingValueMapList(List<Map<String, Object>> valueMapList);

  DataBuilder<T> getFirst();

  <K> MapBuilder<K, T> toMapOnKey(String key, Class<K> keyClass);
}
