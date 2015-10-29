package org.wickedsource.coderadar.analyzer.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import org.wickedsource.coderadar.analyzer.api.FileMetrics;
import org.wickedsource.coderadar.analyzer.api.Finding;
import org.wickedsource.coderadar.analyzer.api.Metric;

/**
 * Listener for the Checkstyle checker that receives findings from Checkstyle and converts them into coderadar FileMetrics.
 */
public class CoderadarAuditListener implements AuditListener {

    private FileMetrics metrics = new FileMetrics();

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
        Metric metric = new Metric("checkstyle:" + evt.getSourceName());
        Finding finding = new Finding(evt.getLine(), evt.getLine()); //TODO add column / char position
        metrics.addFinding(metric, finding);
        metrics.incrementMetricCount(metric);
    }

    @Override
    public void addException(AuditEvent evt, Throwable throwable) {
        // do nothing
    }

    protected FileMetrics getMetrics() {
        return this.metrics;
    }
}
