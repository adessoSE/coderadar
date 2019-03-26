package org.wickedsource.coderadar.analyzer.loc;

import java.io.IOException;
import org.wickedsource.coderadar.analyzer.loc.profiles.LocProfile;
import org.wickedsource.coderadar.analyzer.loc.profiles.Profiles;
import org.wickedsource.coderadar.plugin.*;

/** Counts several types of lines of code. */
public class LocAnalyzerPlugin implements SourceCodeFileAnalyzerPlugin {

  private LocCounter locCounter = new LocCounter();

  @Override
  public AnalyzerFileFilter getFilter() {
    return new LocAnalyzerFileFilter();
  }

  @Override
  public FileMetrics analyzeFile(String filename, byte[] fileContent) throws AnalyzerException {
    try {
      String fileEnding = filename.substring(filename.lastIndexOf('.'));
      LocProfile profile = Profiles.getForFile(filename);
      Loc loc = locCounter.count(fileContent, profile);
      return toFileMetrics(loc, fileEnding);
    } catch (IOException e) {
      throw new AnalyzerException(e);
    }
  }

  private FileMetrics toFileMetrics(Loc loc, String fileEnding) {
    String sanitizedFileEnding = fileEnding.replaceAll("\\.", "");
    FileMetrics metrics = new FileMetrics();
    metrics.setMetricCount(
        new Metric("coderadar:size:loc:" + sanitizedFileEnding), (long) loc.getLoc());
    metrics.setMetricCount(
        new Metric("coderadar:size:cloc:" + sanitizedFileEnding), (long) loc.getCloc());
    metrics.setMetricCount(
        new Metric("coderadar:size:sloc:" + sanitizedFileEnding), (long) loc.getSloc());
    metrics.setMetricCount(
        new Metric("coderadar:size:eloc:" + sanitizedFileEnding), (long) loc.getEloc());
    return metrics;
  }
}
