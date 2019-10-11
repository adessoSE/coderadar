package io.reflectoring.coderadar.analyzer.loc;

import io.reflectoring.coderadar.analyzer.loc.profiles.Profiles;
import io.reflectoring.coderadar.plugin.api.AnalyzerFileFilter;

public class LocAnalyzerFileFilter implements AnalyzerFileFilter {

  @Override
  public boolean acceptFilename(String filename) {
    return Profiles.getForFile(filename) != null;
  }
}
