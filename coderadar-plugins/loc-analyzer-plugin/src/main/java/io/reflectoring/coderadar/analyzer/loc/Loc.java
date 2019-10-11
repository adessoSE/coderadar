package io.reflectoring.coderadar.analyzer.loc;

import lombok.Data;

@Data
public class Loc {

  /** Lines of code. Total lines of code, including comments and empty lines. */
  private int loc;

  /** Source lines of code. Total lines of code, excluding comments and empty lines. */
  private int sloc;

  /** Comment lines of code. Total number of lines that only contain comments. */
  private int cloc;

  /**
   * Effective lines of code. Total lines of code, excluding comments, empty lines and "header" and
   * "footer" lines (e.g. single braces and import statements).
   */
  private int eloc;

  public void incrementLoc() {
    this.loc++;
  }

  public void incrementSloc() {
    this.sloc++;
  }

  public void incrementCloc() {
    this.cloc++;
  }

  public void incrementEloc() {
    this.eloc++;
  }
}
