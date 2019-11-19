package io.reflectoring.coderadar.graph.query.service;

import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.FileRepository;
import io.reflectoring.coderadar.plugin.api.ChangeType;
import io.reflectoring.coderadar.projectadministration.CommitNotFoundException;
import io.reflectoring.coderadar.query.domain.*;
import io.reflectoring.coderadar.query.port.driven.GetDeltaTreeForTwoCommitsPort;
import io.reflectoring.coderadar.query.port.driver.GetDeltaTreeForTwoCommitsCommand;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForCommitCommand;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class GetDeltaTreeForTwoCommitsAdapter implements GetDeltaTreeForTwoCommitsPort {

  private final CommitRepository commitRepository;
  private final FileRepository fileRepository;
  private final GetMetricTreeForCommitAdapter getMetricsForAllFilesInCommitAdapter;

  private static final String PROCESSING_ERROR = "Cannot calculate delta tree!";

  public GetDeltaTreeForTwoCommitsAdapter(
      CommitRepository commitRepository,
      FileRepository fileRepository,
      GetMetricTreeForCommitAdapter getMetricsForAllFilesInCommitAdapter) {
    this.commitRepository = commitRepository;
    this.fileRepository = fileRepository;
    this.getMetricsForAllFilesInCommitAdapter = getMetricsForAllFilesInCommitAdapter;
  }

  @Override
  public DeltaTree get(GetDeltaTreeForTwoCommitsCommand command, Long projectId) {
    MetricTree commit1Tree =
        getMetricsForAllFilesInCommitAdapter.get(
            new GetMetricsForCommitCommand(command.getCommit1(), command.getMetrics()), projectId);
    MetricTree commit2Tree =
        getMetricsForAllFilesInCommitAdapter.get(
            new GetMetricsForCommitCommand(command.getCommit2(), command.getMetrics()), projectId);
    Date commit1Time =
        commitRepository
            .findByNameAndProjectId(command.getCommit1(), projectId)
            .orElseThrow(() -> new CommitNotFoundException(command.getCommit1()))
            .getTimestamp();
    Date commit2Time =
        commitRepository
            .findByNameAndProjectId(command.getCommit2(), projectId)
            .orElseThrow(() -> new CommitNotFoundException(command.getCommit2()))
            .getTimestamp();

    if (commit1Time.after(commit2Time)) {
      MetricTree temp = commit1Tree;
      commit1Tree = commit2Tree;
      commit2Tree = temp;

      Date tempDate = commit1Time;
      commit1Time = commit2Time;
      commit2Time = tempDate;
    }

    List<String> addedFiles = new ArrayList<>();
    List<String> removedFiles = new ArrayList<>();
    List<String> modifiedFiles =
        fileRepository.getFilesModifiedBetweenCommits(
            commit1Time.toInstant().toEpochMilli(),
            commit2Time.toInstant().toEpochMilli(),
            projectId);

    DeltaTree deltaTree =
        createDeltaTree(commit1Tree, commit2Tree, modifiedFiles, addedFiles, removedFiles);

    processRenames(deltaTree, removedFiles, addedFiles, commit1Time, commit2Time, projectId);
    return deltaTree;
  }

  private void processRenames(
      DeltaTree deltaTree,
      List<String> removedFiles,
      List<String> addedFiles,
      Date commit1Time,
      Date commit2Time,
      Long projectId) {
    for (String addedFile : addedFiles) {
      String oldPath =
          fileRepository.findOldpathIfRenamedBetweenCommits(
              addedFile,
              commit1Time.toInstant().toEpochMilli(),
              commit2Time.toInstant().toEpochMilli(),
              projectId);
      if (removedFiles.contains(oldPath)) {
        DeltaTree oldName = findChildInDeltaTree(deltaTree, oldPath);
        DeltaTree newName = findChildInDeltaTree(deltaTree, addedFile);
        if (oldName != null && newName != null) {
          newName.setCommit1Metrics(oldName.getCommit1Metrics());
          newName.getChanges().setAdded(false);
          newName.getChanges().setRenamed(true);
          newName.setRenamedFrom(oldPath);
          newName.setRenamedTo(addedFile);
          removeChildFromDeltaTree(deltaTree, oldName);
        }
      }
    }
  }

  /**
   * Create a new delta tree for two commits, given their individual metric tress.
   *
   * @param commit1Tree The metric tree of the first commit.
   * @param commit2Tree The metric tree of the second commit.
   * @param modifiedFiles A list of file paths that were modified between the two commits
   * @param addedFiles A list, that must be filled with all files added in the newest commit.
   * @param removedFiles A list, that must be filled with all files removed in the newest commit.
   * @return A delta tree, which contains no information about renames. Renames must be processed
   *     separately using the added and removed files lists.
   */
  private DeltaTree createDeltaTree(
      MetricTree commit1Tree,
      MetricTree commit2Tree,
      List<String> modifiedFiles,
      List<String> addedFiles,
      List<String> removedFiles) {
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
        Assert.notNull(metricTree1, PROCESSING_ERROR);
        Assert.notNull(metricTree2, PROCESSING_ERROR);
        if (metricTree1.getType().equals(MetricTreeNodeType.MODULE)
            && metricTree2.getType().equals(MetricTreeNodeType.MODULE)) {
          deltaTree
              .getChildren()
              .add(
                  createDeltaTree(
                      metricTree1, metricTree2, modifiedFiles, addedFiles, removedFiles));
        } else {
          deltaTree.getChildren().add(createFileNode(metricTree1, metricTree2, modifiedFiles));
        }
        tree1Counter++;
        tree2Counter++;
      } else if (getChangeType(metricTree1, metricTree2).equals(ChangeType.DELETE)) {
        Assert.notNull(metricTree1, PROCESSING_ERROR);
        deltaTree.getChildren().add(createDeletedFileNode(metricTree1));
        removedFiles.add(metricTree1.getName());
        tree1Counter++;
      } else {
        Assert.notNull(metricTree2, PROCESSING_ERROR);
        deltaTree.getChildren().add(createAddedFileNode(metricTree2));
        addedFiles.add(metricTree2.getName());
        tree2Counter++;
      }
    }
    return deltaTree;
  }

  private ChangeType getChangeType(MetricTree metricTree1, MetricTree metricTree2) {
    if (metricTree1 != null
        && metricTree2 != null
        && metricTree1.getName().equals(metricTree2.getName())) { // File exists in both trees
      return ChangeType.MODIFY;
    } else if (metricTree1 != null // File Deleted in new tree
        && (metricTree2 == null || metricTree1.getName().compareTo(metricTree2.getName()) < 0)) {
      return ChangeType.DELETE;
    } else {
      return ChangeType.ADD;
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

  private DeltaTree createFileNode(
      MetricTree metricTree1, MetricTree metricTree2, List<String> modifiedFiles) {
    DeltaTree child = new DeltaTree();
    child.setName(metricTree1.getName());
    child.setType(metricTree1.getType());
    child.setCommit1Metrics(metricTree1.getMetrics());
    child.setCommit2Metrics(metricTree2.getMetrics());

    Changes changes = new Changes();
    changes.setModified(modifiedFiles.contains(metricTree1.getName()));
    child.setChanges(changes);
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

  /**
   * Removes a child from a delta tree.
   *
   * @param deltaTree The tree to remove from.
   * @param childTree The child to remove.
   */
  private void removeChildFromDeltaTree(DeltaTree deltaTree, DeltaTree childTree) {
    for (DeltaTree child : deltaTree.getChildren()) {
      if (child.getType().equals(MetricTreeNodeType.FILE)) {
        if (child.getName().equals(childTree.getName())) {
          deltaTree.getChildren().remove(child);
          deltaTree.setCommit1Metrics(aggregateChildMetrics(deltaTree.getChildren()));
          return;
        }
      } else {
        removeChildFromDeltaTree(child, childTree);
      }
    }
  }

  /**
   * Aggregates all of the metrics in the delta tress.
   *
   * @param children The trees whose metrics to aggregate
   * @return A list of aggregated metric values.
   */
  private List<MetricValueForCommit> aggregateChildMetrics(List<DeltaTree> children) {
    List<MetricValueForCommit> resultList = new ArrayList<>();
    Map<String, Long> aggregatedMetrics = new HashMap<>();
    for (DeltaTree deltaTree : children) {
      for (MetricValueForCommit val : aggregateChildMetrics(deltaTree.getChildren())) {
        if (deltaTree
            .getCommit1Metrics()
            .stream()
            .noneMatch(metric -> metric.getMetricName().equals(val.getMetricName()))) {
          deltaTree
              .getCommit1Metrics()
              .add(new MetricValueForCommit(val.getMetricName(), val.getValue()));
        }
      }
      for (MetricValueForCommit value : deltaTree.getCommit1Metrics()) {
        aggregatedMetrics.putIfAbsent(value.getMetricName(), 0L);
        aggregatedMetrics.put(
            value.getMetricName(), aggregatedMetrics.get(value.getMetricName()) + value.getValue());
      }
    }
    for (Map.Entry<String, Long> metric : aggregatedMetrics.entrySet()) {
      resultList.add(new MetricValueForCommit(metric.getKey(), metric.getValue()));
    }
    return resultList;
  }
}
