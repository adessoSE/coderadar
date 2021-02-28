package io.reflectoring.coderadar.projectadministration;

import io.reflectoring.coderadar.CoderadarConstants;

public class LongToHashMapper {
  private LongToHashMapper() {}

  public static String longToHash(long longHash) {
    String hash = Long.toHexString(longHash);
    return (CoderadarConstants.ZERO_HASH + hash).substring(hash.length());
  }
}
