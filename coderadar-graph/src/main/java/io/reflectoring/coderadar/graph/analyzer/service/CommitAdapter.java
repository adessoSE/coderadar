package io.reflectoring.coderadar.graph.analyzer.service;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.analyzer.domain.FileToCommitRelationship;
import io.reflectoring.coderadar.graph.analyzer.domain.CommitEntity;
import io.reflectoring.coderadar.graph.analyzer.domain.FileEntity;
import io.reflectoring.coderadar.graph.analyzer.domain.FileToCommitRelationshipEntity;
import io.reflectoring.coderadar.graph.analyzer.domain.MetricValueEntity;
import io.reflectoring.coderadar.graph.analyzer.repository.FileRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.MetricRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.SaveCommitRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.graph.query.repository.GetCommitsInProjectRepository;
import io.reflectoring.coderadar.projectadministration.CommitNotFoundException;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.UpdateCommitsPort;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO: JAVADOC
@Service
public class CommitAdapter implements SaveCommitPort, UpdateCommitsPort {
  private final SaveCommitRepository saveCommitRepository;
  private final GetProjectRepository getProjectRepository;
  private final FileRepository fileRepository;
  private final GetCommitsInProjectRepository getCommitsInProjectRepository;
  private final MetricRepository metricRepository;

  @Autowired
  public CommitAdapter(
          SaveCommitRepository saveCommitRepository,
          GetProjectRepository getProjectRepository,
          FileRepository fileRepository,
          GetCommitsInProjectRepository getCommitsInProjectRepository, MetricRepository metricRepository) {
    this.saveCommitRepository = saveCommitRepository;
    this.getProjectRepository = getProjectRepository;
    this.fileRepository = fileRepository;
    this.getCommitsInProjectRepository = getCommitsInProjectRepository;
    this.metricRepository = metricRepository;
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
      Commit newestCommit = commits.get(commits.size() - 1);
      CommitEntity commitEntity = mapCommitBaseData(newestCommit);
      getFiles(newestCommit.getTouchedFiles(), commitEntity, walkedFiles);
      commitEntity.setParents(findAndSaveParents(newestCommit, walkedCommits, walkedFiles));
      projectEntity.getFiles().addAll(walkedFiles.values());

      walkedCommits.putIfAbsent(commitEntity.getName(), commitEntity);

      saveCommitRepository.save(walkedCommits.values(), 1);
      fileRepository.save(walkedFiles.values(), 1);
      getProjectRepository.save(projectEntity, 1);
    }
  }

  @Override
  public void updateCommits(List<Commit> commits, Long projectId) {
    if (!commits.isEmpty()) {
      ProjectEntity projectEntity =
          getProjectRepository
              .findById(projectId)
              .orElseThrow(() -> new ProjectNotFoundException(projectId));

      List<CommitEntity> commitsInProject =
          getCommitsInProjectRepository.findByProjectId(projectId);
      CommitEntity lastCommit = commitsInProject.get(commitsInProject.size()-1);
      metricRepository.deleteMetricsInCommit(lastCommit.getId());
      Map<String, CommitEntity> walkedCommits = new HashMap<>();

      List<FileEntity> filesInProject = fileRepository.findAllinProject(projectId);
      Map<String, FileEntity> walkedFiles = new HashMap<>();
      for (FileEntity f : filesInProject) {
        walkedFiles.putIfAbsent(f.getPath(), f);
      }

      commits.sort(Comparator.comparing(Commit::getTimestamp));
      Commit newestCommit = commits.get(commits.size() - 1);
      CommitEntity commitEntity = mapCommitBaseData(newestCommit);
      getFiles(newestCommit.getTouchedFiles(), commitEntity, walkedFiles);
      commitEntity.setParents(findAndSaveParents(newestCommit, walkedCommits, walkedFiles));
      projectEntity.getFiles().addAll(walkedFiles.values());

      walkedCommits.putIfAbsent(commitEntity.getName(), commitEntity);

      for(CommitEntity commit : commitsInProject){
        Optional<CommitEntity> existingCommit = walkedCommits.values().stream().filter(c -> c.getName().equals(commit.getName())).findAny();
        if(!existingCommit.isPresent()){
          metricRepository.deleteMetricsInCommit(commit.getId());
        }
      }
      getCommitsInProjectRepository.deleteAll(commitsInProject);
      saveCommitRepository.save(walkedCommits.values(), 1);
      fileRepository.save(walkedFiles.values(), 1);
      projectEntity.getFiles().clear();

      projectEntity.getFiles().addAll(walkedFiles.values());
      fileRepository.removeFilesWithoutCommits(projectEntity.getId());
      metricRepository.removeMetricsWithoutCommits(projectId);
      getProjectRepository.save(projectEntity, 1);
    }
  }

  private CommitEntity mapCommitBaseData(Commit commit) {
    CommitEntity commitEntity = new CommitEntity();
    commitEntity.setAnalyzed(commit.isAnalyzed());
    commitEntity.setAuthor(commit.getAuthor());
    commitEntity.setComment(commit.getComment());
    commitEntity.setMerged(commit.isMerged());
    commitEntity.setName(commit.getName());
    commitEntity.setTimestamp(commit.getTimestamp());
    return commitEntity;
  }

  @Override
  public void saveCommit(Commit commit) {
    CommitEntity commitEntity =
        saveCommitRepository
            .findById(commit.getId())
            .orElseThrow(() -> new CommitNotFoundException(commit.getId()));
    Long id = commitEntity.getId();
    commitEntity = mapCommitBaseData(commit);
    commitEntity.setId(id);
    saveCommitRepository.save(commitEntity, 0);
  }

  private List<CommitEntity> findAndSaveParents(
      Commit commit, Map<String, CommitEntity> walkedCommits, Map<String, FileEntity> walkedFiles) {

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
        walkedCommits.putIfAbsent(c.getName(), commitEntity);
        commitEntity.setParents(findAndSaveParents(c, walkedCommits, walkedFiles));
        parents.add(commitEntity);
      }
    }
    return parents;
  }

  private void getFiles(
      List<FileToCommitRelationship> relationships,
      CommitEntity entity,
      Map<String, FileEntity> walkedFiles) {
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
        walkedFiles.putIfAbsent(fileEntity.getPath(), fileEntity);
      }
      fileEntity.getCommits().add(fileToCommitRelationshipEntity);
      if(fileEntity.getId() != null) { //If the file entity already exists, check if it has any metrics and attach those to the commit entity.
          for (MetricValueEntity metric : fileRepository.findMetricsByFileAndCommitName(fileEntity.getId(), entity.getName())) {
            entity.getMetricValues().add(metric);
            entity.setAnalyzed(true);
          }
      }
      fileToCommitRelationshipEntity.setFile(fileEntity);
    }
  }
}
