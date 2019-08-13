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
      GetCommitsInProjectRepository getCommitsInProjectRepository,
      MetricRepository metricRepository) {
    this.saveCommitRepository = saveCommitRepository;
    this.getProjectRepository = getProjectRepository;
    this.fileRepository = fileRepository;
    this.getCommitsInProjectRepository = getCommitsInProjectRepository;
    this.metricRepository = metricRepository;
  }

  /**
   * This method should be used for the initial creation of Commits when saving a project. It maps
   * all of the domain objects to entities and saves them in the DB.
   *
   * @param commits The commit tree to save.
   * @param projectId The id of the project.
   */
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

  /**
   * Update the commits in the project (add or remove commits from the commit tree)
   *
   * @param commits The new Commit tree
   * @param projectId The projectId
   */
  @Override
  public void updateCommits(List<Commit> commits, Long projectId) {
    if (!commits.isEmpty()) {
      ProjectEntity projectEntity =
          getProjectRepository
              .findById(projectId)
              .orElseThrow(() -> new ProjectNotFoundException(projectId));

      List<CommitEntity> commitsInProject = // First we need all of the commits in the project
          getCommitsInProjectRepository.findByProjectId(projectId);

      commits.sort(Comparator.comparing(Commit::getTimestamp));
      Commit newLastCommit = commits.get(0);
      CommitEntity lastCommit =
          commitsInProject.get(commitsInProject.size() - 1); // We grab the last one (oldest)
      if (!lastCommit.getName().equals(newLastCommit.getName())) {
        metricRepository.deleteMetricsInCommit(
            lastCommit
                .getId()); // And delete all of it's metrics, as the files this commit contains are
        // about to change.
      }

      Map<String, CommitEntity> walkedCommits = new HashMap<>();

      // Get all of the files in this project and save them in a map so that we can pass it to the
      // getFiles method
      List<FileEntity> filesInProject = fileRepository.findAllinProject(projectId);
      Map<String, FileEntity> walkedFiles = new HashMap<>();
      for (FileEntity f : filesInProject) {
        walkedFiles.putIfAbsent(f.getPath(), f);
      }

      // Get the newest commit from our new commit tree and set all of it's parents and files.
      commits.sort(Comparator.comparing(Commit::getTimestamp));
      Commit newestCommit = commits.get(commits.size() - 1);
      CommitEntity commitEntity = mapCommitBaseData(newestCommit);
      getFiles(newestCommit.getTouchedFiles(), commitEntity, walkedFiles);
      commitEntity.setParents(findAndSaveParents(newestCommit, walkedCommits, walkedFiles));
      walkedCommits.putIfAbsent(commitEntity.getName(), commitEntity);

      // See if any of the commits we had before have been deleted and delete their metrics.
      for (CommitEntity commit : commitsInProject) {
        Optional<CommitEntity> existingCommit =
            walkedCommits
                .values()
                .stream()
                .filter(c -> c.getName().equals(commit.getName()))
                .findAny();
        if (!existingCommit.isPresent()) {
          metricRepository.deleteMetricsInCommit(commit.getId());
        }
      }

      // Delete all of the old commit nodes, we don't need these anymore as we'll be saving the
      // newly created tree.
      getCommitsInProjectRepository.deleteAll(commitsInProject);

      // Save the newly created tree
      saveCommitRepository.save(walkedCommits.values(), 1);
      fileRepository.save(walkedFiles.values(), 1);

      // Remove the old file nodes from the project (this is how we avoid duplicates) and add the
      // new ones.
      projectEntity.getFiles().clear();
      projectEntity.getFiles().addAll(walkedFiles.values());

      // Remove any files that don't have commits attached to them.
      fileRepository.removeFilesWithoutCommits(projectId);

      // Remove any old metrics, whose commits have been deleted.
      metricRepository.removeMetricsWithoutCommits(projectId);

      getProjectRepository.save(projectEntity, 1);
    }
  }

  /**
   * Maps a Commit object to a CommitEntity object. Does not set files or parents
   *
   * @param commit The commit to map.
   * @return A new CommitEntity object.
   */
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

  /**
   * Saves a single commit in the database , this operation can only be used to save/update basic
   * data (no parents/files).
   *
   * @param commit The commit to save/update.
   */
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

  /**
   * Recursively finds all parents of a Commit, and returns a new CommitEntity tree. Also calls
   * getFiles to set the files and relationships.
   *
   * @param commit The commit whose parents to find.
   * @param walkedCommits Commits we have already found/created. We need this to prevent creating
   *     thousands of duplicate nodes and going into endless recursion
   * @param walkedFiles Files we have already created/found for the current tree. Same purpose as
   *     the walkedCommits.
   * @return A list of fully initialized parents for the given commit.
   */
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

  /**
   * Sets the files and relationships for a given CommitEntity
   *
   * @param relationships The relationships that we have to map to DB entities.
   * @param entity The commitEntity
   * @param walkedFiles Files we have already walked. We need this to prevent endless recursion when
   *     mapping.
   */
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
      if (fileEntity.getId()
          != null) { // If the file entity already exists, check if it has any metrics and attach
        // those to the commit entity.
        for (MetricValueEntity metric :
            fileRepository.findMetricsByFileAndCommitName(fileEntity.getId(), entity.getName())) {
          entity.getMetricValues().add(metric);
          entity.setAnalyzed(true);
        }
      }
      fileToCommitRelationshipEntity.setFile(fileEntity);
    }
  }
}
