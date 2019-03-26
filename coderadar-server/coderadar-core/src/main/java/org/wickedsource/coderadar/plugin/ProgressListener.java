package org.wickedsource.coderadar.plugin;

public interface ProgressListener {

  void reportProgress(int totalCount, int doneCount);
}
