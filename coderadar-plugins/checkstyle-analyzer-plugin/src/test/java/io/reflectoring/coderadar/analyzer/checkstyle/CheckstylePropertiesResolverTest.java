package io.reflectoring.coderadar.analyzer.checkstyle;

import java.util.Properties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CheckstylePropertiesResolverTest {

  @Test
  void existingPropertiesAreResolved() {
    Properties properties = new Properties();
    properties.put(CheckstyleSourceCodeFileAnalyzerPlugin.class.getName() + ".property1", "value1");
    CheckstylePropertiesResolver resolver = new CheckstylePropertiesResolver(properties);
    Assertions.assertEquals("value1", resolver.resolve("property1"));
  }

  @Test
  void missingPropertiesAreNotResolved() {
    Properties properties = new Properties();
    properties.put(
        CheckstyleSourceCodeFileAnalyzerPlugin.class.getName() + ".existingProperty",
        "existingValue");
    CheckstylePropertiesResolver resolver = new CheckstylePropertiesResolver(properties);
    Assertions.assertNull(resolver.resolve("missingProperty"));
  }
}
