package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.graph.analyzer.FindingsMapper;
import io.reflectoring.coderadar.graph.query.repository.MetricQueryRepository;
import io.reflectoring.coderadar.plugin.api.Finding;
import io.reflectoring.coderadar.query.domain.MetricWithFindings;
import io.reflectoring.coderadar.query.port.driven.GetMetricsAndFindingsForFilePort;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetMetricsAndFindingsForFileAdapter implements GetMetricsAndFindingsForFilePort {

  private final MetricQueryRepository metricQueryRepository;
  private final FindingsMapper findingsMapper = new FindingsMapper();

  public List<MetricWithFindings> getMetricsAndFindingsForFile(
      long projectId, String commitHash, String filepath) {
    List<Map<String, Object>> metrics =
        metricQueryRepository.getMetricsAndFindingsForCommitAndFilepath(
            projectId, commitHash, filepath);
    List<MetricWithFindings> result = new ArrayList<>(metrics.size());
    for (Map<String, Object> metric : metrics) {
      String name = (String) metric.get("name");
      long value = (long) metric.get("value");
      var findingsTemp = (Object[]) metric.get("findings");
      List<String> strings = new ArrayList<>(findingsTemp.length);
      for (Object f : findingsTemp) {
        strings.add((String) f);
      }
      List<Finding> findings = findingsMapper.mapNodeEntities(strings);
      result.add(new MetricWithFindings(name, value, findings));
    }
    return result;
  }
}
