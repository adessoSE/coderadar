package io.reflectoring.coderadar.graph.analyzer.service;

import io.reflectoring.coderadar.analyzer.port.driven.StartAnalyzingPort;
import io.reflectoring.coderadar.analyzer.port.driver.StartAnalyzingCommand;
import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzingJobEntity;
import io.reflectoring.coderadar.graph.analyzer.repository.AnalyzingJobRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class StartAnalyzingAdapter implements StartAnalyzingPort {
  private final ProjectRepository projectRepository;
  private final AnalyzingJobRepository analyzingJobRepository;

  public StartAnalyzingAdapter(
      ProjectRepository projectRepository, AnalyzingJobRepository analyzingJobRepository) {
    this.projectRepository = projectRepository;
    this.analyzingJobRepository = analyzingJobRepository;
  }

  @Override
  public Long start(StartAnalyzingCommand command, Long projectId) {
    Optional<AnalyzingJobEntity> analyzingJobEntity =
        analyzingJobRepository.findByProjectId(projectId);

    if (analyzingJobEntity.isPresent()) {
      analyzingJobEntity.get().setActive(true);
      return analyzingJobRepository.save(analyzingJobEntity.get()).getId();
    } else {
      AnalyzingJobEntity analyzingJob = new AnalyzingJobEntity();
      analyzingJob.setRescan(command.getRescan());
      analyzingJob.setFrom(command.getFrom());
      analyzingJob.setActive(true);

      analyzingJob = analyzingJobRepository.save(analyzingJob);
      projectRepository.setAnalyzingJob(projectId, analyzingJob.getId());
      return analyzingJob.getId();
    }
  }
}
