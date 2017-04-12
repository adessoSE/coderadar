package org.wickedsource.coderadar.graph.domain;

public abstract class Immutable<T> {

  private final T value;

  protected Immutable(T value) {
    this.value = value;
  }

  public T getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Immutable<?> immutable = (Immutable<?>) o;

    return value != null ? value.equals(immutable.value) : immutable.value == null;
  }

  @Override
  public int hashCode() {
    return value != null ? value.hashCode() : 0;
  }
}
