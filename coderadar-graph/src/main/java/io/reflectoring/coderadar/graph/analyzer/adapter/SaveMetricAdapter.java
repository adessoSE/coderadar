package io.reflectoring.coderadar.graph.analyzer.adapter;

import io.reflectoring.coderadar.analyzer.domain.MetricValue;
import io.reflectoring.coderadar.graph.analyzer.FindingsMapper;
import io.reflectoring.coderadar.graph.analyzer.domain.FileIdAndMetricQueryResult;
import io.reflectoring.coderadar.graph.analyzer.domain.MetricValueEntity;
import io.reflectoring.coderadar.graph.analyzer.repository.MetricRepository;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveMetricPort;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveMetricAdapter implements SaveMetricPort {

  private final MetricRepository metricRepository;
  private final FindingsMapper findingsMapper = new FindingsMapper();

  @Override
  public void saveMetricValues(List<MetricValue> metricValues) {
    int metricValuesSize = metricValues.size();
    List<MetricValueEntity> metricValueEntities = new ArrayList<>(metricValuesSize);
    for (MetricValue metricValue : metricValues) {
      MetricValueEntity metricValueEntity = new MetricValueEntity();
      metricValueEntity.setValue(metricValue.getValue());
      metricValueEntity.setName(metricValue.getName());
      metricValueEntity.setFindings(findingsMapper.mapDomainObjects(metricValue.getFindings()));
      metricValueEntities.add(metricValueEntity);
    }
    metricRepository.save(metricValueEntities, 0);
    List<long[]> commitAndFileRels = new ArrayList<>(metricValuesSize);
    for (int i = 0; i < metricValuesSize; i++) {
      commitAndFileRels.add(
          new long[] {
            metricValueEntities.get(i).getId(),
            metricValues.get(i).getFileId(),
            metricValues.get(i).getCommitId()
          });
    }
    metricRepository.createFileAndCommitRelationships(commitAndFileRels);
  }

  @Override
  public Map<Long, List<MetricValue>> getMetricsForFiles(long projectId, String branch) {
    List<FileIdAndMetricQueryResult> metrics =
        metricRepository.getLastMetricsForFiles(projectId, branch);
    Map<Long, List<MetricValue>> filesMetrics = new HashMap<>();
    for (var i : metrics) {
      long fileId = i.getId();
      List<MetricValueEntity> fileMetrics = i.getMetrics();
      List<MetricValue> mapped = new ArrayList<>();
      for (MetricValueEntity entity : fileMetrics) {
        mapped.add(
            new MetricValue(
                entity.getName(), entity.getValue(), 0L, fileId, Collections.emptyList()));
      }
      filesMetrics.put(fileId, mapped);
    }
    return filesMetrics;
  }
}
