package org.wickedsource.coderadar.graph.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.graph.domain.commit.CommitName;
import org.wickedsource.coderadar.graph.domain.commit.CommitNode;
import org.wickedsource.coderadar.graph.domain.commit.CommitNodeRepository;
import org.wickedsource.coderadar.graph.domain.filesnapshot.FileSnapshotNode;
import org.wickedsource.coderadar.graph.domain.filesnapshot.FileSnapshotNodeRepository;

/** Allows creation of new {@link CommitGraph}s. */
@Component
class CommitGraphFactory {

  private CommitNodeRepository commitNodeRepository;

  private FileSnapshotNodeRepository fileSnapshotNodeRepository;

  @Autowired
  CommitGraphFactory(
      CommitNodeRepository commitNodeRepository,
      FileSnapshotNodeRepository fileSnapshotNodeRepository) {
    this.commitNodeRepository = commitNodeRepository;
    this.fileSnapshotNodeRepository = fileSnapshotNodeRepository;
  }

  CommitGraph createCommitGraph(CommitName commitName, List<GitChange> changesInThisCommit) {
    CommitNode commitNode = createOrLoadCommitNode(commitName, changesInThisCommit);
    Set<FileSnapshotNode> fileSnapshotsFromPreviousCommits =
        fileSnapshotNodeRepository.notDeletedInPreviousCommits(commitName.getValue());
    CommitGraph commitGraph = new CommitGraph(commitNode, fileSnapshotsFromPreviousCommits);
    return commitGraph;
  }

  /**
   * Creates a {@link CommitNode} with the given name. Extracts the {@link CommitNode}'s parents
   * from the specified list of {@link GitChange} objects. This method assumes that the {@link
   * CommitNode}'s parents already exist in the graph database! If a {@link CommitNode} with the
   * specified name already exists within the graph database, this node is returned.
   *
   * @param commitName the name of the commit for which to create a {@link CommitNode} in the
   *     database
   * @param changes the list of {@link GitChange}s from which to discern the commit's parents
   */
  CommitNode createOrLoadCommitNode(CommitName commitName, List<GitChange> changes) {
    CommitNode existingCommitNode = commitNodeRepository.findByName(commitName);
    if (existingCommitNode != null) {
      return existingCommitNode;
    }

    Set<CommitNode> parentNodes = extractParentCommitNodes(changes);

    CommitNode commitNode = new CommitNode();
    commitNode.setName(commitName);
    if (!isEmpty(parentNodes)) {
      commitNode.setParents(parentNodes);
    }
    commitNode.setTimestamp(changes.get(0).getTimestamp());
    commitNodeRepository.save(commitNode);
    return commitNode;
  }

  Set<CommitNode> extractParentCommitNodes(List<GitChange> changes) {
    // we assume the parent commits already exist in the graph
    Set<String> parentCommitNames =
        changes.stream().map(GitChange::getParentCommitName).collect(Collectors.toSet());
    if (parentCommitNames.size() == 1 && parentCommitNames.iterator().next() == null) {
      parentCommitNames = new HashSet<>();
    }
    Set<CommitNode> parentNodes = commitNodeRepository.findByNameIn(parentCommitNames);

    if (parentCommitNames.size() != parentNodes.size()) {
      Set<String> foundCommitNames =
          parentNodes.stream().map(c -> c.getName().getValue()).collect(Collectors.toSet());
      throw new IllegalStateException(
          String.format(
              "Did not find expected CommitNode parents in database. Expected CommitNodes: %s. Found CommitNodes: %s",
              parentCommitNames, foundCommitNames));
    }

    return parentNodes;
  }

  private <T> boolean isEmpty(Collection<T> list) {
    for (T element : list) {
      if (element != null) {
        return false;
      }
    }
    return true;
  }
}
