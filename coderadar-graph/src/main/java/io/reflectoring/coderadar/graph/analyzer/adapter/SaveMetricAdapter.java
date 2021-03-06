package io.reflectoring.coderadar.graph.analyzer.adapter;

import com.google.common.collect.Maps;
import io.reflectoring.coderadar.analyzer.domain.MetricValue;
import io.reflectoring.coderadar.graph.analyzer.FindingsMapper;
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
    long commitId = metricValues.get(0).getCommitId();
    for (MetricValue metricValue : metricValues) {
      List<String> findings = findingsMapper.mapDomainObjects(metricValue.getFindings());
      saveData.add(
          new Object[] {
            metricValue.getValue(),
            metricValue.getName(),
            findings.isEmpty() ? null : findings,
            metricValue.getFileId(),
          });
    }
    metricRepository.saveMetrics(commitId, saveData);
  }

  @Override
  public Map<Long, List<MetricValue>> getMetricsForFiles(long projectId, String branch) {
    List<Map<String, Object>> metrics = metricRepository.getLastMetricsForFiles(projectId, branch);
    Map<Long, List<MetricValue>> filesMetrics = Maps.newHashMapWithExpectedSize(metrics.size());
    for (var i : metrics) {
      long fileId = (long) i.get("id");
      Object[] fileMetrics = (Object[]) i.get("metrics");
      List<MetricValue> mapped = new ArrayList<>(fileMetrics.length);
      for (var entity : (Map<String, Object>[]) fileMetrics) {
        mapped.add(
            new MetricValue(
                (int) (long) entity.get("name"),
                (int) (long) entity.get("value"),
                0L,
                fileId,
                Collections.emptyList()));
      }
      filesMetrics.put(fileId, mapped);
    }
    return filesMetrics;
  }
}
