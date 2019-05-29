package org.wickedsource.coderadar.analyzer.checkstyle;

import com.puppycrawl.tools.checkstyle.PropertyResolver;

import java.util.Properties;

/**
 * Stores the properties of the CheckstyleAnalyzer (which start with the class name as a namespace)
 * and resolves them into properties that can be accessed by Checkstyle itself. The resolve() method
 * prefixes the given property name with the class name of CheckstyleAnalyzer and reads it from the
 * stored properties.
 */
public class CheckstylePropertiesResolver implements PropertyResolver {

  private Properties backingProperties;

  /**
   * @param backingProperties the properties from which to resolve properties. The names of these
   *     Properties all start with the class name of the CheckstyleAnalyzer.
   */
  public CheckstylePropertiesResolver(Properties backingProperties) {
    this.backingProperties = backingProperties;
  }

  @Override
  public String resolve(String name) {
    return (String)
        backingProperties.get(CheckstyleSourceCodeFileAnalyzerPlugin.class.getName() + "." + name);
  }
}
