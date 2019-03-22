package org.wickedsource.coderadar.filepattern.match;

import java.util.ArrayList;
import java.util.List;
import org.wickedsource.coderadar.projectadministration.domain.FilePattern;

/**
 * This class allows to specify a set of include patterns and exclude patterns that define a set of
 * files from a given starting folder. The patterns are Ant-style patterns. The exclude patterns are
 * applied AFTER the include patterns so that excludes can be used to override certain includes.
 */
public class FilePatternMatcher {

  private AntPathMatcher matcher = new AntPathMatcher();

  private List<String> includePatterns = new ArrayList<>();

  private List<String> excludePatterns = new ArrayList<>();

  public FilePatternMatcher() {}

  public FilePatternMatcher(List<FilePattern> patterns) {
    addFilePatterns(patterns);
  }

  public void addIncludePattern(String pattern) {
    this.includePatterns.add(pattern);
  }

  public void addExcludePattern(String pattern) {
    this.excludePatterns.add(pattern);
  }

  public void addFilePattern(FilePattern pattern) {
    switch (pattern.getInclusionType()) {
      case INCLUDE:
        addIncludePattern(pattern.getPattern());
        break;
      case EXCLUDE:
        addExcludePattern(pattern.getPattern());
        break;
      default:
        throw new IllegalStateException(
            String.format("invalid InclusionType %s", pattern.getInclusionType()));
    }
  }

  public void addFilePatterns(List<FilePattern> patterns) {
    for (FilePattern pattern : patterns) {
      addFilePattern(pattern);
    }
  }

  /**
   * Determines if the given filePath matches the include and exclude patterns defined in this
   * FileMatchingPattern. The return value is calculated as follows:
   *
   * <ul>
   *   <li>false, if the filepath does not match any include pattern
   *   <li>false, if the filepath matches at least one exclude pattern
   *   <li>true, if the filepath matches at least one include pattern and no exclude pattern
   * </ul>
   *
   * @param filepath the path to the file to match against the pattern
   * @return true if the filepath matches the include and exclude patterns, false otherwise.
   */
  public boolean matches(String filepath) {
    boolean match = false;

    // we have a match if at least one include pattern matches
    for (String includePattern : includePatterns) {
      if (matcher.match(includePattern, filepath)) {
        match = true;
        break;
      }
    }

    // if we have a matching exclude pattern, we don't have a match
    if (match) {
      for (String excludePattern : excludePatterns) {
        if (matcher.match(excludePattern, filepath)) {
          match = false;
          break;
        }
      }
    }

    return match;
  }
}
