package io.reflectoring.coderadar.graph.analyzer.service;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.analyzer.domain.FileToCommitRelationship;
import io.reflectoring.coderadar.graph.analyzer.domain.CommitEntity;
import io.reflectoring.coderadar.graph.analyzer.domain.FileEntity;
import io.reflectoring.coderadar.graph.analyzer.domain.FileToCommitRelationshipEntity;
import io.reflectoring.coderadar.graph.analyzer.repository.FileRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.SaveCommitRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.projectadministration.CommitNotFoundException;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@Service
public class SaveCommitAdapter implements SaveCommitPort {
  private final SaveCommitRepository saveCommitRepository;
  private final GetProjectRepository getProjectRepository;
  private final FileRepository fileRepository;

  @Autowired
  public SaveCommitAdapter(
          SaveCommitRepository saveCommitRepository, GetProjectRepository getProjectRepository, FileRepository fileRepository) {
    this.saveCommitRepository = saveCommitRepository;
    this.getProjectRepository = getProjectRepository;
    this.fileRepository = fileRepository;
  }

  @Override
  public void saveCommits(List<Commit> commits, Long projectId) {
    if (!commits.isEmpty()) {
      ProjectEntity projectEntity =
          getProjectRepository
              .findById(projectId)
              .orElseThrow(() -> new ProjectNotFoundException(projectId));
      HashMap<String, FileEntity> walkedFiles = new HashMap<>();
      HashMap<String, CommitEntity> walkedCommits = new HashMap<>();

      commits.sort(Comparator.comparing(Commit::getTimestamp));
      CommitEntity commitEntity = new CommitEntity();
      Commit newestCommit = commits.get(commits.size() - 1);
      commitEntity.setAnalyzed(newestCommit.isAnalyzed());
      commitEntity.setAuthor(newestCommit.getAuthor());
      commitEntity.setComment(newestCommit.getComment());
      commitEntity.setMerged(newestCommit.isMerged());
      commitEntity.setName(newestCommit.getName());
      commitEntity.setTimestamp(newestCommit.getTimestamp());
      getFiles(newestCommit.getTouchedFiles(), commitEntity, walkedFiles);
      commitEntity.setParents(findAndSaveParents(newestCommit, walkedCommits, walkedFiles));
      projectEntity.getFiles().addAll(walkedFiles.values());

      walkedCommits.put(commitEntity.getName(), commitEntity);

      saveCommitRepository.save(walkedCommits.values(), 1);
      fileRepository.save(walkedFiles.values(), 1);
      getProjectRepository.save(projectEntity, 1);
    }
  }

  @Override
  public void saveCommit(Commit commit) {
    CommitEntity commitEntity =
        saveCommitRepository
            .findById(commit.getId())
            .orElseThrow(() -> new CommitNotFoundException(commit.getId()));
    commitEntity.setName(commit.getName());
    commitEntity.setMerged(commit.isMerged());
    commitEntity.setTimestamp(commit.getTimestamp());
    commitEntity.setAuthor(commit.getAuthor());
    commitEntity.setComment(commit.getComment());
    commitEntity.setAnalyzed(commit.isAnalyzed());
    saveCommitRepository.save(commitEntity, 0);
  }

  private List<CommitEntity> findAndSaveParents(
      Commit commit,
      HashMap<String, CommitEntity> walkedCommits,
      HashMap<String, FileEntity> walkedFiles) {

    List<CommitEntity> parents = new ArrayList<>();

    for (Commit c : commit.getParents()) {

      CommitEntity commitEntity = walkedCommits.get(c.getName());
      if (commitEntity != null) {
        parents.add(commitEntity);
      } else {
        commitEntity = new CommitEntity();
        commitEntity.setName(c.getName());
        commitEntity.setAuthor(c.getAuthor());
        commitEntity.setComment(c.getComment());
        commitEntity.setTimestamp(c.getTimestamp());
        getFiles(c.getTouchedFiles(), commitEntity, walkedFiles);
        walkedCommits.put(c.getName(), commitEntity);
        commitEntity.setParents(findAndSaveParents(c, walkedCommits, walkedFiles));
        parents.add(commitEntity);
      }
    }
    return parents;
  }

  private void getFiles(
      List<FileToCommitRelationship> relationships,
      CommitEntity entity,
      HashMap<String, FileEntity> walkedFiles) {
    for (FileToCommitRelationship fileToCommitRelationship : relationships) {
      FileToCommitRelationshipEntity fileToCommitRelationshipEntity =
          new FileToCommitRelationshipEntity();
      fileToCommitRelationshipEntity.setCommit(entity);
      fileToCommitRelationshipEntity.setChangeType(fileToCommitRelationship.getChangeType());
      fileToCommitRelationshipEntity.setOldPath(fileToCommitRelationship.getOldPath());

      FileEntity fileEntity = walkedFiles.get(fileToCommitRelationship.getFile().getPath());
      if (fileEntity == null) {
        fileEntity = new FileEntity();
        fileEntity.setPath(fileToCommitRelationship.getFile().getPath());
        walkedFiles.put(fileEntity.getPath(), fileEntity);
      }
      fileEntity.getCommits().add(fileToCommitRelationshipEntity);
      fileToCommitRelationshipEntity.setFile(fileEntity);
    }
  }
}
