package io.reflectoring.coderadar;

public class CoderadarConstants {

  private CoderadarConstants() {}

  // Must always be 16. This allows for the hash to fit into a long.
  public static final int COMMIT_HASH_LENGTH = 16;

  public static final String ZERO_HASH = "0000000000000000";
}
