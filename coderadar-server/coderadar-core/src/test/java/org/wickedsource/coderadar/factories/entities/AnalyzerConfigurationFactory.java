package org.wickedsource.coderadar.factories.entities;

import org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfiguration;
import org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfigurationFile;

public class AnalyzerConfigurationFactory {

  public AnalyzerConfiguration analyzerConfiguration() {
    AnalyzerConfiguration configuration = new AnalyzerConfiguration();
    configuration.setAnalyzerName("org.wickedsource.locAnalyzer");
    configuration.setEnabled(Boolean.TRUE);
    configuration.setId(1L);
    return configuration;
  }

  public AnalyzerConfiguration analyzerConfiguration2() {
    AnalyzerConfiguration configuration = new AnalyzerConfiguration();
    configuration.setAnalyzerName("org.wickedsource.fooAnalyzer");
    configuration.setEnabled(Boolean.FALSE);
    configuration.setId(2L);
    return configuration;
  }

  public AnalyzerConfigurationFile analyzerConfigurationFile() {
    AnalyzerConfigurationFile file = new AnalyzerConfigurationFile();
    file.setFileData("<config><param1>value1</param1></config>".getBytes());
    file.setFileName("testconfig.xml");
    file.setAnalyzerConfiguration(analyzerConfiguration());
    file.setContentType("text/xml");
    return file;
  }
}
