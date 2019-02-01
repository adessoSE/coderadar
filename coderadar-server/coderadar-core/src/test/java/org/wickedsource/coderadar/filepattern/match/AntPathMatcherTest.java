package org.wickedsource.coderadar.filepattern.match;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AntPathMatcherTest {

  @Test
  public void matchesAntPaths() {
    AntPathMatcher matcher = new AntPathMatcher();

    Assertions.assertTrue(matcher.match("com/t?st.jsp", "com/test.jsp"));
    Assertions.assertTrue(matcher.match("com/t?st.jsp", "com/txst.jsp"));
    Assertions.assertFalse(matcher.match("com/t?st.jsp", "com/text.jsp"));

    Assertions.assertTrue(matcher.match("com/*.jsp", "com/text.jsp"));
    Assertions.assertTrue(matcher.match("com/*.jsp", "com/test.jsp"));
    Assertions.assertFalse(matcher.match("com/*.jsp", "org/test.jsp"));

    Assertions.assertTrue(matcher.match("com/**/test.jsp", "com/test.jsp"));
    Assertions.assertTrue(matcher.match("com/**/test.jsp", "com/org/test.jsp"));
    Assertions.assertFalse(matcher.match("com/**/test.jsp", "com/text.jsp"));
    Assertions.assertFalse(matcher.match("com/**/test.jsp", "com/org/text.jsp"));

    Assertions.assertTrue(
        matcher.match("org/springframework/**/*.jsp", "org/springframework/test.jsp"));
    Assertions.assertTrue(
        matcher.match("org/springframework/**/*.jsp", "org/springframework/package/test.jsp"));
    Assertions.assertFalse(
        matcher.match("org/springframework/**/*.jsp", "org/winterframework/test.jsp"));

    Assertions.assertTrue(matcher.match("org/**/servlet/bla.jsp", "org/servlet/bla.jsp"));
    Assertions.assertTrue(
        matcher.match("org/**/servlet/bla.jsp", "org/springframework/servlet/bla.jsp"));
    Assertions.assertFalse(
        matcher.match("org/**/servlet/bla.jsp", "org/springframework/servlet/test.jsp"));

    Assertions.assertTrue(
        matcher.match(
            "**/src/main/java/**/*.java", "diffparser/src/main/java/org/wickedsource/Test.java"));
  }
}
