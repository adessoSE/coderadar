package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.analyzer.domain.AnalyzeCommitDto;
import io.reflectoring.coderadar.analyzer.domain.AnalyzeFileDto;
import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.adapter.CommitBaseDataMapper;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.query.domain.CommitResponse;
import io.reflectoring.coderadar.query.port.driven.GetCommitsInProjectPort;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCommitsInProjectAdapter implements GetCommitsInProjectPort {

  private final CommitRepository commitRepository;
  private final CommitBaseDataMapper commitBaseDataMapper = new CommitBaseDataMapper();

  @Override
  public List<Commit> getCommitsSortedByTimestampDescWithNoRelationships(
      long projectId, String branch) {
    return commitBaseDataMapper.mapNodeEntities(
        commitRepository.findByProjectIdAndBranchName(projectId, branch));
  }

  @Override
  public AnalyzeCommitDto[] getNonAnalyzedSortedByTimestampAscWithNoParents(
      long projectId, List<FilePattern> filePatterns, String branch) {
    // Map Ant-Patterns to RegEx
    Pair<List<String>, List<String>> includesAndExcludes =
        PatternUtil.mapPatternsToRegex(filePatterns);
    return mapCommitEntitiesNoParents(
        commitRepository.findByProjectIdNonAnalyzedWithFiles(
            projectId, branch, includesAndExcludes.getFirst(), includesAndExcludes.getSecond()));
  }

  @Override
  public CommitResponse[] getCommitResponses(long projectId, String branch) {
    return mapCommitResponses(
        commitRepository.findByProjectIdAndBranchNameResponses(projectId, branch));
  }

  @Override
  public CommitResponse[] getCommitsForContributorResponses(
      long projectId, String branchName, String email) {
    return mapCommitResponses(
        commitRepository.findByProjectIdAndBranchNameAndEmailResponses(
            projectId, branchName, email));
  }

  private CommitResponse[] mapCommitResponses(List<Map<String, Object>> entities) {
    CommitResponse[] commits = new CommitResponse[entities.size()];
    int i = 0;
    for (Map<String, Object> commitEntity : entities) {
      CommitResponse commit =
          new CommitResponse()
              .setHash((String) commitEntity.get("hash"))
              .setAnalyzed((boolean) commitEntity.get("analyzed"))
              .setAuthor((String) commitEntity.get("author"))
              .setAuthorEmail((String) commitEntity.get("authorEmail"))
              .setComment((String) commitEntity.get("comment"))
              .setTimestamp((long) commitEntity.get("timestamp"));
      commits[i++] = commit;
    }
    return commits;
  }

  /**
   * Maps a list of commit entities to Commit domain objects. Does not map parent relationships and
   * does not set attributes on FileToCommitRelationships.
   *
   * @param commitEntities The commit entities to map.
   * @return A list of commit domain objects.
   */
  private AnalyzeCommitDto[] mapCommitEntitiesNoParents(List<Map<String, Object>> commitEntities) {
    AnalyzeCommitDto[] commits = new AnalyzeCommitDto[commitEntities.size()];
    Map<Long, AnalyzeFileDto> walkedFiles = new HashMap<>();
    int i = 0;
    for (Map<String, Object> result : commitEntities) {
      var files =
          Arrays.stream((Object[]) result.get("files"))
              .filter(o -> ((Map) o).get("id") != null)
              .toArray();
      AnalyzeFileDto[] changedFiles = new AnalyzeFileDto[files.length];
      int j = 0;
      for (var val : files) {
        var filePathAndId = (Map) val;
        var fileId = (Long) filePathAndId.get("id");
        var filepath = (String) filePathAndId.get("path");
        AnalyzeFileDto file =
            walkedFiles.computeIfAbsent(fileId, id -> new AnalyzeFileDto(id, filepath));
        changedFiles[j++] = file;
      }
      var commitEntity = (Map<String, Object>) result.get("commit");
      commits[i++] =
          new AnalyzeCommitDto(
              (long) commitEntity.get("id"), (String) commitEntity.get("hash"), changedFiles);
    }
    return commits;
  }
}
