package org.wickedsource.coderadar.analyzer.loc;

public class LocContext {

  private boolean withinMultiLineComment;

  public boolean isWithinMultiLineComment() {
    return withinMultiLineComment;
  }

  public void setWithinMultiLineComment(boolean withinMultiLineComment) {
    this.withinMultiLineComment = withinMultiLineComment;
  }
}
