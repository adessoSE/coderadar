package org.wickedsource.coderadar.analyzer.walk;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.AnyObjectId;
import org.wickedsource.coderadar.analyzer.plugin.api.FileMetrics;

/**
 * Stores a file's metrics as a git note on the file in the git repository.
 */
public class AnnotatingMetricsProcessor implements MetricsProcessor {

    @Override
    public void processMetrics(FileMetrics fileMetrics, Git gitClient, AnyObjectId commitId, AnyObjectId fileId, String filePath) {

    }
}
