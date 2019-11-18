package io.reflectoring.coderadar.graph.projectadministration.project;

import io.reflectoring.coderadar.graph.AbstractMapper;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.projectadministration.domain.Project;

public class ProjectMapper extends AbstractMapper<Project, ProjectEntity> {

  @Override
  public Project mapNodeEntity(ProjectEntity nodeEntity) {
    Project project = new Project();
    project.setId(nodeEntity.getId());
    project.setName(nodeEntity.getName());
    project.setVcsEnd(nodeEntity.getVcsEnd());
    project.setVcsStart(nodeEntity.getVcsStart());
    project.setVcsOnline(nodeEntity.isVcsOnline());
    project.setVcsUsername(nodeEntity.getVcsUsername());
    project.setVcsPassword(nodeEntity.getVcsPassword());
    project.setVcsUrl(nodeEntity.getVcsUrl());
    project.setWorkdirName(nodeEntity.getWorkdirName());
    return project;
  }

  @Override
  public ProjectEntity mapDomainObject(Project domainObject) {
    ProjectEntity project = new ProjectEntity();
    project.setId(domainObject.getId());
    project.setName(domainObject.getName());
    project.setVcsEnd(domainObject.getVcsEnd());
    project.setVcsStart(domainObject.getVcsStart());
    project.setVcsOnline(domainObject.isVcsOnline());
    project.setVcsUsername(domainObject.getVcsUsername());
    project.setVcsPassword(domainObject.getVcsPassword());
    project.setVcsUrl(domainObject.getVcsUrl());
    project.setWorkdirName(domainObject.getWorkdirName());
    project.setBeingDeleted(false);
    return project;
  }
}
