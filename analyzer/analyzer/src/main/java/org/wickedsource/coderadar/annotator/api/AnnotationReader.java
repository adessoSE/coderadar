package org.wickedsource.coderadar.annotator.api;

import org.wickedsource.coderadar.analyzer.api.CommitMetrics;
import org.wickedsource.coderadar.analyzer.api.FileMetrics;
import org.wickedsource.coderadar.analyzer.api.Metric;

import java.util.Date;
import java.util.List;

/**
 * Interface for reading access to a git repository and the annotations containing metrics created by coderadar analyzers.
 */
public interface AnnotationReader {

    List<Commit> getLatestCommits(int count);

    List<Commit> getLatestCommits(int count, int offset);

    CommitMetrics getMetricsForCommit(String commitId);

    FileMetrics getMetricsForFile(String commitId, String filePath);

    MetricValue getMetricChangeForCommit(Metric metric, String commitId); //TODO: support multiple parents

    List<CommitAssociatedMetricValue> getMetricSeries(Metric metric);

    List<CommitAssociatedMetricValue> getMetricSeries(Metric metric, Date fromDate);

    List<CommitAssociatedMetricValue> getMetricSeries(Metric metric, Date fromDate, Date toDate);

    RepositoryMetrics getRepositoryMetrics();

    RepositoryMetrics getRepositoryMetrics(Date date);

}
