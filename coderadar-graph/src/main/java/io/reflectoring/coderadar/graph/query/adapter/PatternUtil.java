package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.data.util.Pair;

/**
 * Code taken from
 * https://github.com/bndtools/bnd/blob/master/aQute.libg/src/aQute/libg/glob/AntGlob.java
 */
public class PatternUtil {

  private PatternUtil() {}

  // match forward slash or back slash (windows)
  private static final String SLASHY = "[\\\\/]";
  private static final String NOT_SLASHY = "[^\\\\/]";

  private static Pattern toPattern(String line) {
    line = line.trim();
    int strLen = line.length();
    StringBuilder sb = new StringBuilder(strLen << 2);
    char previousChar = 0;
    for (int i = 0; i < strLen; i++) {
      char currentChar = line.charAt(i);
      switch (currentChar) {
        case '*':
          int j;
          int k;
          if ((i == 0 || isSlashy(previousChar))
              && //
              ((j = i + 1) < strLen && line.charAt(j) == '*')
              && //
              ((k = j + 1) == strLen || isSlashy(line.charAt(k)))) {
            if (i == 0 && k < strLen) { // line starts with "**/"
              sb.append("(?:.*" + SLASHY + "|)");
              i = k;
            } else if (i > 1) { // after "x/"
              sb.setLength(sb.length() - SLASHY.length());
              sb.append("(?:" + SLASHY + ".*|)");
              i = j;
            } else {
              sb.append(".*");
              i = j;
            }
          } else {
            sb.append(NOT_SLASHY + "*");
          }
          break;
        case '?':
          sb.append(NOT_SLASHY);
          break;
        case '/':
        case '\\':
          if (i + 1 == strLen) {
            // ending with "/" is shorthand for ending with "/**"
            sb.append("(?:" + SLASHY + ".*|)");
          } else {
            sb.append(SLASHY);
          }
          break;
        case '.':
        case '(':
        case ')':
        case '[':
        case ']':
        case '{':
        case '}':
        case '+':
        case '|':
        case '^':
        case '$':
          sb.append('\\');
          // FALL THROUGH
        default:
          sb.append(currentChar);
          break;
      }
      previousChar = currentChar;
    }
    return Pattern.compile(sb.toString(), 0);
  }

  /**
   * Transforms the given FilePatterns to the corresponding regex representation and returns them as
   * a pair.
   *
   * @param filePatterns FilePatterns to transform to regex pattern.
   * @return Pair of regex patterns, first list: includes, second list: excludes
   */
  public static Pair<List<String>, List<String>> mapPatternsToRegex(
      List<FilePattern> filePatterns) {
    List<String> includes = new ArrayList<>();
    List<String> excludes = new ArrayList<>();
    for (FilePattern filePattern : filePatterns) {
      if (filePattern.getInclusionType().equals(InclusionType.INCLUDE)) {
        includes.add(toPattern(filePattern.getPattern()).toString());
      } else {
        excludes.add(toPattern(filePattern.getPattern()).toString());
      }
    }
    return Pair.of(includes, excludes);
  }

  private static boolean isSlashy(char c) {
    return c == '/' || c == '\\';
  }
}
