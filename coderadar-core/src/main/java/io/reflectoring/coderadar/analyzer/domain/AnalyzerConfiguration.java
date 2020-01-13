package io.reflectoring.coderadar.analyzer.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/** An AnalyzerConfiguration stores the configuration for a single analyzer plugin in a project. */
@Data
@NoArgsConstructor
public class AnalyzerConfiguration {
  private Long id;
  private String analyzerName;
  private Boolean enabled;

  private AnalyzerConfigurationFile analyzerConfigurationFile;

  public AnalyzerConfiguration(Long id, String analyzerName, Boolean enabled) {
    this.id = id;
    this.analyzerName = analyzerName;
    this.enabled = enabled;
  }
}
