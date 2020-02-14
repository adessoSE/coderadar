package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.graph.analyzer.repository.FileRepository;
import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import io.reflectoring.coderadar.graph.contributor.repository.ContributorRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.FileEntity;
import io.reflectoring.coderadar.query.port.driven.GetCriticalFilesPort;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetCriticalFilesAdapter implements GetCriticalFilesPort {
  private final FileRepository fileRepository;
  private final ContributorRepository contributorRepository;

  public GetCriticalFilesAdapter(
      FileRepository fileRepository, ContributorRepository contributorRepository) {
    this.fileRepository = fileRepository;
    this.contributorRepository = contributorRepository;
  }

  @Override
  public List<String> getCriticalFiles(Long projectId) {
    List<FileEntity> files = fileRepository.findAllinProject(projectId);
    List<String> criticalFiles = new ArrayList<>();
    for (FileEntity file : files) {
      if (!file.getPath().endsWith(".java")) {
        continue;
      }
      List<ContributorEntity> contributors =
          contributorRepository.findContributorsForFilenameAndProject(projectId, file.getPath());
      if (contributors.size() == 1) {
        criticalFiles.add(file.getPath());
      }
    }
    return criticalFiles;
  }
}
