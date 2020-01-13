package io.reflectoring.coderadar.graph.projectadministration.project.adapter;

import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import io.reflectoring.coderadar.projectadministration.domain.Commit;

public class CommitBaseDataMapper {

  private CommitBaseDataMapper() {}

  public static Commit mapCommitEntity(CommitEntity entity) {
    Commit commit = new Commit();
    commit.setId(entity.getId());
    commit.setName(entity.getName());
    commit.setAnalyzed(entity.isAnalyzed());
    commit.setAuthor(entity.getAuthor());
    commit.setComment(entity.getComment());
    commit.setTimestamp(entity.getTimestamp());
    return commit;
  }

  public static CommitEntity mapCommit(Commit commit) {
    CommitEntity commitEntity = new CommitEntity();
    commitEntity.setId(commit.getId());
    commitEntity.setName(commit.getName());
    commitEntity.setAnalyzed(commit.isAnalyzed());
    commitEntity.setAuthor(commit.getAuthor());
    commitEntity.setComment(commit.getComment());
    commitEntity.setTimestamp(commit.getTimestamp());
    return commitEntity;
  }
}
