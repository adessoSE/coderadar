package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.adapter.CommitBaseDataMapper;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.File;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.query.domain.CommitResponse;
import io.reflectoring.coderadar.query.port.driven.GetCommitsInProjectPort;
import java.util.ArrayList;
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
  public List<Commit> getNonAnalyzedSortedByTimestampAscWithNoParents(
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
  private List<Commit> mapCommitEntitiesNoParents(List<Map<String, Object>> commitEntities) {
    List<Commit> commits = new ArrayList<>(commitEntities.size());
    Map<Long, File> walkedFiles = new HashMap<>();
    for (Map<String, Object> result : commitEntities) {
      var commitEntity = (Map<String, Object>) result.get("commit");
      var files = (Object[]) result.get("files");
      Commit commit =
          new Commit()
              .setId((long) commitEntity.get("id"))
              .setHash((String) commitEntity.get("hash"));

      commit.setChangedFiles(new ArrayList<>(files.length));
      for (var val : files) {
        var filePathAndId = (Map) val;
        var fileId = (Long) filePathAndId.get("id");
        var filepath = (String) filePathAndId.get("path");
        if (fileId == null || filepath == null) {
          continue;
        }
        File file = walkedFiles.computeIfAbsent(fileId, id -> new File(id, filepath));
        commit.getChangedFiles().add(file);
      }
      commits.add(commit);
    }
    return commits;
  }
}
