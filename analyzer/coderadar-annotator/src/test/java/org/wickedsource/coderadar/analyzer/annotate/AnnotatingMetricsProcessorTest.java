package org.wickedsource.coderadar.analyzer.annotate;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.GitTestTemplate;
import org.wickedsource.coderadar.analyzer.api.Metric;
import org.wickedsource.coderadar.analyzer.walk.FileMetricsWithChangeType;

public class AnnotatingMetricsProcessorTest extends GitTestTemplate{

    @Test
    public void testProcessMetrics() throws Exception {
        add("file1", "content1");
        add("dir1/file2", "content2");
        RevCommit commit = commit();

        FileMetricsWithChangeType metrics1 = new FileMetricsWithChangeType(DiffEntry.ChangeType.ADD);
        metrics1.setMetricValue(new Metric("123"), 5l);

        FileMetricsWithChangeType metrics2 = new FileMetricsWithChangeType(DiffEntry.ChangeType.COPY);
        metrics2.setMetricValue(new Metric("321"), 3l);

        AnnotatingMetricsProcessor processor = new AnnotatingMetricsProcessor();
        processor.processMetrics(metrics1, git, commit.getId(), "file1");
        processor.processMetrics(metrics2, git, commit.getId(), "dir1/file2");

        String note = getNote(commit.getName(), "coderadar");

        // assert that note contains the metrics as JSON
        Assert.assertEquals("{\"metrics\":{\"file1\":{\"changeType\":\"ADD\",\"metricValues\":{\"123\":5}},\"dir1/file2\":{\"changeType\":\"COPY\",\"metricValues\":{\"321\":3}}}}", note);
    }
}