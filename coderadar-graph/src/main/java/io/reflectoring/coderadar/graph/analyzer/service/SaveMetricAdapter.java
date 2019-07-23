package io.reflectoring.coderadar.graph.analyzer.service;

import io.reflectoring.coderadar.analyzer.domain.Finding;
import io.reflectoring.coderadar.analyzer.domain.MetricValue;
import io.reflectoring.coderadar.graph.analyzer.domain.CommitEntity;
import io.reflectoring.coderadar.graph.analyzer.domain.FindingEntity;
import io.reflectoring.coderadar.graph.analyzer.domain.MetricValueEntity;
import io.reflectoring.coderadar.graph.analyzer.repository.SaveMetricRepository;
import io.reflectoring.coderadar.graph.query.repository.GetCommitsInProjectRepository;
import io.reflectoring.coderadar.projectadministration.CommitNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveMetricPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaveMetricAdapter implements SaveMetricPort {

  private SaveMetricRepository saveMetricRepository;

  private GetCommitsInProjectRepository getCommitsInProjectRepository;

  @Autowired
  public SaveMetricAdapter(SaveMetricRepository saveMetricRepository, GetCommitsInProjectRepository getCommitsInProjectRepository) {
    this.saveMetricRepository = saveMetricRepository;
    this.getCommitsInProjectRepository = getCommitsInProjectRepository;
  }

  @Override
  public void saveMetricValues(List<MetricValue> metricValues) {
    CommitEntity commitEntity = new CommitEntity();
    List<MetricValueEntity> metricValueEntities = new ArrayList<>();
    for(MetricValue metricValue : metricValues){
      if(!metricValue.getCommit().getId().equals(commitEntity.getId())){
        commitEntity = getCommitsInProjectRepository.findById(metricValue.getCommit().getId()).orElseThrow(() -> new CommitNotFoundException(metricValue.getCommit().getId()));
        commitEntity.setAnalyzed(true);
      }

      MetricValueEntity metricValueEntity = new MetricValueEntity();
      metricValueEntity.setCommit(commitEntity);
      metricValueEntity.setFindings(mapFindingsToEntities(metricValue.getFindings()));
      metricValueEntity.setValue(metricValue.getValue());
      metricValueEntity.setName(metricValue.getName());
      metricValueEntity.getCommit().getParents().clear();
      metricValueEntities.add(metricValueEntity);
      //saveMetricRepository.save(metricValueEntity, 1);
    }
    saveMetricRepository.save(metricValueEntities, 1);
  }

  private List<FindingEntity> mapFindingsToEntities(List<Finding> findings) {
    List<FindingEntity> result = new ArrayList<>();
    for(Finding finding : findings){
      FindingEntity findingEntity = new FindingEntity();
      findingEntity.setCharEnd(finding.getCharEnd());
      findingEntity.setCharStart(finding.getCharStart());
      findingEntity.setLineEnd(finding.getLineEnd());
      findingEntity.setLineStart(finding.getLineStart());
      result.add(findingEntity);
    }
    return result;
  }
}
