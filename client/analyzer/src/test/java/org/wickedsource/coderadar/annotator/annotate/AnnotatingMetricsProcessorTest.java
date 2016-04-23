package org.wickedsource.coderadar.annotator.annotate;

import com.google.gson.Gson;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.api.ChangeType;
import org.wickedsource.coderadar.analyzer.api.CommitMetrics;
import org.wickedsource.coderadar.analyzer.api.FileMetricsWithChangeType;
import org.wickedsource.coderadar.analyzer.api.Metric;
import org.wickedsource.coderadar.annotator.GitTestTemplate;
import org.wickedsource.coderadar.annotator.serialize.GsonFactory;

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

        Gson gson = GsonFactory.getInstance().createGson();
        CommitMetrics metrics = gson.fromJson(note, CommitMetrics.class);
        Assert.assertTrue(metrics.getFiles().contains("file1"));
        Assert.assertEquals(Long.valueOf(5), metrics.getFileMetrics("file1").getMetricCount(new Metric("123")));

        Assert.assertTrue(metrics.getFiles().contains("dir1/file2"));
        Assert.assertEquals(Long.valueOf(3), metrics.getFileMetrics("dir1/file2").getMetricCount(new Metric("321")));
    }
}