package io.github.survival1sm.fakerpojo.builders;

/**
 * Build Primitive Wrapper data, ie, {@link Integer}, {@link String}, {@link Float}, etc.
 * @param <T> The {@link java.lang.reflect.Type} of the return data
 */
public interface ClassBuilder<T> {

  /**
   * Return a {@link java.util.Set} of fake data
   */
  SetBuilder<T> toSet();

  /**
   * Return a {@link java.util.List} of fake data
   */
  ListBuilder<T> toList();

  /**
   * Return a single instance of fake data
   */
  DataBuilder<T> getFirst();
}
