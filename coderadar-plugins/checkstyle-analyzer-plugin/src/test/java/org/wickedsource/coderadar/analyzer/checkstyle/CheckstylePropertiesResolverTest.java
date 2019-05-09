package org.wickedsource.coderadar.analyzer.checkstyle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Properties;

public class CheckstylePropertiesResolverTest {

  @Test
  public void existingPropertiesAreResolved() {
    Properties properties = new Properties();
    properties.put(CheckstyleSourceCodeFileAnalyzerPlugin.class.getName() + ".property1", "value1");
    CheckstylePropertiesResolver resolver = new CheckstylePropertiesResolver(properties);
    Assertions.assertEquals("value1", resolver.resolve("property1"));
  }

  @Test
  public void missingPropertiesAreNotResolved() {
    Properties properties = new Properties();
    properties.put(
        CheckstyleSourceCodeFileAnalyzerPlugin.class.getName() + ".existingProperty",
        "existingValue");
    CheckstylePropertiesResolver resolver = new CheckstylePropertiesResolver(properties);
    Assertions.assertNull(resolver.resolve("missingProperty"));
  }
}
