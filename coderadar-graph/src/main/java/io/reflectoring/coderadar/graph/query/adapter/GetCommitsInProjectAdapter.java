package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.adapter.CommitBaseDataMapper;
import io.reflectoring.coderadar.projectadministration.domain.*;
import io.reflectoring.coderadar.query.port.driven.GetCommitsInProjectPort;
import java.util.*;
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
    List<String> includes = new ArrayList<>();
    List<String> excludes = new ArrayList<>();
    for (FilePattern filePattern : filePatterns) {
      if (filePattern.getInclusionType().equals(InclusionType.INCLUDE)) {
        includes.add(PatternUtil.toPattern(filePattern.getPattern()).toString());
      } else {
        excludes.add(PatternUtil.toPattern(filePattern.getPattern()).toString());
      }
    }
    return mapCommitEntitiesNoParents(
        commitRepository.findByProjectIdNonAnalyzedWithFiles(
            projectId, branch, includes, excludes));
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
    var mapClass = Collections.unmodifiableMap(new HashMap<>(0)).getClass();
    for (Map<String, Object> result : commitEntities) {
      var commitEntity = (CommitEntity) result.get("commit");
      var files = Object[].class.cast(result.get("files"));
      Commit commit = commitBaseDataMapper.mapNodeEntity(commitEntity);
      commit.setTouchedFiles(new ArrayList<>());
      for (var val : files) {
        Map filePathAndId;
        try {
          filePathAndId = mapClass.cast(val);
        } catch (ClassCastException e) {
          filePathAndId = (Map) val;
        }
        FileToCommitRelationship fileToCommitRelationship = new FileToCommitRelationship();
        var fileId = (Long) filePathAndId.get("id");
        var filepath = (String) filePathAndId.get("path");
        if (fileId == null || filepath == null) {
          continue;
        }
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
