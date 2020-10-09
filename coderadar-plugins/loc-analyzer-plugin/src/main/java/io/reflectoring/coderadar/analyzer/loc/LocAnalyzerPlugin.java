package io.reflectoring.coderadar.analyzer.loc;

import io.reflectoring.coderadar.analyzer.loc.profiles.LocProfile;
import io.reflectoring.coderadar.analyzer.loc.profiles.Profiles;
import io.reflectoring.coderadar.plugin.api.*;
import java.io.IOException;

/** Counts several types of lines of code. */
public class LocAnalyzerPlugin implements SourceCodeFileAnalyzerPlugin {

  private final LocCounter locCounter = new LocCounter();

  @Override
  public AnalyzerFileFilter getFilter() {
    return new LocAnalyzerFileFilter();
  }

  @Override
  public FileMetrics analyzeFile(String filename, byte[] fileContent) {
    try {
      String fileEnding = filename.substring(filename.lastIndexOf('.'));
      LocProfile profile = Profiles.getForFile(filename);
      LinesOfCode loc = locCounter.count(fileContent, profile);
      return toFileMetrics(loc, fileEnding);
    } catch (IOException e) {
      throw new AnalyzerException(e);
    }
  }

  private FileMetrics toFileMetrics(LinesOfCode loc, String fileEnding) {
    String sanitizedFileEnding = fileEnding.replaceAll("\\.", "");
    FileMetrics metrics = new FileMetrics();
    metrics.setMetricCount(new Metric("coderadar:size:loc:" + sanitizedFileEnding), loc.getLoc());
    metrics.setMetricCount(new Metric("coderadar:size:cloc:" + sanitizedFileEnding), loc.getCloc());
    metrics.setMetricCount(new Metric("coderadar:size:sloc:" + sanitizedFileEnding), loc.getSloc());
    metrics.setMetricCount(new Metric("coderadar:size:eloc:" + sanitizedFileEnding), loc.getEloc());
    return metrics;
  }
}
