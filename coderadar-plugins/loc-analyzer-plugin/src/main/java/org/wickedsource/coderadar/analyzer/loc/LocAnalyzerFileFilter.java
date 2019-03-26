package org.wickedsource.coderadar.analyzer.loc;

import org.wickedsource.coderadar.analyzer.loc.profiles.Profiles;
import org.wickedsource.coderadar.plugin.AnalyzerFileFilter;

public class LocAnalyzerFileFilter implements AnalyzerFileFilter {

  @Override
  public boolean acceptFilename(String filename) {
    return Profiles.getForFile(filename) != null;
  }
}
