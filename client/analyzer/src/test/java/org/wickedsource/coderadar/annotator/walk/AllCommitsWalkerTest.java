package org.wickedsource.coderadar.annotator.walk;

import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;
import org.mockito.Mockito;
import org.wickedsource.coderadar.analyzer.api.*;
import org.wickedsource.coderadar.annotator.GitTestTemplate;
import org.wickedsource.coderadar.annotator.annotate.MetricsProcessor;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class AllCommitsWalkerTest extends GitTestTemplate {

    @Test
    public void testWalk() throws Exception {
        add("file1.txt", "testFile");
        add("dir1/File2.java", "testJavaFile");
        RevCommit commit = commit();

        // mocking analyzer plugin 1
        final FileMetricsWithChangeType metrics1 = new FileMetricsWithChangeType(ChangeType.ADD);
        metrics1.setMetricCount(new Metric("123"), 5l);
        FileAnalyzerPlugin plugin1 = Mockito.mock(FileAnalyzerPlugin.class);
        when(plugin1.analyzeFile(any(byte[].class))).thenReturn(metrics1);
        when(plugin1.getFilter()).thenReturn(new DefaultFileFilter());

        // mocking analyzer plugin 2
        final FileMetricsWithChangeType metrics2 = new FileMetricsWithChangeType(ChangeType.ADD);
        metrics1.setMetricCount(new Metric("321"), 10l);
        FileAnalyzerPlugin plugin2 = Mockito.mock(FileAnalyzerPlugin.class);
        when(plugin2.analyzeFile(any(byte[].class))).thenReturn(metrics2);
        when(plugin2.getFilter()).thenReturn(new DefaultFileFilter());

        List<FileAnalyzerPlugin> analyzerPlugins = Arrays.asList(plugin1, plugin2);

        // mocking metrics processor
        MetricsProcessor processor = Mockito.mock(MetricsProcessor.class);

        AllCommitsWalker walker = new AllCommitsWalker();
        walker.walk(git, analyzerPlugins, processor);

        // verify that each file is passed into all analyzers
        verify(plugin1).analyzeFile("testFile".getBytes());
        verify(plugin1).analyzeFile("testJavaFile".getBytes());
        verify(plugin2).analyzeFile("testFile".getBytes());
        verify(plugin2).analyzeFile("testJavaFile".getBytes());

        // verify that each analysis result is passed into the MetricsProcessor
        FileMetricsWithChangeType aggregatedMetrics = metrics1;
        aggregatedMetrics.add(metrics2);
        verify(processor).processMetrics(eq(aggregatedMetrics), eq(git), eq(commit.getId()), eq("file1.txt"));
        verify(processor).processMetrics(eq(aggregatedMetrics), eq(git), eq(commit.getId()), eq("dir1/File2.java"));
    }

}