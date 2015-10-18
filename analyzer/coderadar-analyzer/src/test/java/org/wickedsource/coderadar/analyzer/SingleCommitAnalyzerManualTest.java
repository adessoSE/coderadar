package org.wickedsource.coderadar.analyzer;

import org.eclipse.jgit.api.Git;
import org.junit.Test;
import org.wickedsource.coderadar.analyzer.plugin.api.AnalyzerPlugin;
import org.wickedsource.coderadar.plugin.loc.LocAnalyzerPlugin;

import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.*;

public class SingleCommitAnalyzerManualTest {

    public void testAnalyzeCodebase() throws Exception {
        Git git = Git.open(new File("D:\\Test\\trunk"));

        AnalyzerPlugin plugin = new LocAnalyzerPlugin();

        SingleCommitAnalyzer analyzer = new SingleCommitAnalyzer(Arrays.asList(plugin), git);
        FileSetMetrics metrics = analyzer.analyzeCodebase("eec4f3ffb233a80f5378c1c7ec9ac1f5feddbc42");
    }
}