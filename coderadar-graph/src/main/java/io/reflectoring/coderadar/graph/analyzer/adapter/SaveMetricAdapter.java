package io.reflectoring.coderadar.graph.analyzer.adapter;

import io.reflectoring.coderadar.analyzer.domain.MetricValue;
import io.reflectoring.coderadar.graph.analyzer.FindingsMapper;
import io.reflectoring.coderadar.graph.analyzer.domain.MetricValueEntity;
import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.FileRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.MetricRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.FileEntity;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveMetricPort;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class SaveMetricAdapter implements SaveMetricPort {

  private final MetricRepository metricRepository;
  private final CommitRepository commitRepository;
  private final FileRepository fileRepository;
  private final FindingsMapper findingsMapper = new FindingsMapper();

  public SaveMetricAdapter(
      MetricRepository metricRepository,
      CommitRepository commitRepository,
      FileRepository fileRepository) {
    this.metricRepository = metricRepository;
    this.commitRepository = commitRepository;
    this.fileRepository = fileRepository;
  }

  @Override
  public void saveMetricValues(List<MetricValue> metricValues) {

    List<Long> commitIds = new ArrayList<>();
    List<Long> fileIds = new ArrayList<>();

    for (MetricValue metricValue : metricValues) {
      commitIds.add(metricValue.getCommit().getId());
      fileIds.add(metricValue.getFileId());
    }

    // Get the commit entities of the metric values.
    Map<Long, CommitEntity> commits =
        commitRepository
            .findAllById(commitIds)
            .stream()
            .collect(
                Collectors.toMap(
                    CommitEntity::getId,
                    commitEntity -> {
                      commitEntity.setAnalyzed(true);
                      return commitEntity;
                    }));

    // Get the file entities of the metric values.
    Map<Long, FileEntity> files =
        fileRepository
            .findAllById(fileIds)
            .stream()
            .collect(Collectors.toMap(FileEntity::getId, fileEntity -> fileEntity));

    List<MetricValueEntity> metricValueEntities = new ArrayList<>();

    for (MetricValue metricValue : metricValues) {
      CommitEntity commitEntity = commits.get(metricValue.getCommit().getId());
      FileEntity fileEntity = files.get(metricValue.getFileId());

      MetricValueEntity metricValueEntity = new MetricValueEntity();

      metricValueEntity.setCommit(commitEntity);
      metricValueEntity.setFile(fileEntity);
      metricValueEntity.setValue(metricValue.getValue());
      metricValueEntity.setName(metricValue.getName());
      metricValueEntity.setFindings(findingsMapper.mapDomainObjects(metricValue.getFindings()));

      metricValueEntities.add(metricValueEntity);
    }
    metricRepository.save(metricValueEntities, 1);
  }
}
