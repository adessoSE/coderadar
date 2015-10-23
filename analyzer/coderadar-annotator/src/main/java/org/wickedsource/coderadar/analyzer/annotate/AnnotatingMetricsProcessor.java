package org.wickedsource.coderadar.analyzer.annotate;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.wickedsource.coderadar.analyzer.walk.FileMetricsWithChangeType;

/**
 * Stores a file's metrics as a git note on the file in the git repository.
 */
public class AnnotatingMetricsProcessor implements MetricsProcessor {

    @Override
    public void processMetrics(FileMetricsWithChangeType fileMetrics, Git gitClient, AnyObjectId commitId, AbbreviatedObjectId fileId, String filePath) {

    }
}
