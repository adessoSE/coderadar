package io.reflectoring.coderadar.analyzer.loc.profiles;

import java.util.HashMap;
import java.util.Map;

public class Profiles {

  private Profiles() {}

  private static final Map<String, LocProfile> LOC_PROFILES = new HashMap<>();

  static {
    LOC_PROFILES.put(".java", new JavaLocProfile());
    LOC_PROFILES.put(".xml", new XmlLocProfile());
  }

  public static LocProfile getForFile(String file) {
    int lastDotIndex = file.lastIndexOf('.');

    if (lastDotIndex == -1) {
      return null;
    }

    String fileEnding = file.substring(lastDotIndex);
    return LOC_PROFILES.get(fileEnding);
  }
}
