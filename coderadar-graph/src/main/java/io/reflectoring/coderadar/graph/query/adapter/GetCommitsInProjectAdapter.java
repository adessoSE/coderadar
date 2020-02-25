package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.FileEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.FileToCommitRelationshipEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.adapter.CommitBaseDataMapper;
import io.reflectoring.coderadar.projectadministration.domain.*;
import io.reflectoring.coderadar.query.port.driven.GetCommitsInProjectPort;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
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
      Long projectId, String branch) {
    return commitBaseDataMapper.mapNodeEntities(
        commitRepository.findByProjectIdAndBranchName(projectId, branch));
  }

  @Override
  public List<Commit> getNonAnalyzedSortedByTimestampAscWithNoParents(
      Long projectId, List<FilePattern> filePatterns, String branch) {
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
        commitRepository.findByProjectIdNonAnalyzedWithFileRelationships(
            projectId, branch, includes, excludes));
  }

  /**
   * Maps a list of commit entities to Commit domain objects. Does not map parent relationships.
   *
   * @param commitEntities The commit entities to map.
   * @return A list of commit domain objects.
   */
  private List<Commit> mapCommitEntitiesNoParents(List<CommitEntity> commitEntities) {
    List<Commit> commits = new ArrayList<>(commitEntities.size());
    IdentityHashMap<FileEntity, File> walkedFiles = new IdentityHashMap<>(commitEntities.size());
    for (CommitEntity commitEntity : commitEntities) {
      Commit commit = commitBaseDataMapper.mapNodeEntity(commitEntity);
      commit.setTouchedFiles(
          mapFileToCommitRelationships(commitEntity.getTouchedFiles(), walkedFiles));
      commits.add(commit);
    }
    return commits;
  }

  /**
   * Maps FileToCommitRelationshipEntities to FileToCommitRelationships. Creates a File domain
   * object for each FileEntity
   *
   * @param touchedFiles The files touched in the current commit.
   * @param walkedFiles A map of all the file entities created so far. (Empty on first method run)
   * @return A list of FileToCommitRelationships with the accompanying files.
   */
  private List<FileToCommitRelationship> mapFileToCommitRelationships(
      List<FileToCommitRelationshipEntity> touchedFiles,
      IdentityHashMap<FileEntity, File> walkedFiles) {
    List<FileToCommitRelationship> fileToCommitRelationships = new ArrayList<>(touchedFiles.size());

    for (FileToCommitRelationshipEntity fileToCommitRelationshipEntity : touchedFiles) {
      FileToCommitRelationship fileToCommitRelationship = new FileToCommitRelationship();

      File file = walkedFiles.get(fileToCommitRelationshipEntity.getFile());
      if (file == null) {
        file = new File();
        file.setId(fileToCommitRelationshipEntity.getFile().getId());
        file.setPath(fileToCommitRelationshipEntity.getFile().getPath());
        walkedFiles.put(fileToCommitRelationshipEntity.getFile(), file);
      }
      fileToCommitRelationship.setFile(file);
      fileToCommitRelationships.add(fileToCommitRelationship);
    }
    return fileToCommitRelationships;
  }
}
