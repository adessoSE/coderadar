package io.reflectoring.coderadar.graph.analyzer.service;

import io.reflectoring.coderadar.analyzer.domain.MetricValue;
import io.reflectoring.coderadar.graph.analyzer.repository.SaveMetricRepository;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveMetricPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveMetricAdapter implements SaveMetricPort {

  private SaveMetricRepository saveMetricRepository;

  @Autowired
  public SaveMetricAdapter(SaveMetricRepository saveMetricRepository) {
    this.saveMetricRepository = saveMetricRepository;
  }

  @Override
  public void saveMetricValue(MetricValue metricValue) {
    saveMetricRepository.save(metricValue);
  }
}
