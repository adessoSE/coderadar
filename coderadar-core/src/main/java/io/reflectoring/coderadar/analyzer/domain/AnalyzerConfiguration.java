package io.reflectoring.coderadar.analyzer.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/** An AnalyzerConfiguration stores the configuration for a single analyzer plugin in a project. */
@Data
@NoArgsConstructor
public class AnalyzerConfiguration {
  private long id;
  private String analyzerName;
  private boolean enabled;

  private AnalyzerConfigurationFile analyzerConfigurationFile;

  public AnalyzerConfiguration(long id, String analyzerName, boolean enabled) {
    this.id = id;
    this.analyzerName = analyzerName;
    this.enabled = enabled;
  }
}
