package io.reflectoring.coderadar.analyzer.service;

import io.reflectoring.coderadar.plugin.api.AnalyzerFileFilter;
import io.reflectoring.coderadar.plugin.api.FileMetrics;
import io.reflectoring.coderadar.plugin.api.SourceCodeFileAnalyzerPlugin;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/** Combines multiple FileAnalyzerPlugins to calculate metrics for a single file. */
@Service
public class AnalyzeFileService {

  private static final Logger logger = LoggerFactory.getLogger(AnalyzeFileService.class);

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
  FileMetrics analyzeFile(
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
          if (fileContent != null && fileContent.length > 0) {
            fileMetrics.add(analyzerPlugin.analyzeFile(filePath, fileContent));
          }
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
