package org.wickedsource.coderadar.analyzer.walk;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;
import org.mockito.Mockito;
import org.wickedsource.coderadar.analyzer.GitTestTemplate;
import org.wickedsource.coderadar.analyzer.annotate.MetricsProcessor;
import org.wickedsource.coderadar.analyzer.plugin.api.AnalyzerPlugin;
import org.wickedsource.coderadar.analyzer.plugin.api.DefaultFilter;
import org.wickedsource.coderadar.analyzer.plugin.api.Metric;

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
        final FileMetricsWithChangeType metrics1 = new FileMetricsWithChangeType(DiffEntry.ChangeType.ADD);
        metrics1.setMetricValue(new Metric("123"), 5l);
        AnalyzerPlugin plugin1 = Mockito.mock(AnalyzerPlugin.class);
        when(plugin1.analyzeFile(any(byte[].class))).thenReturn(metrics1);
        when(plugin1.getFilter()).thenReturn(new DefaultFilter());

        // mocking analyzer plugin 2
        final FileMetricsWithChangeType metrics2 = new FileMetricsWithChangeType(DiffEntry.ChangeType.ADD);
        metrics1.setMetricValue(new Metric("321"), 10l);
        AnalyzerPlugin plugin2 = Mockito.mock(AnalyzerPlugin.class);
        when(plugin2.analyzeFile(any(byte[].class))).thenReturn(metrics2);
        when(plugin2.getFilter()).thenReturn(new DefaultFilter());

        List<AnalyzerPlugin> analyzers = Arrays.asList(plugin1, plugin2);

        // mocking metrics processor
        MetricsProcessor processor = Mockito.mock(MetricsProcessor.class);

        AllCommitsWalker walker = new AllCommitsWalker();
        walker.walk(git, analyzers, processor);

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