package org.wickedsource.coderadar.annotator.api;

import org.wickedsource.coderadar.analyzer.api.CommitMetrics;

public interface AnnotationWriter {

    void annotateCommit(String commitId, CommitMetrics commitMetrics);

}
