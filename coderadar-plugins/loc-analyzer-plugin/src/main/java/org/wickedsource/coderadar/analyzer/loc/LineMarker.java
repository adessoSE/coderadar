package org.wickedsource.coderadar.analyzer.loc;

public class LineMarker implements Comparable<LineMarker> {

  public enum Type {
    STRING_DELIMITER,
    SINGLE_LINE_COMMENT_START,
    MULTI_LINE_COMMENT_START,
    MULTI_LINE_COMMENT_END;
  }

  private final Integer startIndex;

  private final Type type;

  public LineMarker(Integer startIndex, Type type) {
    this.startIndex = startIndex;
    this.type = type;
  }

  public Integer getStartIndex() {
    return startIndex;
  }

  public Type getType() {
    return type;
  }

  @Override
  public int compareTo(LineMarker o) {
    return this.startIndex.compareTo(o.getStartIndex());
  }
}
