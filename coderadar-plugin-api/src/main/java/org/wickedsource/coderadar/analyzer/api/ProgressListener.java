package org.wickedsource.coderadar.analyzer.api;

public interface ProgressListener {

  void reportProgress(int totalCount, int doneCount);
}
