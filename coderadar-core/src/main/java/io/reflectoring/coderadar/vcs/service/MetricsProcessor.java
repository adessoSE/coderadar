package io.reflectoring.coderadar.vcs.service;

public interface MetricsProcessor {

  /*  */
  /**
   * Takes the metrics calculated for a file by some analyzers and does something with it (like
   * storing it away for later use).
   *
   * @param fileMetrics the metrics to do something with.
   * @param gitClient the git client.
   * @param commitId id of the analyzed file's commit within the git repository.
   * @param filePath path of the analyzed file within the git repository.
   */
  /*
  void processMetrics(
      FileMetricsWithChangeType fileMetrics, Git gitClient, AnyObjectId commitId, String filePath);

  */
  /**
   * This method is called after processMetrics() has been called for all files within a commit. A
   * potential use for this method is to write the data that is collected in processMetrics() into
   * some kind of storage.
   *
   * @param gitClient the git client.
   * @param commitId id of the analyzed file's commit within the git repository.
   */
  /*
  void onCommitFinished(Git gitClient, AnyObjectId commitId);*/
}
