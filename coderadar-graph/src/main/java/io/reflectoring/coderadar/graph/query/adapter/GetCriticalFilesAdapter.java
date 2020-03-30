package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.graph.analyzer.repository.FileRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.adapter.CommitBaseDataMapper;
import io.reflectoring.coderadar.graph.query.domain.ContributorsForFileQueryResult;
import io.reflectoring.coderadar.graph.query.domain.FileAndCommitsForTimePeriodQueryResult;
import io.reflectoring.coderadar.graph.query.repository.ContributorQueryRepository;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.query.domain.ContributorsForFile;
import io.reflectoring.coderadar.query.domain.FileAndCommitsForTimePeriod;
import io.reflectoring.coderadar.query.port.driven.GetCriticalFilesPort;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class GetCriticalFilesAdapter implements GetCriticalFilesPort {
  private final ContributorQueryRepository contributorQueryRepository;
  private final FileRepository fileRepository;
  private final CommitBaseDataMapper mapper = new CommitBaseDataMapper();

  public GetCriticalFilesAdapter(
      ContributorQueryRepository contributorQueryRepository, FileRepository fileRepository) {
    this.contributorQueryRepository = contributorQueryRepository;
    this.fileRepository = fileRepository;
  }

  @Override
  public List<ContributorsForFile> getFilesWithManyContributors(
      long projectId, int numberOfContributors, String commitHash, List<FilePattern> filePatterns) {
    Pair<List<String>, List<String>> includesAndExcludes = mapPatternsToRegex(filePatterns);
    return mapContributorsForFileResult(
        contributorQueryRepository.getFilesWithManyContributors(
            projectId,
            numberOfContributors,
            commitHash,
            includesAndExcludes.getFirst(),
            includesAndExcludes.getSecond()));
  }

  @Override
  public List<FileAndCommitsForTimePeriod> getFrequentlyChangedFiles(
      long projectId,
      String commitHash,
      Date startDate,
      int frequency,
      List<FilePattern> filePatterns) {
    Pair<List<String>, List<String>> includesAndExcludes = mapPatternsToRegex(filePatterns);
    return mapFileAndCommitsForTimePeriodResult(
        fileRepository.getFrequentlyChangedFiles(
            projectId,
            commitHash,
            startDate.getTime(),
            frequency,
            includesAndExcludes.getFirst(),
            includesAndExcludes.getSecond()));
  }

  private Pair<List<String>, List<String>> mapPatternsToRegex(List<FilePattern> filePatterns) {
    List<String> includes = new ArrayList<>();
    List<String> excludes = new ArrayList<>();
    for (FilePattern filePattern : filePatterns) {
      if (filePattern.getInclusionType().equals(InclusionType.INCLUDE)) {
        includes.add(PatternUtil.toPattern(filePattern.getPattern()).toString());
      } else {
        excludes.add(PatternUtil.toPattern(filePattern.getPattern()).toString());
      }
    }
    return Pair.of(includes, excludes);
  }

  private List<ContributorsForFile> mapContributorsForFileResult(
      List<ContributorsForFileQueryResult> queryResult) {
    List<ContributorsForFile> result = new ArrayList<>(queryResult.size());
    for (ContributorsForFileQueryResult qr : queryResult) {
      result.add(new ContributorsForFile(qr.getPath(), qr.getContributors()));
    }
    return result;
  }

  private List<FileAndCommitsForTimePeriod> mapFileAndCommitsForTimePeriodResult(
      List<FileAndCommitsForTimePeriodQueryResult> queryResult) {
    List<FileAndCommitsForTimePeriod> result = new ArrayList<>(queryResult.size());
    for (FileAndCommitsForTimePeriodQueryResult qr : queryResult) {
      result.add(
          new FileAndCommitsForTimePeriod(qr.getPath(), mapper.mapNodeEntities(qr.getCommits())));
    }
    return result;
  }
}
