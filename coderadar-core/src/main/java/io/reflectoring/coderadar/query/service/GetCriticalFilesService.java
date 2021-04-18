package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.ValidationUtils;
import io.reflectoring.coderadar.analyzer.MisconfigurationException;
import io.reflectoring.coderadar.domain.ContributorsForFile;
import io.reflectoring.coderadar.domain.FileAndCommitsForTimePeriod;
import io.reflectoring.coderadar.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.ListFilePatternsOfProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.query.port.driven.GetCriticalFilesPort;
import io.reflectoring.coderadar.query.port.driver.criticalfiles.GetCriticalFilesUseCase;
import io.reflectoring.coderadar.query.port.driver.criticalfiles.GetFilesWithContributorsCommand;
import io.reflectoring.coderadar.query.port.driver.criticalfiles.GetFrequentlyChangedFilesCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCriticalFilesService implements GetCriticalFilesUseCase {
  private final GetCriticalFilesPort getCriticalFilesPort;
  private final GetProjectPort getProjectPort;
  private final ListFilePatternsOfProjectPort listFilePatternsOfProjectPort;

  @Override
  public List<ContributorsForFile> getFilesWithContributors(
      long projectId, GetFilesWithContributorsCommand command) {
    checkProjectExists(projectId);
    List<FilePattern> filePatterns = getFilePatternsForProject(projectId);
    return getCriticalFilesPort.getFilesWithContributors(
        projectId,
        command.getNumberOfContributors(),
        Long.parseUnsignedLong(
            ValidationUtils.validateAndTrimCommitHash(command.getCommitHash()), 16),
        filePatterns);
  }

  @Override
  public List<FileAndCommitsForTimePeriod> getFrequentlyChangedFiles(
      long projectId, GetFrequentlyChangedFilesCommand command) {
    checkProjectExists(projectId);
    List<FilePattern> filePatterns = getFilePatternsForProject(projectId);
    return getCriticalFilesPort.getFrequentlyChangedFiles(
        projectId,
        Long.parseUnsignedLong(
            ValidationUtils.validateAndTrimCommitHash(command.getCommitHash()), 16),
        command.getStartDate(),
        command.getFrequency(),
        filePatterns);
  }

  private void checkProjectExists(long projectId) {
    if (!getProjectPort.existsById(projectId)) {
      throw new ProjectNotFoundException(projectId);
    }
  }

  private List<FilePattern> getFilePatternsForProject(long projectId) {
    List<FilePattern> filePatterns = listFilePatternsOfProjectPort.listFilePatterns(projectId);
    if (filePatterns.isEmpty()) {
      throw new MisconfigurationException("No file patterns defined for this project");
    }
    return filePatterns;
  }
}
