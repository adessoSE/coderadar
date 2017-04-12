package org.wickedsource.coderadar.graph.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.graph.domain.commit.CommitName;
import org.wickedsource.coderadar.graph.domain.commit.CommitNode;
import org.wickedsource.coderadar.graph.domain.commit.CommitNodeRepository;

@Service
class CommitNodeFactory {

  private CommitNodeRepository commitNodeRepository;

  @Autowired
  CommitNodeFactory(CommitNodeRepository commitNodeRepository) {
    this.commitNodeRepository = commitNodeRepository;
  }

  /**
   * Creates a CommitNode with the given name. Extracts the CommitNode's parents from the specified
   * list of GitChange objects. This method assumes that the CommitNode's parents already exist in
   * the graph database!
   */
  CommitNode createCommitNode(CommitName commitName, List<GitChange> changes) {
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
