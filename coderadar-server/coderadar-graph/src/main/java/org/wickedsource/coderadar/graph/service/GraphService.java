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
import org.wickedsource.coderadar.graph.domain.commit.CommitNode;
import org.wickedsource.coderadar.graph.domain.commit.CommitNodeRepository;

@Service
public class GraphService {

  private final CommitNodeRepository commitNodeRepository;

  private CommitNodeFactory commitNodeFactory;

  private FileSnapshotNodeFactory fileSnapshotNodeFactory;

  @Autowired
  public GraphService(
      CommitNodeRepository commitNodeRepository,
      CommitNodeFactory commitNodeFactory,
      FileSnapshotNodeFactory fileSnapshotNodeFactory) {
    this.commitNodeRepository = commitNodeRepository;
    this.commitNodeFactory = commitNodeFactory;
    this.fileSnapshotNodeFactory = fileSnapshotNodeFactory;
  }

  /** @param changes the changes are expected to be in chronological order of the commits. */
  public void addGitChanges(List<GitChange> changes) {

    Map<CommitName, List<GitChange>> changesPerCommit =
        changes.stream().collect(Collectors.groupingBy(c -> CommitName.from(c.getCommitName())));
    List<CommitName> sortedCommits = sortCommits(changes);

    for (CommitName commitName : sortedCommits) {
      List<GitChange> changesForCommit = changesPerCommit.get(commitName);
      CommitNode commitNode = commitNodeFactory.createCommitNode(commitName, changesForCommit);
      fileSnapshotNodeFactory.appendFileSnapshotNodes(commitNode, changesForCommit);

      // saving commit node and all attached nodes to graph
      commitNodeRepository.save(commitNode);

      // TODO: FileNodes
      // ADDED / COPIED: 	new FileNode
      // MODIFIED: 		existing FileNode
      // DELETED: 		existing FileNode
      // RENAMED:			existing FileNode (with different file name)

    }
  }

  private void addFileNodes(CommitNode commitNode, List<GitChange> changes) {}

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
