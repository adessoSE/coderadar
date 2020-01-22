package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.ModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.graph.query.domain.MetricValueForCommitTreeQueryResult;
import io.reflectoring.coderadar.graph.query.repository.GetMetricValuesOfCommitRepository;
import io.reflectoring.coderadar.projectadministration.CommitNotFoundException;
import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.query.domain.MetricTree;
import io.reflectoring.coderadar.query.domain.MetricTreeNodeType;
import io.reflectoring.coderadar.query.domain.MetricValueForCommit;
import io.reflectoring.coderadar.query.port.driven.GetMetricTreeForCommitPort;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForCommitCommand;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GetMetricTreeForCommitAdapter implements GetMetricTreeForCommitPort {

  private final GetMetricValuesOfCommitRepository getMetricValuesOfCommitRepository;
  private final ProjectRepository projectRepository;
  private final ModuleRepository moduleRepository;
  private final CommitRepository commitRepository;

  public GetMetricTreeForCommitAdapter(
      GetMetricValuesOfCommitRepository getMetricValuesOfCommitRepository,
      ProjectRepository projectRepository,
      ModuleRepository moduleRepository,
      CommitRepository commitRepository) {
    this.getMetricValuesOfCommitRepository = getMetricValuesOfCommitRepository;
    this.projectRepository = projectRepository;
    this.moduleRepository = moduleRepository;
    this.commitRepository = commitRepository;
  }

  @Override
  public MetricTree get(GetMetricsForCommitCommand command, Long projectId) {
    ProjectEntity projectEntity =
        projectRepository
            .findByIdWithModules(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId));

    CommitEntity commitEntity =
        commitRepository
            .findByNameAndProjectId(command.getCommit(), projectId)
            .orElseThrow(() -> new CommitNotFoundException(command.getCommit()));

    if (commitEntity == null) {
      throw new CommitNotFoundException(command.getCommit());
    }

    List<MetricValueForCommitTreeQueryResult> result =
        getMetricValuesOfCommitRepository.getMetricTreeForCommit(
            projectId, command.getMetrics(), commitEntity.getTimestamp());
    List<ModuleEntity> moduleEntities = moduleRepository.findModulesInProjectSortedDesc(projectId);
    List<MetricTree> moduleChildren = processModules(moduleEntities, result);
    MetricTree rootModule = processRootModule(result);
    rootModule.getChildren().addAll(findChildModules(projectEntity.getModules(), moduleChildren));
    rootModule.setMetrics(aggregateChildMetrics(rootModule.getChildren()));
    return rootModule;
  }

  /**
   * Creates a metric tree for each module in the project
   *
   * @param moduleEntities All module entities in the project
   * @param metricValues The calculated metric values for each file in the project.
   * @return A list of metric trees with aggregated metrics for each module.
   */
  private List<MetricTree> processModules(
      List<ModuleEntity> moduleEntities, List<MetricValueForCommitTreeQueryResult> metricValues) {
    List<MetricTree> moduleChildren = new ArrayList<>();
    for (ModuleEntity moduleEntity : moduleEntities) {
      MetricTree metricTree = new MetricTree();
      metricTree.setType(MetricTreeNodeType.MODULE);
      metricTree.setName(moduleEntity.getPath());

      Map<String, Long> aggregatedMetrics = new HashMap<>();
      if (metricValues.isEmpty()) {
        break;
      }
      List<MetricValueForCommitTreeQueryResult> processedFiles = new ArrayList<>();
      for (MetricValueForCommitTreeQueryResult commitTreeQueryResult : metricValues) {
        if (commitTreeQueryResult.getPath().startsWith(moduleEntity.getPath())) {
          MetricTree metricTreeFile = new MetricTree();
          metricTreeFile.setName(commitTreeQueryResult.getPath());
          metricTreeFile.setType(MetricTreeNodeType.FILE);
          for (Map<String, Object> metric : commitTreeQueryResult.getMetrics()) {
            MetricValueForCommit metricValueForCommit =
                new MetricValueForCommit((String) metric.get("name"), (Long) metric.get("value"));
            metricTreeFile.getMetrics().add(metricValueForCommit);
            aggregatedMetrics.putIfAbsent(metricValueForCommit.getMetricName(), 0L);
            aggregatedMetrics.put(
                metricValueForCommit.getMetricName(),
                aggregatedMetrics.get(metricValueForCommit.getMetricName())
                    + metricValueForCommit.getValue());
          }
          metricTreeFile
              .getMetrics()
              .sort(Comparator.comparing(MetricValueForCommit::getMetricName));
          metricTree.getChildren().add(metricTreeFile);
          processedFiles.add(commitTreeQueryResult);
        }
      }
      metricValues.removeAll(processedFiles);
      for (Map.Entry<String, Long> metric : aggregatedMetrics.entrySet()) {
        metricTree.getMetrics().add(new MetricValueForCommit(metric.getKey(), metric.getValue()));
      }
      metricTree.getMetrics().sort(Comparator.comparing(MetricValueForCommit::getMetricName));
      moduleChildren.add(metricTree);
    }
    return moduleChildren;
  }

  /**
   * Processes the metric values contained in the root module and creates a metric tree
   *
   * @param metricValues The metric values that belong to the root module.
   * @return A MetricTree for the root module.
   */
  private MetricTree processRootModule(List<MetricValueForCommitTreeQueryResult> metricValues) {
    MetricTree rootModule = new MetricTree();
    rootModule.setType(MetricTreeNodeType.MODULE);
    rootModule.setName("root");

    for (MetricValueForCommitTreeQueryResult value : metricValues) {
      MetricTree metricTreeFile = new MetricTree();

      metricTreeFile.setName(value.getPath());
      metricTreeFile.setType(MetricTreeNodeType.FILE);

      for (Map<String, Object> metric : value.getMetrics()) {
        MetricValueForCommit metricValueForCommit =
            new MetricValueForCommit((String) metric.get("name"), (Long) metric.get("value"));
        metricTreeFile.getMetrics().add(metricValueForCommit);
      }
      metricTreeFile.getMetrics().sort(Comparator.comparing(MetricValueForCommit::getMetricName));
      rootModule.getChildren().add(metricTreeFile);
    }
    return rootModule;
  }

  /**
   * Aggregates all of the metrics in the given metric trees.
   *
   * @param children The trees to aggregate.
   * @return A list of aggregated metric values.
   */
  private List<MetricValueForCommit> aggregateChildMetrics(List<MetricTree> children) {
    List<MetricValueForCommit> resultList = new ArrayList<>();
    Map<String, Long> aggregatedMetrics = new HashMap<>();
    for (MetricTree metricTree : children) {
      for (MetricValueForCommit val : aggregateChildMetrics(metricTree.getChildren())) {
        if (metricTree.getMetrics().stream()
            .noneMatch(metric -> metric.getMetricName().equals(val.getMetricName()))) {
          metricTree
              .getMetrics()
              .add(new MetricValueForCommit(val.getMetricName(), val.getValue()));
        }
      }
      for (MetricValueForCommit value : metricTree.getMetrics()) {
        aggregatedMetrics.putIfAbsent(value.getMetricName(), 0L);
        aggregatedMetrics.put(
            value.getMetricName(), aggregatedMetrics.get(value.getMetricName()) + value.getValue());
      }
    }
    for (Map.Entry<String, Long> metric : aggregatedMetrics.entrySet()) {
      resultList.add(new MetricValueForCommit(metric.getKey(), metric.getValue()));
    }
    resultList.sort(Comparator.comparing(MetricValueForCommit::getMetricName));
    return resultList;
  }

  /**
   * Correctly find parent and child modules and constructs a valid MetricTree for them.
   *
   * @param moduleEntities The module entities contained in the project.
   * @param metricTrees The metric trees corresponding to the module entities.
   * @return A complete MetricTree
   */
  private List<MetricTree> findChildModules(
      List<ModuleEntity> moduleEntities, List<MetricTree> metricTrees) {
    List<MetricTree> result = new ArrayList<>();
    for (ModuleEntity moduleEntity : moduleEntities) {
      for (MetricTree metricTree : metricTrees) {
        if (metricTree.getName().equals(moduleEntity.getPath())) {
          Long moduleId = moduleEntity.getId();
          moduleEntity =
              moduleRepository
                  .findById(moduleEntity.getId())
                  .orElseThrow(() -> new ModuleNotFoundException(moduleId));
          metricTree
              .getChildren()
              .addAll(findChildModules(moduleEntity.getChildModules(), metricTrees));
          result.add(metricTree);
        }
      }
    }
    return result;
  }
}
