package org.wickedsource.coderadar.job.analyze;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.plugin.AnalyzerFileFilter;
import org.wickedsource.coderadar.plugin.FileMetrics;
import org.wickedsource.coderadar.plugin.SourceCodeFileAnalyzerPlugin;

/** Combines multiple FileAnalyzerPlugins to calculate metrics for a single file. */
@Component
public class FileAnalyzer {

  private Logger logger = LoggerFactory.getLogger(FileAnalyzer.class);

  /**
   * Analyzes a single file. Lets all AnalyzerPlugins run over the given file to calculate their
   * metrics. The aggregated metrics are returned.
   *
   * @param analyzerPlugins the analyzers to calculate metrics for the given file.
   * @param filePath the path of the file to be analyzed (only needed for purpose of meaningful log
   *     messages)
   * @param fileContent the content of the file to be analyzed
   * @return the metrics calculated by all AnalyzerPlugins.
   */
  public FileMetrics analyzeFile(
      List<SourceCodeFileAnalyzerPlugin> analyzerPlugins, String filePath, byte[] fileContent) {
    FileMetrics fileMetrics = new FileMetrics();
    for (SourceCodeFileAnalyzerPlugin analyzerPlugin : analyzerPlugins) {
      if (analyzerPlugin.getFilter() == null) {
        throw new IllegalStateException(
            String.format(
                "Analyzer %s returns an empty AnalyzerFilter! All Analyzers MUST return a valid filter!",
                analyzerPlugin.getClass()));
      }
      if (acceptFile(analyzerPlugin.getFilter(), filePath)) {
        try {
          fileMetrics.add(analyzerPlugin.analyzeFile(filePath, fileContent));
        } catch (Exception e) {
          // catching all exceptions since the plugin is potentially evil
          logger.warn(
              String.format(
                  "Analyzer of class %s threw an exception while analyzing the file %s ... skipping this analyzer.",
                  analyzerPlugin.getClass().getName(), filePath),
              e);
        }
      }
    }
    return fileMetrics;
  }

  private boolean acceptFile(AnalyzerFileFilter filter, String filePath) {
    return filter.acceptFilename(filePath);
  }
}
