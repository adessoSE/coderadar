package org.wickedsource.coderadar.annotator.api;

import org.wickedsource.coderadar.analyzer.api.FileSetMetrics;

public interface AnnotationWriter {

    void annotateCommit(String commitId, FileSetMetrics fileSetMetrics);

}
