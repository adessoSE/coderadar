package org.wickedsource.coderadar.analyzer.walk;

import org.eclipse.jgit.api.Git;
import org.wickedsource.coderadar.analyzer.plugin.api.AnalyzerPlugin;

import java.util.List;

public interface RepositoryWalker {

    /**
     * Walks the given Git repository and calculates some metrics all files in a range of commits. The metrics for each
     * file are passed into the specified MetricsProcessor, who does something with them (like storing them away).
     *
     * @param gitClient              the git client pointed to a git repository.
     * @param analyzers        list of AnalyzerPlugins that calculate the metrics for each file.
     * @param metricsProcessor the processor that receives the calculated metrics.
     */
    public void walk(Git gitClient, List<AnalyzerPlugin> analyzers, MetricsProcessor metricsProcessor);

}
