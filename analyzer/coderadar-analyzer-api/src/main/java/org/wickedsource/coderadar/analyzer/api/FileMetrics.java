package org.wickedsource.coderadar.analyzer.api;

import java.util.*;

public class FileMetrics {

    private Map<Metric, Long> counts = new HashMap<>();

    private Map<Metric, List<Finding>> findings = new HashMap<>();

    public FileMetrics() {

    }

    /**
     * Copy constructor.
     * @param copyFrom the object whose state to copy into this object.
     */
    public FileMetrics(FileMetrics copyFrom) {
        this.counts = copyFrom.counts;
        this.findings = copyFrom.findings;
    }

    /**
     * Returns all metrics for which a count or findings exist within this FileMetrics object.
     *
     * @return all metrics for which a count or findings exist within this FileMetrics object.
     */
    public Set<Metric> getMetrics() {
        Set<Metric> metrics = new HashSet<>();
        metrics.addAll(counts.keySet());
        metrics.addAll(findings.keySet());
        return metrics;
    }

    /**
     * Returns the count for the given metric.
     *
     * @param metric the metric whose count to return.
     * @return count of the specified metric.
     */
    public Long getMetricCount(Metric metric) {
        return counts.get(metric);
    }

    /**
     * SEts the count for the given metric.
     *
     * @param metric the metric whose count to set.
     * @param count  the value to which to set the count.
     */
    public void setMetricCount(Metric metric, Long count) {
        counts.put(metric, count);
    }

    /**
     * Adds a finding for the specified metric to this FileMetrics object.
     *
     * @param metric  the metric for which to add the finding.
     * @param finding the finding to add.
     */
    public void addFinding(Metric metric, Finding finding) {
        List<Finding> findingsForMetric = findings.get(metric);
        if (findingsForMetric == null) {
            findingsForMetric = new ArrayList<>();
            findings.put(metric, findingsForMetric);
        }
        findingsForMetric.add(finding);
    }

    /**
     * Adds a collection of findings for the specified metric to this FileMetrics object.
     *
     * @param metric        the metric for which to add the findings.
     * @param findingsToAdd the findings to add.
     */
    public void addFindings(Metric metric, Collection<Finding> findingsToAdd) {
        List<Finding> findingsForMetric = findings.get(metric);
        if (findingsForMetric == null) {
            findingsForMetric = new ArrayList<>();
            findings.put(metric, findingsForMetric);
        }
        findingsForMetric.addAll(findingsToAdd);
    }

    /**
     * Returns the findings of the given metric stored in this FileMetrics object.
     *
     * @param metric the metric whose findings to return.
     * @return the findings of the specified metric.
     */
    public List<Finding> getFindings(Metric metric) {
        return findings.get(metric);
    }

    /**
     * Adds the given metrics to the metrics stored in this object.
     *
     * @param metrics the metrics to add to this FileMetrics object
     */
    public void add(FileMetrics metrics) {
        for (Metric metric : metrics.getMetrics()) {
            Long currentValue = counts.get(metric);
            if (currentValue == null) {
                currentValue = 0l;
            }
            counts.put(metric, currentValue + metrics.getMetricCount(metric));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileMetrics that = (FileMetrics) o;

        if (counts != null ? !counts.equals(that.counts) : that.counts != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return counts != null ? counts.hashCode() : 0;
    }
}
