package io.reflectoring.coderadar.graph.projectadministration.project;

import io.reflectoring.coderadar.domain.Project;
import io.reflectoring.coderadar.graph.Mapper;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;

public class ProjectMapper implements Mapper<Project, ProjectEntity> {

  @Override
  public Project mapGraphObject(ProjectEntity nodeEntity) {
    Project project = new Project();
    project.setId(nodeEntity.getId());
    project.setName(nodeEntity.getName());
    project.setVcsStart(nodeEntity.getVcsStart());
    project.setVcsUsername(nodeEntity.getVcsUsername());
    project.setVcsPassword(nodeEntity.getVcsPassword());
    project.setVcsUrl(nodeEntity.getVcsUrl());
    project.setWorkdirName(nodeEntity.getWorkdirName());
    project.setDefaultBranch(nodeEntity.getDefaultBranch());
    return project;
  }

  @Override
  public ProjectEntity mapDomainObject(Project domainObject) {
    ProjectEntity project = new ProjectEntity();
    project.setName(domainObject.getName());
    project.setVcsStart(domainObject.getVcsStart());
    project.setVcsUsername(domainObject.getVcsUsername());
    project.setVcsPassword(domainObject.getVcsPassword());
    project.setVcsUrl(domainObject.getVcsUrl());
    project.setWorkdirName(domainObject.getWorkdirName());
    project.setDefaultBranch(domainObject.getDefaultBranch());
    project.setBeingDeleted(false);
    return project;
  }
}
