package org.wickedsource.coderadar.analyzer.match;

import org.junit.Assert;
import org.junit.Test;

public class AntPathMatcherTest {

    @Test
    public void matchesAntPaths() {
        AntPathMatcher matcher = new AntPathMatcher();

        Assert.assertTrue(matcher.match("com/t?st.jsp", "com/test.jsp"));
        Assert.assertTrue(matcher.match("com/t?st.jsp", "com/txst.jsp"));
        Assert.assertFalse(matcher.match("com/t?st.jsp", "com/text.jsp"));

        Assert.assertTrue(matcher.match("com/*.jsp", "com/text.jsp"));
        Assert.assertTrue(matcher.match("com/*.jsp", "com/test.jsp"));
        Assert.assertFalse(matcher.match("com/*.jsp", "org/test.jsp"));

        Assert.assertTrue(matcher.match("com/**/test.jsp", "com/test.jsp"));
        Assert.assertTrue(matcher.match("com/**/test.jsp", "com/org/test.jsp"));
        Assert.assertFalse(matcher.match("com/**/test.jsp", "com/text.jsp"));
        Assert.assertFalse(matcher.match("com/**/test.jsp", "com/org/text.jsp"));

        Assert.assertTrue(matcher.match("org/springframework/**/*.jsp", "org/springframework/test.jsp"));
        Assert.assertTrue(matcher.match("org/springframework/**/*.jsp", "org/springframework/package/test.jsp"));
        Assert.assertFalse(matcher.match("org/springframework/**/*.jsp", "org/winterframework/test.jsp"));

        Assert.assertTrue(matcher.match("org/**/servlet/bla.jsp", "org/servlet/bla.jsp"));
        Assert.assertTrue(matcher.match("org/**/servlet/bla.jsp", "org/springframework/servlet/bla.jsp"));
        Assert.assertFalse(matcher.match("org/**/servlet/bla.jsp", "org/springframework/servlet/test.jsp"));

        Assert.assertTrue(matcher.match("**/src/main/java/**/*.java", "diffparser/src/main/java/org/wickedsource/Test.java"));
    }

}