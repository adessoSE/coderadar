package org.wickedsource.coderadar.analyzer.api;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Stores metrics to each file of a commit.
 */
public class CommitMetrics {

    private Map<String, FileMetrics> metrics = new HashMap<>();

    public FileMetrics getFileMetrics(String file) {
        return metrics.get(file);
    }

    public void addMetricsToFile(String file, FileMetrics metrics) {
        FileMetrics fileMetrics = this.metrics.get(file);
        if (fileMetrics == null) {
            this.metrics.put(file, metrics);
        } else {
            fileMetrics = this.metrics.get(file);
            fileMetrics.add(metrics);
        }
    }

    public Set<String> getFiles() {
        return metrics.keySet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommitMetrics that = (CommitMetrics) o;

        if (metrics != null ? !metrics.equals(that.metrics) : that.metrics != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return metrics != null ? metrics.hashCode() : 0;
    }
}
