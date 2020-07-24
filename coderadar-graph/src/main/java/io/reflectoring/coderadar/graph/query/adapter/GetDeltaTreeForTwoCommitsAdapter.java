package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.plugin.api.ChangeType;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.query.domain.*;
import io.reflectoring.coderadar.query.port.driven.GetDeltaTreeForTwoCommitsPort;
import io.reflectoring.coderadar.query.port.driver.deltatree.GetDeltaTreeForTwoCommitsCommand;
import io.reflectoring.coderadar.vcs.port.driven.GetRawCommitContentPort;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class GetDeltaTreeForTwoCommitsAdapter implements GetDeltaTreeForTwoCommitsPort {

  private final GetMetricTreeForCommitAdapter getMetricsForAllFilesInCommitAdapter;
  private final ProjectRepository projectRepository;
  private final CommitRepository commitRepository;
  private final GetRawCommitContentPort getRawCommitContentPort;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  public GetDeltaTreeForTwoCommitsAdapter(
      GetMetricTreeForCommitAdapter getMetricsForAllFilesInCommitAdapter,
      ProjectRepository projectRepository,
      CommitRepository commitRepository,
      GetRawCommitContentPort getRawCommitContentPort,
      CoderadarConfigurationProperties coderadarConfigurationProperties) {
    this.getMetricsForAllFilesInCommitAdapter = getMetricsForAllFilesInCommitAdapter;
    this.projectRepository = projectRepository;
    this.commitRepository = commitRepository;
    this.getRawCommitContentPort = getRawCommitContentPort;
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
  }

  @Override
  public DeltaTree get(GetDeltaTreeForTwoCommitsCommand command, long projectId) {
    ProjectEntity projectEntity =
        projectRepository
            .findByIdWithModules(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId));

    if (commitRepository.commitIsNewer(command.getCommit1(), command.getCommit2())) {
      String temp = command.getCommit1();
      command.setCommit1(command.getCommit2());
      command.setCommit2(temp);
    }

    MetricTree commit1Tree =
        getMetricsForAllFilesInCommitAdapter.get(
            projectEntity, command.getCommit1(), command.getMetrics());
    MetricTree commit2Tree =
        getMetricsForAllFilesInCommitAdapter.get(
            projectEntity, command.getCommit2(), command.getMetrics());

    DeltaTree deltaTree = createDeltaTree(commit1Tree, commit2Tree);
    List<Pair<String, String>> renamedFiles =
        getRawCommitContentPort.getRenamesBetweenCommits(
            command.getCommit1(),
            command.getCommit2(),
            coderadarConfigurationProperties.getWorkdir()
                + "/projects/"
                + projectEntity.getWorkdirName());
    processRenames(deltaTree, renamedFiles);
    return deltaTree;
  }

  private void processRenames(DeltaTree deltaTree, List<Pair<String, String>> renamedFiles) {

    for (Pair<String, String> rename : renamedFiles) {
      DeltaTree oldEntry = findChildInDeltaTree(deltaTree, rename.getFirst());
      DeltaTree newEntry = findChildInDeltaTree(deltaTree, rename.getSecond());
      if (oldEntry != null && newEntry != null) {
        newEntry.setCommit1Metrics(oldEntry.getCommit1Metrics());

        newEntry.getChanges().setAdded(false);
        newEntry.getChanges().setRenamed(true);
        newEntry.setRenamedFrom(rename.getFirst());

        oldEntry.getChanges().setRenamed(true);
        oldEntry.getChanges().setDeleted(false);
        oldEntry.setRenamedTo(rename.getSecond());
      }
    }
  }

  /**
   * Create a new delta tree for two commits, given their individual metric tress.
   *
   * @param commit1Tree The metric tree of the first commit.
   * @param commit2Tree The metric tree of the second commit.
   * @return A delta tree, which contains no information about renames. Renames must be processed
   *     separately using the added and removed files lists.
   */
  private DeltaTree createDeltaTree(MetricTree commit1Tree, MetricTree commit2Tree) {
    DeltaTree deltaTree = new DeltaTree();
    deltaTree.setName(commit2Tree.getName());
    deltaTree.setType(commit2Tree.getType());
    deltaTree.setCommit1Metrics(commit1Tree.getMetrics());
    deltaTree.setCommit2Metrics(commit2Tree.getMetrics());
    int tree1Counter = 0;
    int tree2Counter = 0;
    while (tree1Counter < commit1Tree.getChildren().size()
        || tree2Counter < commit2Tree.getChildren().size()) {

      MetricTree metricTree2 =
          tree2Counter < commit2Tree.getChildren().size()
              ? commit2Tree.getChildren().get(tree2Counter)
              : null;

      MetricTree metricTree1 =
          tree1Counter < commit1Tree.getChildren().size()
              ? commit1Tree.getChildren().get(tree1Counter)
              : null;

      if (getChangeType(metricTree1, metricTree2).equals(ChangeType.MODIFY)) {
        if (metricTree1.getType().equals(MetricTreeNodeType.MODULE)
            && metricTree2.getType().equals(MetricTreeNodeType.MODULE)) {
          deltaTree.getChildren().add(createDeltaTree(metricTree1, metricTree2));
        } else {
          deltaTree.getChildren().add(createFileNode(metricTree1, metricTree2));
        }
        tree1Counter++;
        tree2Counter++;
      } else if (getChangeType(metricTree1, metricTree2).equals(ChangeType.DELETE)) {
        deltaTree.getChildren().add(createDeletedFileNode(metricTree1));
        tree1Counter++;
      } else {
        if (metricTree2.getType().equals(MetricTreeNodeType.MODULE) && metricTree1 != null) {
          deltaTree.getChildren().add(createAddedFileNode(metricTree1));
          tree1Counter++;
        } else {
          deltaTree.getChildren().add(createAddedFileNode(metricTree2));
          tree2Counter++;
        }
      }
    }
    return deltaTree;
  }

  private ChangeType getChangeType(MetricTree metricTree1, MetricTree metricTree2) {
    if (metricTree1 != null
        && metricTree2 != null
        && metricTree1.getName().equals(metricTree2.getName())) { // File exists in both trees
      return ChangeType.MODIFY;
    } else if (metricTree1 != null // File deleted in new tree
        && (metricTree2 == null || metricTree1.getName().compareTo(metricTree2.getName()) < 0)) {
      return ChangeType.DELETE;
    } else if (metricTree2 != null // File added in new tree
        && (metricTree1 == null || metricTree2.getName().compareTo(metricTree1.getName()) < 0)) {
      return ChangeType.ADD;
    } else {
      return ChangeType.UNCHANGED;
    }
  }

  private DeltaTree createAddedFileNode(MetricTree metricTree2) {
    DeltaTree child = new DeltaTree();
    child.setName(metricTree2.getName());
    child.setType(MetricTreeNodeType.FILE);
    child.setCommit1Metrics(new ArrayList<>());
    child.setCommit2Metrics(metricTree2.getMetrics());
    Changes changes = new Changes();
    changes.setAdded(true);
    child.setChanges(changes);
    return child;
  }

  private DeltaTree createDeletedFileNode(MetricTree metricTree1) {
    DeltaTree child = new DeltaTree();
    child.setName(metricTree1.getName());
    child.setType(MetricTreeNodeType.FILE);
    child.setCommit1Metrics(metricTree1.getMetrics());
    child.setCommit2Metrics(new ArrayList<>());
    Changes changes = new Changes();
    changes.setDeleted(true);
    child.setChanges(changes);
    return child;
  }

  private DeltaTree createFileNode(MetricTree metricTree1, MetricTree metricTree2) {
    DeltaTree child = new DeltaTree();
    child.setName(metricTree1.getName());
    child.setType(metricTree1.getType());
    child.setCommit1Metrics(metricTree1.getMetrics());
    child.setCommit2Metrics(metricTree2.getMetrics());

    Changes changes = new Changes().setModified(false);
    child.setChanges(changes);

    for (MetricValueForCommit value : metricTree1.getMetrics()) {
      for (MetricValueForCommit value2 : metricTree2.getMetrics()) {
        if (value.getMetricName().equals(value2.getMetricName())
            && value.getValue() != value2.getValue()) {
          changes.setModified(true);
          return child;
        }
      }
    }
    return child;
  }

  /**
   * Finds a child with the given path in a delta tree.
   *
   * @param deltaTree The tree to look in.
   * @param path The child path to look for.
   * @return The tree with the given path or null if nothing is found.
   */
  private DeltaTree findChildInDeltaTree(DeltaTree deltaTree, String path) {
    for (DeltaTree child : deltaTree.getChildren()) {
      if (child.getType().equals(MetricTreeNodeType.FILE)) {
        if (child.getName().equals(path)) {
          return child;
        }
      } else {
        DeltaTree result = findChildInDeltaTree(child, path);
        if (result != null) {
          return result;
        }
      }
    }
    return null;
  }
}
