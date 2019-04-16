package org.wickedsource.coderadar.analyzer.loc;

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
}
