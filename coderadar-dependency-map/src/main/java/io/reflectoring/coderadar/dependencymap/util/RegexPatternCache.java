package io.reflectoring.coderadar.dependencymap.util;

import com.google.re2j.Pattern;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegexPatternCache {

  private final Map<RegexPattern, Pattern> patternMap;

  public RegexPatternCache() {
    patternMap = new HashMap<>();
  }

  public Pattern getPattern(String regex, int flags) {
    RegexPattern regexPattern = new RegexPattern(regex, flags);
    return getOrCreate(regexPattern);
  }

  public Pattern getPattern(String regex) {
    return getPattern(regex, 0);
  }

  public boolean matches(String regex, String string) {
    RegexPattern regexPattern = new RegexPattern(regex, 0);
    Pattern pattern = getOrCreate(regexPattern);
    return pattern.matcher(string).matches();
  }

  private Pattern getOrCreate(RegexPattern regexPattern) {
    if (patternMap.containsKey(regexPattern)) {
      return patternMap.get(regexPattern);
    }
    Pattern newPattern = Pattern.compile(regexPattern.regex, regexPattern.flags);
    patternMap.put(regexPattern, newPattern);
    return newPattern;
  }

  static class RegexPattern {

    String regex;
    int flags;

    public RegexPattern(String regex, int flags) {
      this.regex = regex;
      this.flags = flags;
    }

    public boolean equals(Object obj) {
      if (obj instanceof RegexPattern) {
        RegexPattern other = (RegexPattern) obj;
        return other.regex.equals(regex) && other.flags == flags;
      } else {
        return false;
      }
    }

    public int hashCode() {
      return Objects.hash(regex, flags);
    }
  }
}
