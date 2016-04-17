package org.wickedsource.coderadar.annotator.annotate;

import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.api.ChangeType;
import org.wickedsource.coderadar.analyzer.api.FileMetricsWithChangeType;
import org.wickedsource.coderadar.analyzer.api.Metric;
import org.wickedsource.coderadar.annotator.GitTestTemplate;

public class AnnotatingMetricsProcessorTest extends GitTestTemplate {

    @Test
    public void metricsAreAnnotatedToGitCommit() throws Exception {
        add("file1", "content1");
        add("dir1/file2", "content2");
        RevCommit commit = commit();

        FileMetricsWithChangeType metrics1 = new FileMetricsWithChangeType(ChangeType.ADD);
        metrics1.setMetricCount(new Metric("123"), 5l);

        FileMetricsWithChangeType metrics2 = new FileMetricsWithChangeType(ChangeType.COPY);
        metrics2.setMetricCount(new Metric("321"), 3l);

        AnnotatingMetricsProcessor processor = new AnnotatingMetricsProcessor();
        processor.processMetrics(metrics1, git, commit.getId(), "file1");
        processor.processMetrics(metrics2, git, commit.getId(), "dir1/file2");
        processor.onCommitFinished(git, commit.getId());

        String note = getNote(commit.getName(), NoteUtil.CODERADAR_NAMESPACE);

        // assert that note contains the metrics as JSON
        Assert.assertEquals("{\"metrics\":{\"file1\":{\"changeType\":\"ADD\",\"counts\":{\"123\":5},\"findings\":{}},\"dir1/file2\":{\"changeType\":\"COPY\",\"counts\":{\"321\":3},\"findings\":{}}}}", note);
    }
}