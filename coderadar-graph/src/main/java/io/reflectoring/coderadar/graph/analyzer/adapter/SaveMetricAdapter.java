package io.reflectoring.coderadar.graph.analyzer.adapter;

import io.reflectoring.coderadar.analyzer.domain.MetricValue;
import io.reflectoring.coderadar.graph.analyzer.FindingsMapper;
import io.reflectoring.coderadar.graph.analyzer.domain.MetricValueEntity;
import io.reflectoring.coderadar.graph.analyzer.repository.MetricRepository;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveMetricPort;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SaveMetricAdapter implements SaveMetricPort {

  private final MetricRepository metricRepository;
  private final FindingsMapper findingsMapper = new FindingsMapper();

  public SaveMetricAdapter(MetricRepository metricRepository) {
    this.metricRepository = metricRepository;
  }

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
    metricRepository.save(metricValueEntities, 1);
    List<HashMap<String, Object>> commitAndFileRels = new ArrayList<>(metricValuesSize);
    for (int i = 0; i < metricValuesSize; i++) {
      HashMap<String, Object> commitAndFileRel = new HashMap<>(6);
      commitAndFileRel.put("metricId", metricValueEntities.get(i).getId());
      commitAndFileRel.put("commitId", metricValues.get(i).getCommitId());
      commitAndFileRel.put("fileId", metricValues.get(i).getFileId());
      commitAndFileRels.add(commitAndFileRel);
    }
    metricRepository.createFileAndCommitRelationsips(commitAndFileRels);
  }
}
