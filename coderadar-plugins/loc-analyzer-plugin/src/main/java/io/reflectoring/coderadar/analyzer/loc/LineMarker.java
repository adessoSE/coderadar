package io.reflectoring.coderadar.analyzer.loc;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LineMarker implements Comparable<LineMarker> {

  public enum Type {
    STRING_DELIMITER,
    SINGLE_LINE_COMMENT_START,
    MULTI_LINE_COMMENT_START,
    MULTI_LINE_COMMENT_END;
  }

  private final Integer startIndex;

  private final Type type;

  @Override
  public int compareTo(LineMarker o) {
    return this.startIndex.compareTo(o.getStartIndex());
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof LineMarker
        && this.startIndex.equals(((LineMarker) o).startIndex)
        && this.type.equals(((LineMarker) o).type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startIndex, type);
  }
}
