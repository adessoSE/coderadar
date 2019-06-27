package io.reflectoring.coderadar.graph.analyzer.service;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.graph.analyzer.domain.CommitEntity;
import io.reflectoring.coderadar.graph.analyzer.repository.SaveCommitRepository;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveCommitAdapter implements SaveCommitPort {
  private final SaveCommitRepository saveCommitRepository;

  @Autowired
  public SaveCommitAdapter(SaveCommitRepository saveCommitRepository) {
    this.saveCommitRepository = saveCommitRepository;
  }

  @Override
  public void saveCommit(Commit commit) {
    // TODO
  }

  @Override
  public void saveCommits(List<Commit> commits) {
    if (!commits.isEmpty()) {
      commits.sort(Comparator.comparing(Commit::getTimestamp));
      CommitEntity commitEntity = new CommitEntity();
      Commit newestCommit = commits.get(commits.size() - 1);
      commitEntity.setAnalyzed(newestCommit.isAnalyzed());
      commitEntity.setAuthor(newestCommit.getAuthor());
      commitEntity.setComment(newestCommit.getComment());
      commitEntity.setMerged(newestCommit.isMerged());
      commitEntity.setName(newestCommit.getName());
      commitEntity.setTimestamp(newestCommit.getTimestamp());
      commitEntity.setParents(findAndSaveParents(newestCommit, new HashMap<>()));
      commitEntity.getParents().forEach(commitEntity1 -> commitEntity1.getParents().clear());
      saveCommitRepository.save(commitEntity, 1);
    }
  }

  private List<CommitEntity> findAndSaveParents(
      Commit commit, HashMap<String, CommitEntity> walkedCommits) {

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
        walkedCommits.put(c.getName(), commitEntity);
        commitEntity.setParents(findAndSaveParents(c, walkedCommits));
        parents.add(commitEntity);
      }
    }
    parents.forEach(
        commit1 -> {
          commit1.getParents().forEach(commit2 -> commit2.getParents().clear());
          // Clear the parents of each parent commit and save it. This
          // keeps Neo4j happy.
        });
    saveCommitRepository.save(parents, 1);
    return parents;
  }
}
