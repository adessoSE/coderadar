package io.reflectoring.coderadar.graph.analyzer.service;

import io.reflectoring.coderadar.analyzer.domain.Finding;
import io.reflectoring.coderadar.analyzer.domain.MetricValue;
import io.reflectoring.coderadar.graph.analyzer.domain.CommitEntity;
import io.reflectoring.coderadar.graph.analyzer.domain.FileEntity;
import io.reflectoring.coderadar.graph.analyzer.domain.FindingEntity;
import io.reflectoring.coderadar.graph.analyzer.domain.MetricValueEntity;
import io.reflectoring.coderadar.graph.analyzer.repository.FileRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.MetricRepository;
import io.reflectoring.coderadar.graph.query.repository.GetCommitsInProjectRepository;
import io.reflectoring.coderadar.projectadministration.CommitNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveMetricPort;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveMetricAdapter implements SaveMetricPort {

  private MetricRepository metricRepository;

  private GetCommitsInProjectRepository getCommitsInProjectRepository;
  private final FileRepository fileRepository;

  @Autowired
  public SaveMetricAdapter(
      MetricRepository metricRepository,
      GetCommitsInProjectRepository getCommitsInProjectRepository,
      FileRepository fileRepository) {
    this.metricRepository = metricRepository;
    this.getCommitsInProjectRepository = getCommitsInProjectRepository;
    this.fileRepository = fileRepository;
  }

  @Override
  public void saveMetricValues(List<MetricValue> metricValues, Long projectId) {
    CommitEntity commitEntity = new CommitEntity();
    List<MetricValueEntity> metricValueEntities = new ArrayList<>();
    HashMap<String, FileEntity> visitedFiles = new HashMap<>();
    for (MetricValue metricValue : metricValues) {
      if (!metricValue.getCommit().getId().equals(commitEntity.getId())) {
        commitEntity =
            getCommitsInProjectRepository
                .findById(metricValue.getCommit().getId(), 0)
                .orElseThrow(() -> new CommitNotFoundException(metricValue.getCommit().getId()));
        commitEntity.setAnalyzed(true);
      }

      MetricValueEntity metricValueEntity = new MetricValueEntity();
      metricValueEntity.setCommit(commitEntity);

      FileEntity fileEntity = visitedFiles.get(metricValue.getFilepath());
      if (fileEntity == null) {
        fileEntity = fileRepository.findByPathInProject(metricValue.getFilepath(), projectId);
        visitedFiles.put(metricValue.getFilepath(), fileEntity);
      }
      metricValueEntity.setFile(fileEntity);
      metricValueEntity.setFindings(mapFindingsToEntities(metricValue.getFindings()));
      metricValueEntity.setValue(metricValue.getValue());
      metricValueEntity.setName(metricValue.getName());
      metricValueEntity.getCommit().getParents().clear();
      metricValueEntities.add(metricValueEntity);
    }
    metricRepository.save(metricValueEntities, 1);
  }

  private List<FindingEntity> mapFindingsToEntities(List<Finding> findings) {
    List<FindingEntity> result = new ArrayList<>();
    for (Finding finding : findings) {
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
