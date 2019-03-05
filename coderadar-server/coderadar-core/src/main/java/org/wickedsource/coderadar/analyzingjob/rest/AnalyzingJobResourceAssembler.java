package org.wickedsource.coderadar.analyzingjob.rest;

import org.wickedsource.coderadar.analyzingjob.domain.AnalyzingJob;
import org.wickedsource.coderadar.core.rest.AbstractResourceAssembler;
import org.wickedsource.coderadar.project.domain.Project;

public class AnalyzingJobResourceAssembler
    extends AbstractResourceAssembler<AnalyzingJob, AnalyzingJobResource> {

  private Long projectId;

  AnalyzingJobResourceAssembler(Long projectId) {
    this.projectId = projectId;
  }

  @Override
  public AnalyzingJobResource toResource(AnalyzingJob entity) {
    return new AnalyzingJobResource(entity.getFromDate(), entity.isActive());
  }

  public AnalyzingJob updateEntity(
      AnalyzingJob entity, AnalyzingJobResource resource, Project project) {
    entity.setProject(project);
    entity.setActive(resource.getActive());
    entity.setFromDate(resource.getFromDate());
    return entity;
  }
}
