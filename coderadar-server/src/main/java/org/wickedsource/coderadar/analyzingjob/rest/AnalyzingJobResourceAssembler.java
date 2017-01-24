package org.wickedsource.coderadar.analyzingjob.rest;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.wickedsource.coderadar.analyzingjob.domain.AnalyzingJob;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.rest.ProjectController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class AnalyzingJobResourceAssembler
    extends ResourceAssemblerSupport<AnalyzingJob, AnalyzingJobResource> {

  private Long projectId;

  AnalyzingJobResourceAssembler(Long projectId) {
    super(AnalyzingJobController.class, AnalyzingJobResource.class);
    this.projectId = projectId;
  }

  @Override
  public AnalyzingJobResource toResource(AnalyzingJob entity) {
    AnalyzingJobResource resource =
        new AnalyzingJobResource(entity.getFromDate(), entity.isActive());
    resource.add(
        linkTo(methodOn(AnalyzingJobController.class).getAnalyzingJob(projectId))
            .withRel("self"));
    resource.add(
        linkTo(methodOn(ProjectController.class).getProject(projectId)).withRel("project"));
    return resource;
  }

  public AnalyzingJob updateEntity(
          AnalyzingJob entity, AnalyzingJobResource resource, Project project) {
    entity.setProject(project);
    entity.setActive(resource.isActive());
    entity.setFromDate(resource.getFromDate());
    return entity;
  }
}
