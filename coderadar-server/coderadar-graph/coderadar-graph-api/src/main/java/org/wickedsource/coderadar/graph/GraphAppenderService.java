package org.wickedsource.coderadar.graph;

import java.util.List;

public interface GraphAppenderService {

  /**
   * Appends the specified list of changes in a git repository to the coderadar graph database. This
   * method can be called multiple times for the same git repository, however it is expected that
   * the changes passed into this method are in chronological order between two calls.
   *
   * @param changes the changes made in a git repository. The changes are expected to be in
   *     chronological order of the commits.
   */
  void appendGitChanges(List<GitChange> changes);
}
