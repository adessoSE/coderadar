package io.reflectoring.coderadar.graph.query.service;

import io.reflectoring.coderadar.graph.analyzer.repository.FileRepository;
import io.reflectoring.coderadar.graph.query.repository.GetCommitsInProjectRepository;
import io.reflectoring.coderadar.query.domain.Changes;
import io.reflectoring.coderadar.query.domain.MetricValueForCommit;
import io.reflectoring.coderadar.query.domain.MetricsTreeNodeType;
import io.reflectoring.coderadar.query.port.driven.GetMetricValuesOfTwoCommitsPort;
import io.reflectoring.coderadar.query.port.driven.MetricTree;
import io.reflectoring.coderadar.query.port.driver.DeltaTree;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForCommitCommand;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForTwoCommitsCommand;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class GetMetricValuesForTwoCommitsAdapter implements GetMetricValuesOfTwoCommitsPort {

  private final GetCommitsInProjectRepository getCommitsInProjectRepository;
  private final FileRepository fileRepository;
  private final GetMetricsForAllFilesInCommitAdapter getMetricsForAllFilesInCommitAdapter;

  public GetMetricValuesForTwoCommitsAdapter(
      GetCommitsInProjectRepository getCommitsInProjectRepository,
      FileRepository fileRepository,
      GetMetricsForAllFilesInCommitAdapter getMetricsForAllFilesInCommitAdapter) {
    this.getCommitsInProjectRepository = getCommitsInProjectRepository;
    this.fileRepository = fileRepository;
    this.getMetricsForAllFilesInCommitAdapter = getMetricsForAllFilesInCommitAdapter;
  }

  @Override
  public DeltaTree get(GetMetricsForTwoCommitsCommand command, Long projectId) {
    MetricTree commit1Tree =
        getMetricsForAllFilesInCommitAdapter.get(
            new GetMetricsForCommitCommand(command.getCommit1(), command.getMetrics()), projectId);
    MetricTree commit2Tree =
        getMetricsForAllFilesInCommitAdapter.get(
            new GetMetricsForCommitCommand(command.getCommit2(), command.getMetrics()), projectId);
    Date commit1Time =
        getCommitsInProjectRepository
            .findByNameAndProjectId(command.getCommit1(), projectId)
            .getTimestamp();
    Date commit2Time =
        getCommitsInProjectRepository
            .findByNameAndProjectId(command.getCommit2(), projectId)
            .getTimestamp();

    if (commit1Time.after(commit2Time)) {
      throw new IllegalArgumentException("commit1 cannot be newer than commit2");
    }

    List<String> addedFiles = new ArrayList<>();
    List<String> removedFiles = new ArrayList<>();

    DeltaTree deltaTree =
        createDeltaTree(
            commit1Tree,
            commit2Tree,
            commit1Time.toInstant().toString(),
            commit2Time.toInstant().toString(),
            projectId,
            addedFiles,
            removedFiles);

    for (String addedFile : addedFiles) {
      String oldPath =
          fileRepository.wasRenamedBetweenCommits(
              addedFile,
              commit1Time.toInstant().toString(),
              commit2Time.toInstant().toString(),
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
    return deltaTree;
  }

  private DeltaTree createDeltaTree(
      MetricTree commit1Tree,
      MetricTree commit2Tree,
      String commit1Time,
      String commit2Time,
      Long projectId,
      List<String> addedFiles,
      List<String> removedFiles) {
    DeltaTree deltaTree = new DeltaTree();
    deltaTree.setName(commit2Tree.getName());
    deltaTree.setType(commit2Tree.getType());
    deltaTree.setCommit1Metrics(commit1Tree.getMetrics());
    deltaTree.setCommit2Metrics(commit2Tree.getMetrics());

    for (int tree1Counter = 0, tree2Counter = 0;
        tree1Counter < commit1Tree.getChildren().size()
            || tree2Counter < commit2Tree.getChildren().size(); ) {
      MetricTree metricTree2 = null;
      MetricTree metricTree1 = null;
      if (tree1Counter < commit1Tree.getChildren().size()) {
        metricTree1 = commit1Tree.getChildren().get(tree1Counter);
      }
      if (tree2Counter < commit2Tree.getChildren().size()) {
        metricTree2 = commit2Tree.getChildren().get(tree2Counter);
      }

      if (metricTree1 != null
          && metricTree2 != null
          && metricTree1.getName().equals(metricTree2.getName())) {
        if (metricTree1.getType().equals(MetricsTreeNodeType.MODULE)
            && metricTree2.getType().equals(MetricsTreeNodeType.MODULE)) {
          deltaTree
              .getChildren()
              .add(
                  createDeltaTree(
                      metricTree1,
                      metricTree2,
                      commit1Time,
                      commit2Time,
                      projectId,
                      addedFiles,
                      removedFiles));
        } else {
          DeltaTree child = new DeltaTree();
          child.setName(metricTree1.getName());
          child.setType(metricTree1.getType());
          child.setCommit1Metrics(metricTree1.getMetrics());
          child.setCommit2Metrics(metricTree2.getMetrics());

          Changes changes = new Changes();
          changes.setModified(
              fileRepository.wasModifiedBetweenCommits(
                  metricTree1.getName(), commit1Time, commit2Time, projectId));
          child.setChanges(changes);
          deltaTree.getChildren().add(child);
        }
        tree1Counter++;
        tree2Counter++;
      } else if (metricTree1 != null
          && (tree2Counter >= commit2Tree.getChildren().size()
              || metricTree1.getName().compareTo(metricTree2.getName()) < 0)) {
        DeltaTree child = new DeltaTree();
        child.setName(metricTree1.getName());
        child.setType(MetricsTreeNodeType.FILE);
        child.setCommit1Metrics(metricTree1.getMetrics());
        child.setCommit2Metrics(new ArrayList<>());
        Changes changes = new Changes();
        changes.setDeleted(true);
        child.setChanges(changes);
        deltaTree.getChildren().add(child);
        removedFiles.add(metricTree1.getName());
        tree1Counter++;
      } else if (metricTree2 != null
          && (tree1Counter >= commit1Tree.getChildren().size()
              || metricTree1.getName().compareTo(metricTree2.getName()) > 0)) {
        DeltaTree child = new DeltaTree();
        child.setName(metricTree2.getName());
        child.setType(MetricsTreeNodeType.FILE);
        child.setCommit1Metrics(new ArrayList<>());
        child.setCommit2Metrics(metricTree2.getMetrics());
        Changes changes = new Changes();
        changes.setAdded(true);
        child.setChanges(changes);
        deltaTree.getChildren().add(child);
        addedFiles.add(metricTree2.getName());
        tree2Counter++;
      }
    }

    return deltaTree;
  }

  private DeltaTree findChildInDeltaTree(DeltaTree deltaTree, String path) {
    for (DeltaTree child : deltaTree.getChildren()) {
      if (child.getType().equals(MetricsTreeNodeType.FILE)) {
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

  private void removeChildFromDeltaTree(DeltaTree deltaTree, DeltaTree childTree) {
    for (DeltaTree child : deltaTree.getChildren()) {
      if (child.getType().equals(MetricsTreeNodeType.FILE)) {
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
