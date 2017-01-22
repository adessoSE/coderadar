package org.wickedsource.coderadar.analyzer.match;

import org.junit.Assert;
import org.junit.Test;

public class FileMatchingPatternTest {

  @Test
  public void testMatch() {
    FileMatchingPattern pattern = new FileMatchingPattern();
    pattern.addIncludePattern("root/sub/**/*.jsp");
    pattern.addIncludePattern("root/sub/**/*.java");
    pattern.addExcludePattern("root/sub/**/ignored.java");

    Assert.assertTrue(pattern.matches("root/sub/a/b/c/match.jsp"));
    Assert.assertTrue(pattern.matches("root/sub/a/match.jsp"));
    Assert.assertTrue(pattern.matches("root/sub/match.jsp"));
    Assert.assertTrue(pattern.matches("root/sub/a/b/c/match.java"));
    Assert.assertTrue(pattern.matches("root/sub/a/match.java"));
    Assert.assertTrue(pattern.matches("root/sub/match.java"));
    Assert.assertFalse(pattern.matches("root/sub/a/b/c/ignored.java"));
    Assert.assertFalse(pattern.matches("root/sub/a/ignored.java"));
    Assert.assertFalse(pattern.matches("root/sub/ignored.java"));
  }
}
