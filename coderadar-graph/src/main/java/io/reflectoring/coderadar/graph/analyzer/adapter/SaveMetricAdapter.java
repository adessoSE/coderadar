package io.reflectoring.coderadar.graph.analyzer.adapter;

import com.google.common.collect.Maps;
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
    List<Object> saveData = new ArrayList<>(metricValues.size());
    for (MetricValue metricValue : metricValues) {
      saveData.add(
          new Object[] {
            metricValue.getValue(),
            metricValue.getName(),
            findingsMapper.mapDomainObjects(metricValue.getFindings()),
            metricValue.getFileId(),
            metricValue.getCommitId()
          });
    }
    metricRepository.saveMetrics(saveData);
  }

  @Override
  public Map<Long, List<MetricValue>> getMetricsForFiles(long projectId, String branch) {
    List<FileIdAndMetricQueryResult> metrics =
        metricRepository.getLastMetricsForFiles(projectId, branch);
    Map<Long, List<MetricValue>> filesMetrics = Maps.newHashMapWithExpectedSize(metrics.size());
    for (var i : metrics) {
      long fileId = i.getId();
      List<MetricValueEntity> fileMetrics = i.getMetrics();
      List<MetricValue> mapped = new ArrayList<>(fileMetrics.size());
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
