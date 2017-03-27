package org.wickedsource.coderadar.filepattern.match;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.filepattern.domain.FilePattern;
import org.wickedsource.coderadar.filepattern.domain.FileSetType;
import org.wickedsource.coderadar.project.domain.InclusionType;

public class FilePatternMatcherTest {

  @Test
  public void testStringPatterns() {
    FilePatternMatcher matcher = new FilePatternMatcher();
    matcher.addIncludePattern("root/sub/**/*.jsp");
    matcher.addIncludePattern("root/sub/**/*.java");
    matcher.addExcludePattern("root/sub/**/ignored.java");

    assertMatches(matcher);
  }

  @Test
  public void testFilePatternConstructor() {
    List<FilePattern> patterns = new ArrayList<>();

    FilePattern pattern1 = new FilePattern();
    pattern1.setFileSetType(FileSetType.SOURCE);
    pattern1.setInclusionType(InclusionType.INCLUDE);
    pattern1.setPattern("root/sub/**/*.jsp");
    patterns.add(pattern1);

    FilePattern pattern2 = new FilePattern();
    pattern2.setFileSetType(FileSetType.SOURCE);
    pattern2.setInclusionType(InclusionType.INCLUDE);
    pattern2.setPattern("root/sub/**/*.java");
    patterns.add(pattern2);

    FilePattern pattern3 = new FilePattern();
    pattern3.setFileSetType(FileSetType.SOURCE);
    pattern3.setInclusionType(InclusionType.EXCLUDE);
    pattern3.setPattern("root/sub/**/ignored.java");
    patterns.add(pattern3);

    FilePatternMatcher matcher = new FilePatternMatcher(patterns);

    assertMatches(matcher);
  }

  private void assertMatches(FilePatternMatcher matcher) {
    Assert.assertTrue(matcher.matches("root/sub/a/b/c/match.jsp"));
    Assert.assertTrue(matcher.matches("root/sub/a/match.jsp"));
    Assert.assertTrue(matcher.matches("root/sub/match.jsp"));
    Assert.assertTrue(matcher.matches("root/sub/a/b/c/match.java"));
    Assert.assertTrue(matcher.matches("root/sub/a/match.java"));
    Assert.assertTrue(matcher.matches("root/sub/match.java"));
    Assert.assertFalse(matcher.matches("root/sub/a/b/c/ignored.java"));
    Assert.assertFalse(matcher.matches("root/sub/a/ignored.java"));
    Assert.assertFalse(matcher.matches("root/sub/ignored.java"));
  }
}
