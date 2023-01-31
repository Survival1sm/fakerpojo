package survival1sm.fakerpojo.builders;

public interface ClassBuilder<T> {

  SetBuilder<T> toSet();

  ListBuilder<T> toList();

  DataBuilder<T> getFirst();
}
