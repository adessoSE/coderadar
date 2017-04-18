package org.wickedsource.coderadar.graph.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.graph.domain.commit.CommitName;
import org.wickedsource.coderadar.graph.domain.commit.CommitNodeRepository;

/**
 * The {@link GraphAppenderService} provides functionality to add nodes to the coderadar graph
 * database.
 */
@Service
public class GraphAppenderService {

  private final CommitNodeRepository commitNodeRepository;

  private CommitGraphFactory commitGraphFactory;

  @Autowired
  public GraphAppenderService(
      CommitNodeRepository commitNodeRepository, CommitGraphFactory commitGraphFactory) {
    this.commitNodeRepository = commitNodeRepository;
    this.commitGraphFactory = commitGraphFactory;
  }

  /**
   * Appends the specified list of changes in a git repository to the coderadar graph database. This
   * method can be called multiple times for the same git repository, however it is expected that
   * the changes passed into this method are in chronological order between two calls.
   *
   * @param changes the changes made in a git repository. The changes are expected to be in
   *     chronological order of the commits.
   */
  public void appendGitChanges(List<GitChange> changes) {

    Map<CommitName, List<GitChange>> changesPerCommit =
        changes.stream().collect(Collectors.groupingBy(c -> CommitName.from(c.getCommitName())));
    List<CommitName> sortedCommits = sortCommits(changes);

    for (CommitName commitName : sortedCommits) {
      List<GitChange> changesInThisCommit = changesPerCommit.get(commitName);
      CommitGraph commitGraph =
          commitGraphFactory.createCommitGraph(commitName, changesInThisCommit);
      commitGraph.appendGitChanges(changesInThisCommit);
      commitNodeRepository.save(commitGraph.getCommitNode());
    }
  }

  private List<CommitName> sortCommits(List<GitChange> changes) {
    Set<String> processedCommits = new HashSet<>();
    List<CommitName> sortedCommits = new ArrayList<>();
    for (GitChange change : changes) {
      String commitName = change.getCommitName();
      if (!processedCommits.contains(commitName)) {
        sortedCommits.add(CommitName.from(commitName));
        processedCommits.add(commitName);
      }
    }
    return sortedCommits;
  }
}
