package io.reflectoring.coderadar.plugin.api;

public interface ProgressListener {

    void reportProgress(int totalCount, int doneCount);
}
