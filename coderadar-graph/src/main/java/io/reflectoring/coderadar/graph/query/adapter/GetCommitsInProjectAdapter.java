package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.adapter.CommitBaseDataMapper;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.File;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.domain.FileToCommitRelationship;
import io.reflectoring.coderadar.query.port.driven.GetCommitsInProjectPort;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class GetCommitsInProjectAdapter implements GetCommitsInProjectPort {
  private final CommitRepository commitRepository;
  private final CommitBaseDataMapper commitBaseDataMapper = new CommitBaseDataMapper();

  public GetCommitsInProjectAdapter(CommitRepository commitRepository) {
    this.commitRepository = commitRepository;
  }

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
  public List<Commit> getCommitsForContributorSortedByTimestampDescWithNoRelationships(
      long projectId, String branchName, String email) {
    return commitBaseDataMapper.mapNodeEntities(
        commitRepository.findByProjectIdBranchNameAndContributor(projectId, branchName, email));
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
    Map<Long, File> walkedFiles = new HashMap<>(commitEntities.size());
    for (Map<String, Object> result : commitEntities) {
      var commitEntity = (CommitEntity) result.get("commit");
      var files = (Object[]) result.get("files");
      Commit commit = commitBaseDataMapper.mapGraphObject(commitEntity);
      commit.setTouchedFiles(new ArrayList<>(files.length));
      for (var val : files) {
        var filePathAndId = (Map) val;
        var fileId = (Long) filePathAndId.get("id");
        var filepath = (String) filePathAndId.get("path");
        if (fileId == null || filepath == null) {
          continue;
        }
        FileToCommitRelationship fileToCommitRelationship = new FileToCommitRelationship();
        File file = walkedFiles.get(fileId);
        if (file == null) {
          file = new File();
          file.setId(fileId);
          file.setPath(filepath);
          walkedFiles.put(fileId, file);
        }
        fileToCommitRelationship.setFile(file);
        commit.getTouchedFiles().add(fileToCommitRelationship);
      }
      commits.add(commit);
    }
    return commits;
  }
}
