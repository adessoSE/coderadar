package org.wickedsource.coderadar.graph.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wickedsource.coderadar.graph.domain.commit.CommitNode;
import org.wickedsource.coderadar.graph.domain.file.FileId;
import org.wickedsource.coderadar.graph.domain.file.FileNode;
import org.wickedsource.coderadar.graph.domain.filesnapshot.FileSnapshotNode;

/**
 * A {@link CommitGraph} object is a subgraph of the coderadar graph that only contains a {@link
 * CommitNode} with it's dependent nodes ({@link FileNode}s, {@link FileSnapshotNode}s). This
 * subgraph can be modified by calling the append* methods and finally persisted to the graph
 * database by storing the {@link CommitNode} return by {@link #getCommitNode()}.
 */
class CommitGraph {

  private Logger logger = LoggerFactory.getLogger(CommitGraph.class);

  private final CommitNode commitNode;

  private Map<String, FileSnapshotNode> fileSnapshotsInCurrentCommitByPath = new HashMap<>();

  private Map<String, FileNode> filesFromPreviousCommitsByPath = new HashMap<>();

  /**
   * Construcs a new {@link CommitGraph}.
   *
   * @param commitNode the {@link CommitNode} that serves as root for this subgraph. This {@link
   *     CommitNode} may or may not yet be persisted in the database.
   * @param fileSnapshotNodesFromPreviousCommits this set must contain all {@link FileSnapshotNode}s
   *     that were ADDED, MODIFIED or RENAMED but not DELETED in one or more of the commits prior to
   *     the {@link CommitNode} described by the {@code commitNode} parameter. This information is
   *     needed by the append* methods to correctly connect {@link FileNode}s and {@link
   *     FileSnapshotNode}s.
   */
  CommitGraph(CommitNode commitNode, Set<FileSnapshotNode> fileSnapshotNodesFromPreviousCommits) {
    this.commitNode = commitNode;
    for (FileSnapshotNode node : fileSnapshotNodesFromPreviousCommits) {
      this.filesFromPreviousCommitsByPath.put(node.getFilepath(), node.getSnapshotOf());
    }
  }

  /**
   * Returns the {@link CommitNode} that is the root of this subgraph.
   *
   * @return the root of this subgraph.
   */
  CommitNode getCommitNode() {
    return commitNode;
  }

  /**
   * Generates {@link FileSnapshotNode}s and {@link FileNode}s for the list of {@link GitChange}s
   * and appends them to the graph.
   *
   * @param gitChanges the code changes made to a git repository.
   */
  void appendGitChanges(List<GitChange> gitChanges) {
    this.appendFileSnapshotNodes(gitChanges);
    this.appendFileNodes(gitChanges);
  }

  /**
   * Generates {@link FileSnapshotNode}s from the list of {@link GitChange}s and appends them to the
   * graph. A {@link FileSnapshotNode} is created for EVERY {@link GitChange} in the list.
   *
   * @param gitChanges the code changes made to a git repository.
   */
  private void appendFileSnapshotNodes(List<GitChange> gitChanges) {
    for (GitChange change : gitChanges) {
      FileSnapshotNode fileSnapshotNode = new FileSnapshotNode();
      fileSnapshotNode.setFilepath(change.getFilepath());
      fileSnapshotNode.snapshotInCommit(commitNode);
      commitNode.touched(fileSnapshotNode);
      fileSnapshotsInCurrentCommitByPath.put(fileSnapshotNode.getFilepath(), fileSnapshotNode);
    }
  }

  /**
   * Generates {@link FileNode}s from the list of {@link GitChange}s and appends them to the graph.
   * A {@link FileNode} is only created for {@link GitChange}s with {@code changeType} ADDED. For
   * {@code changeType}s MODIFIED, DELETED and RENAMED, the {@link FileNode} ADDED in an earlier
   * commit is used instead of creating a new {@link FileNode} (if it exists) so that each {@link
   * FileNode} describes a concrete file during it's lifetime from being ADDED to being DELETED.
   *
   * @param gitChanges the code changes made to a git repository.
   */
  private void appendFileNodes(List<GitChange> gitChanges) {
    for (GitChange change : gitChanges) {
      FileNode fileNode = null;
      if (change.getChangeType() == GitChange.ChangeType.ADDED) {
        fileNode = new FileNode(FileId.newId());
      } else if (change.getChangeType() == GitChange.ChangeType.MODIFIED) {
        fileNode = this.filesFromPreviousCommitsByPath.get(change.getFilepath());
      } else if (change.getChangeType() == GitChange.ChangeType.DELETED) {
        fileNode = this.filesFromPreviousCommitsByPath.get(change.getFilepath());
      } else if (change.getChangeType() == GitChange.ChangeType.RENAMED) {
        fileNode = this.filesFromPreviousCommitsByPath.get(change.getOldFilepath());
      }
      if (fileNode == null) {
        logger.info(
            "Could not find existing FileNode for path '%s' and commit '%s'...creating a new one!",
            change.getFilepath(), commitNode.getName());
        fileNode = new FileNode(FileId.newId());
      }
      connectFileNode(fileNode, change.getFilepath(), change.getChangeType());
    }
  }

  private void connectFileNode(FileNode fileNode, String path, GitChange.ChangeType changeType) {
    FileSnapshotNode fileSnapshotNode = fileSnapshotsInCurrentCommitByPath.get(path);
    if (fileSnapshotNode == null) {
      throw new IllegalStateException(
          String.format(
              "Could not find FileSnapshotNode with path '%s' for commit '%s'!",
              path, this.commitNode.getName()));
    }
    fileSnapshotNode.snapshotOfFile(fileNode);
    this.commitNode.touched(fileNode);
    switch (changeType) {
      case ADDED:
        fileNode.addedIn(this.commitNode);
        break;
      case MODIFIED:
        fileNode.modifiedIn(this.commitNode);
        break;
      case DELETED:
        fileNode.deletedIn(this.commitNode);
        break;
      case RENAMED:
        fileNode.renamedIn(this.commitNode);
        break;
      default:
        throw new IllegalStateException(String.format("unknown change type: '%s'", changeType));
    }
  }
}
