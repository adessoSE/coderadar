package io.reflectoring.coderadar.graph.projectadministration.project.adapter;

import io.reflectoring.coderadar.graph.Mapper;
import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import io.reflectoring.coderadar.projectadministration.domain.Commit;

public class CommitBaseDataMapper implements Mapper<Commit, CommitEntity> {

  public Commit mapGraphObject(CommitEntity entity) {
    Commit commit = new Commit();
    commit.setId(entity.getId());
    commit.setName(entity.getName());
    commit.setAnalyzed(entity.isAnalyzed());
    commit.setAuthor(entity.getAuthor());
    commit.setComment(entity.getComment());
    commit.setTimestamp(entity.getTimestamp());
    commit.setAuthorEmail(entity.getAuthorEmail());
    return commit;
  }

  public CommitEntity mapDomainObject(Commit commit) {
    CommitEntity commitEntity = new CommitEntity();
    commitEntity.setName(commit.getName());
    commitEntity.setAnalyzed(commit.isAnalyzed());
    commitEntity.setAuthor(commit.getAuthor());
    commitEntity.setComment(commit.getComment());
    commitEntity.setTimestamp(commit.getTimestamp());
    commitEntity.setAuthorEmail(commit.getAuthorEmail());
    return commitEntity;
  }
}
