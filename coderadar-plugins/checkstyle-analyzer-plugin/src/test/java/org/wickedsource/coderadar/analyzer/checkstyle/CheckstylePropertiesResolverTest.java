package org.wickedsource.coderadar.analyzer.checkstyle;

import java.util.Properties;
import org.junit.Assert;
import org.junit.Test;

public class CheckstylePropertiesResolverTest {

  @Test
  public void existingPropertiesAreResolved() {
    Properties properties = new Properties();
    properties.put(CheckstyleSourceCodeFileAnalyzerPlugin.class.getName() + ".property1", "value1");
    CheckstylePropertiesResolver resolver = new CheckstylePropertiesResolver(properties);
    Assert.assertEquals("value1", resolver.resolve("property1"));
  }

  @Test
  public void missingPropertiesAreNotResolved() {
    Properties properties = new Properties();
    properties.put(
        CheckstyleSourceCodeFileAnalyzerPlugin.class.getName() + ".existingProperty",
        "existingValue");
    CheckstylePropertiesResolver resolver = new CheckstylePropertiesResolver(properties);
    Assert.assertNull(resolver.resolve("missingProperty"));
  }
}
