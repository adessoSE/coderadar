package io.reflectoring.coderadar.analyzer.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import io.reflectoring.coderadar.plugin.api.FileMetrics;
import io.reflectoring.coderadar.plugin.api.Finding;
import io.reflectoring.coderadar.plugin.api.Metric;

/**
 * Listener for the Checkstyle checker that receives findings from Checkstyle and converts them into
 * coderadar FileMetrics.
 */
public class CoderadarAuditListener implements AuditListener {

  private FileMetrics metrics = new FileMetrics();

  private MetricCountExtractor metricCountExtractor = new MetricCountExtractor();

  @Override
  public void auditStarted(AuditEvent evt) {
    // do nothing
  }

  @Override
  public void auditFinished(AuditEvent evt) {
    // do nothing
  }

  @Override
  public void fileStarted(AuditEvent evt) {
    // do nothing
  }

  @Override
  public void fileFinished(AuditEvent evt) {
    // do nothing
  }

  @Override
  public void addError(AuditEvent evt) {
    if (evt.getSeverityLevel() != SeverityLevel.IGNORE) {
      Metric metric = new Metric("checkstyle:" + evt.getSourceName());
      Finding finding = new Finding(evt.getLine(), evt.getLine(), evt.getColumn(), evt.getColumn());
      Long metricCount = metricCountExtractor.extractMetricCount(evt);
      metrics.addFinding(metric, finding, metricCount);
    }
  }

  @Override
  public void addException(AuditEvent evt, Throwable throwable) {
    // do nothing
  }

  protected FileMetrics getMetrics() {
    return this.metrics;
  }

  public void reset() {
    metrics = new FileMetrics();
  }
}
