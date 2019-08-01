package io.reflectoring.coderadar.graph.query.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.CreateModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.ListModulesOfProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.graph.query.repository.GetMetricValuesOfCommitRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.MetricValueForCommitTreeQueryResult;
import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.query.domain.MetricValueForCommit;
import io.reflectoring.coderadar.query.domain.MetricsTreeNodeType;
import io.reflectoring.coderadar.query.port.driven.GetMetricsForAllFilesInCommitPort;
import io.reflectoring.coderadar.query.port.driven.MetricTree;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForCommitCommand;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GetMetricsForAllFilesInCommitAdapter implements GetMetricsForAllFilesInCommitPort {

    private final GetMetricValuesOfCommitRepository getMetricValuesOfCommitRepository;
    private final GetProjectRepository getProjectRepository;
    private final ListModulesOfProjectRepository listModulesOfProjectRepository;
    private final CreateModuleRepository createModuleRepository;

    public GetMetricsForAllFilesInCommitAdapter(GetMetricValuesOfCommitRepository getMetricValuesOfCommitRepository, GetProjectRepository getProjectRepository, ListModulesOfProjectRepository listModulesOfProjectRepository, CreateModuleRepository createModuleRepository) {
        this.getMetricValuesOfCommitRepository = getMetricValuesOfCommitRepository;
        this.getProjectRepository = getProjectRepository;
        this.listModulesOfProjectRepository = listModulesOfProjectRepository;
        this.createModuleRepository = createModuleRepository;
    }


    @Override
  public MetricTree get(GetMetricsForCommitCommand command, Long projectId) {
        ProjectEntity projectEntity = getProjectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
      List<MetricValueForCommitTreeQueryResult> result = getMetricValuesOfCommitRepository.getMetricTreeForCommit(command.getCommit(), command.getMetrics());

      List<ModuleEntity> moduleEntities = listModulesOfProjectRepository.findModulesInProject(projectId);
      moduleEntities.sort(Comparator.comparing(ModuleEntity::getPath));
        Collections.reverse(moduleEntities);

        List<MetricTree> moduleChildren = new ArrayList<>();
        for(ModuleEntity moduleEntity : moduleEntities){
            MetricTree metricTree = new MetricTree();
            metricTree.setType(MetricsTreeNodeType.MODULE);
            metricTree.setName(moduleEntity.getPath());

            Map<String, Long> aggregatedMetrics = new HashMap<>();
            if(result.isEmpty()){
                break;
            }
            for(MetricValueForCommitTreeQueryResult commitTreeQueryResult : result){
                if(commitTreeQueryResult.getPath().startsWith(moduleEntity.getPath())){
                    MetricTree metricTreeFile = new MetricTree();
                    metricTreeFile.setName(commitTreeQueryResult.getPath());
                    metricTreeFile.setType(MetricsTreeNodeType.FILE);
                    for(Map<String, Object> metric : commitTreeQueryResult.getMetrics()){
                        MetricValueForCommit metricValueForCommit = new MetricValueForCommit((String)metric.get("name"), (Long)metric.get("value"));
                        metricTreeFile.getMetrics().add(metricValueForCommit);
                        aggregatedMetrics.putIfAbsent(metricValueForCommit.getMetricName(), 0L);
                        aggregatedMetrics.put(metricValueForCommit.getMetricName(), aggregatedMetrics.get(metricValueForCommit.getMetricName()) + metricValueForCommit.getValue());
                    }
                    metricTree.getChildren().add(metricTreeFile);
                }
            }
            for(Map.Entry<String, Long> metric : aggregatedMetrics.entrySet()){
                metricTree.getMetrics().add(new MetricValueForCommit(metric.getKey(), metric.getValue()));
            }
            moduleChildren.add(metricTree);
        }

        MetricTree rootModule = processRootModule(result);
        rootModule.getChildren().addAll(findChildModules(projectEntity.getModules(), moduleChildren));
        List<MetricValueForCommit> aggregatedMetricsForRootModule = aggregateChildMetrics(rootModule.getChildren());
        for(MetricValueForCommit val : aggregatedMetricsForRootModule){
            if(rootModule.getMetrics().stream().noneMatch(metric -> metric.getMetricName().equals(val.getMetricName()))) {
                rootModule.getMetrics().add(new MetricValueForCommit(val.getMetricName(), val.getValue()));
            }
        }
      return rootModule;
  }

    private MetricTree processRootModule(List<MetricValueForCommitTreeQueryResult> result) {
        MetricTree rootModule = new MetricTree();
        rootModule.setType(MetricsTreeNodeType.MODULE);
        rootModule.setName("root");
        Map<String, Long> aggregatedMetrics = new HashMap<>();

        for(MetricValueForCommitTreeQueryResult value : result) {
            MetricTree metricTreeFile = new MetricTree();

            metricTreeFile.setName(value.getPath());
            metricTreeFile.setType(MetricsTreeNodeType.FILE);

            for (Map<String, Object> metric : value.getMetrics()) {
                MetricValueForCommit metricValueForCommit = new MetricValueForCommit((String)metric.get("name"), (Long)metric.get("value"));
                metricTreeFile.getMetrics().add(metricValueForCommit);
                aggregatedMetrics.putIfAbsent(metricValueForCommit.getMetricName(), 0L);
                aggregatedMetrics.put(metricValueForCommit.getMetricName(), aggregatedMetrics.get(metricValueForCommit.getMetricName()) + metricValueForCommit.getValue());
            }
            rootModule.getChildren().add(metricTreeFile);
        }
        for(Map.Entry<String, Long> metric : aggregatedMetrics.entrySet()){
            rootModule.getMetrics().add(new MetricValueForCommit(metric.getKey(), metric.getValue()));
        }
        return rootModule;
    }

    private List<MetricValueForCommit> aggregateChildMetrics(List<MetricTree> children) {
        List<MetricValueForCommit> resultList = new ArrayList<>();
        Map<String, Long> aggregatedMetrics = new HashMap<>();
        for(MetricTree metricTree : children){
            for(MetricValueForCommit val : aggregateChildMetrics(metricTree.getChildren())){
                if(metricTree.getMetrics().stream().noneMatch(metric -> metric.getMetricName().equals(val.getMetricName()))) {
                    metricTree.getMetrics().add(new MetricValueForCommit(val.getMetricName(), val.getValue()));
                }
            }
            for(MetricValueForCommit value : metricTree.getMetrics()){
                aggregatedMetrics.putIfAbsent(value.getMetricName(), 0L);
                aggregatedMetrics.put(value.getMetricName(), aggregatedMetrics.get(value.getMetricName()) + value.getValue());
            }
        }
        for(Map.Entry<String, Long> metric : aggregatedMetrics.entrySet()){
            resultList.add(new MetricValueForCommit(metric.getKey(), metric.getValue()));
        }
        return resultList;
    }

    private List<MetricTree> findChildModules(List<ModuleEntity> moduleEntities, List<MetricTree> metricTrees) {
        List<MetricTree> result = new ArrayList<>();
        for(ModuleEntity moduleEntity : moduleEntities){
            for(MetricTree metricTree : metricTrees){
                if(metricTree.getName().equals(moduleEntity.getPath())){
                    Long moduleId = moduleEntity.getId();
                    moduleEntity = createModuleRepository.findById(moduleEntity.getId()).orElseThrow(() -> new ModuleNotFoundException(moduleId));
                    metricTree.getChildren().addAll(findChildModules(moduleEntity.getChildModules(), metricTrees));
                    result.add(metricTree);
                }
            }
        }
        return result;
    }
}
