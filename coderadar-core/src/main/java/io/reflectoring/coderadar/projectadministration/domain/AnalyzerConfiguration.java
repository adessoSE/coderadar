package io.reflectoring.coderadar.projectadministration.domain;

import lombok.Data;

/** An AnalyzerConfiguration stores the configuration for a single analyzer plugin in a project. */
@Data
public class AnalyzerConfiguration {
  private Long id;
  private String analyzerName;
  private Boolean enabled;

  private AnalyzerConfigurationFile analyzerConfigurationFile;
}
