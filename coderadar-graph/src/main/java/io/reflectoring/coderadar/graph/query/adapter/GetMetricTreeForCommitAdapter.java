package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.ValidationUtils;
import io.reflectoring.coderadar.analyzer.domain.MetricName;
import io.reflectoring.coderadar.analyzer.domain.MetricNameMapper;
import io.reflectoring.coderadar.domain.MetricTree;
import io.reflectoring.coderadar.domain.MetricTreeNodeType;
import io.reflectoring.coderadar.domain.MetricValueForCommit;
import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.graph.query.repository.MetricQueryRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.query.port.driven.GetMetricTreeForCommitPort;
import io.reflectoring.coderadar.query.port.driver.metrictree.GetMetricTreeForCommitCommand;
import java.util.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetMetricTreeForCommitAdapter implements GetMetricTreeForCommitPort {

  private final MetricQueryRepository metricQueryRepository;
  private final ProjectRepository projectRepository;

  MetricTree get(ProjectEntity project, long commitHash, List<String> metrics) {
    List<Map<String, Object>> result =
        metricQueryRepository.getMetricTreeForCommit(
            project.getId(),
            commitHash,
            metrics.stream().mapToInt(MetricNameMapper::mapToInt).toArray());
    List<ModuleEntity> moduleEntities = getAllModulesInProject(project.getModules());
    List<MetricTree> moduleChildren = processModules(moduleEntities, result);
    MetricTree rootModule = processRootModule(result);
    rootModule.getChildren().addAll(findChildModules(project.getModules(), moduleChildren));
    rootModule.setMetrics(aggregateChildMetrics(rootModule.getChildren()));
    return rootModule;
  }

  private List<ModuleEntity> getAllModulesInProject(List<ModuleEntity> modules) {
    if (modules == null) {
      return Collections.emptyList();
    }
    List<ModuleEntity> result = new ArrayList<>(modules);
    for (ModuleEntity entity : modules) {
      result.addAll(getAllModulesInProject(entity.getChildModules()));
    }
    return result;
  }

  @Override
  public MetricTree get(long projectId, GetMetricTreeForCommitCommand command) {
    ProjectEntity projectEntity =
        projectRepository
            .findByIdWithModules(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId));
    return get(
        projectEntity,
        Long.parseUnsignedLong(ValidationUtils.validateAndTrimCommitHash(command.getCommit()), 16),
        command.getMetrics());
  }

  /**
   * Creates a metric tree for each module in the project
   *
   * @param moduleEntities All module entities in the project
   * @param metricValues The calculated metric values for each file in the project.
   * @return A list of metric trees with aggregated metrics for each module.
   */
  private List<MetricTree> processModules(
      List<ModuleEntity> moduleEntities, List<Map<String, Object>> metricValues) {
    List<MetricTree> moduleChildren = new ArrayList<>();
    Collections.reverse(moduleEntities);
    for (ModuleEntity moduleEntity : moduleEntities) {
      MetricTree metricTree = new MetricTree();
      metricTree.setType(MetricTreeNodeType.MODULE);
      metricTree.setName(moduleEntity.getPath());

      Map<String, Long> aggregatedMetrics = new LinkedHashMap<>();
      List<Map<String, Object>> processedFiles = new ArrayList<>();
      for (Map<String, Object> commitTreeQueryResult : metricValues) {
        String path = (String) commitTreeQueryResult.get("path");
        String[] metrics = (String[]) commitTreeQueryResult.get("metrics");
        if (path.startsWith(moduleEntity.getPath())) {
          MetricTree metricTreeFile = new MetricTree();
          metricTreeFile.setName(path);
          metricTreeFile.setType(MetricTreeNodeType.FILE);
          for (String metric : metrics) {
            String[] temp = metric.split("=");
            MetricValueForCommit metricValueForCommit =
                new MetricValueForCommit(
                    MetricName.valueOfInt(Integer.parseInt(temp[0])).getName(),
                    Long.parseLong(temp[1]));
            metricTreeFile.getMetrics().add(metricValueForCommit);
            aggregatedMetrics.putIfAbsent(metricValueForCommit.getMetricName(), 0L);
            aggregatedMetrics.put(
                metricValueForCommit.getMetricName(),
                aggregatedMetrics.get(metricValueForCommit.getMetricName())
                    + metricValueForCommit.getValue());
          }
          metricTree.getChildren().add(metricTreeFile);
          processedFiles.add(commitTreeQueryResult);
        }
      }
      metricValues.removeAll(processedFiles);
      for (Map.Entry<String, Long> metric : aggregatedMetrics.entrySet()) {
        metricTree.getMetrics().add(new MetricValueForCommit(metric.getKey(), metric.getValue()));
      }
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
  private MetricTree processRootModule(List<Map<String, Object>> metricValues) {
    MetricTree rootModule = new MetricTree();
    rootModule.setType(MetricTreeNodeType.MODULE);
    rootModule.setName("root");

    for (Map<String, Object> value : metricValues) {
      String path = (String) value.get("path");
      String[] metrics = (String[]) value.get("metrics");

      MetricTree metricTreeFile = new MetricTree();

      metricTreeFile.setName(path);
      metricTreeFile.setType(MetricTreeNodeType.FILE);

      for (String metric : metrics) {
        String[] temp = metric.split("=");
        MetricValueForCommit metricValueForCommit =
            new MetricValueForCommit(
                MetricName.valueOfInt(Integer.parseInt(temp[0])).getName(),
                Long.parseLong(temp[1]));
        metricTreeFile.getMetrics().add(metricValueForCommit);
      }
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
    Map<String, Long> aggregatedMetrics = new LinkedHashMap<>();
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
    List<MetricValueForCommit> resultList = new ArrayList<>(aggregatedMetrics.size());
    for (Map.Entry<String, Long> metric : aggregatedMetrics.entrySet()) {
      resultList.add(new MetricValueForCommit(metric.getKey(), metric.getValue()));
    }
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
