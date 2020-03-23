package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.graph.query.domain.ContributorsForFileQueryResult;
import io.reflectoring.coderadar.graph.query.repository.ContributorQueryRepository;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.query.domain.ContributorsForFile;
import io.reflectoring.coderadar.query.port.driven.GetCriticalFilesPort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetCriticalFilesAdapter implements GetCriticalFilesPort {
  private final ContributorQueryRepository contributorQueryRepository;

  public GetCriticalFilesAdapter(ContributorQueryRepository contributorQueryRepository) {
    this.contributorQueryRepository = contributorQueryRepository;
  }

  @Override
  public List<ContributorsForFile> getCriticalFiles(
      Long projectId, int numberOfContributors, String commitHash, List<FilePattern> filePatterns) {

    // Map Ant-Patterns to RegEx
    List<String> includes = new ArrayList<>();
    List<String> excludes = new ArrayList<>();
    for (FilePattern filePattern : filePatterns) {
      if (filePattern.getInclusionType().equals(InclusionType.INCLUDE)) {
        includes.add(PatternUtil.toPattern(filePattern.getPattern()).toString());
      } else {
        excludes.add(PatternUtil.toPattern(filePattern.getPattern()).toString());
      }
    }
    return mapQueryResults(
        contributorQueryRepository.getCriticalFiles(
            projectId, numberOfContributors, commitHash, includes, excludes));
  }

  private List<ContributorsForFile> mapQueryResults(
      List<ContributorsForFileQueryResult> criticalFiles) {
    List<ContributorsForFile> result = new ArrayList<>(criticalFiles.size());
    for (ContributorsForFileQueryResult qr : criticalFiles) {
      result.add(new ContributorsForFile(qr.getPath(), qr.getContributors()));
    }
    return result;
  }
}
