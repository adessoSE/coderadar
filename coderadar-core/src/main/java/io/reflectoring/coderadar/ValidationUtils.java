package io.reflectoring.coderadar;

public class ValidationUtils {

  private ValidationUtils() {}

  public static String validateAndTrimCommitHash(String commitHash) {
    if (commitHash.length() < CoderadarConstants.COMMIT_HASH_LENGTH) {
      throw new IllegalArgumentException(
          String.format(
              "The length of the commit hash must be at least %d",
              CoderadarConstants.COMMIT_HASH_LENGTH));
    }
    return commitHash.substring(0, CoderadarConstants.COMMIT_HASH_LENGTH);
  }
}
