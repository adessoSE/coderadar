package org.wickedsource.coderadar.analyzer.plugin.api;

public interface ProgressListener {

    void reportProgress(int totalCount, int doneCount);

}
