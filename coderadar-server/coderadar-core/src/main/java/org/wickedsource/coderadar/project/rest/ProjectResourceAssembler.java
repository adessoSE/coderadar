package org.wickedsource.coderadar.project.rest;

import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.core.rest.AbstractResourceAssembler;
import org.wickedsource.coderadar.project.domain.VcsCoordinates;
import org.wickedsource.coderadar.projectadministration.domain.Project;

@Component
public class ProjectResourceAssembler extends AbstractResourceAssembler<Project, ProjectResource> {

  @Override
  public ProjectResource toResource(Project project) {
    ProjectResource resource = new ProjectResource();
    resource.setName(project.getName());
    resource.setId(project.getId());
    if (project.getVcsCoordinates() != null) {
      resource.setVcsUrl(project.getVcsCoordinates().getUrl().toString());
      resource.setVcsUser(project.getVcsCoordinates().getUsername());
      resource.setVcsPassword(project.getVcsCoordinates().getPassword());

      // Check if dates are null
      if (project.getVcsCoordinates().getStartDate() == null) {
        resource.setStartDate(null);
      } else {
        resource.setStartDate(
            Jsr310Converters.DateToLocalDateConverter.INSTANCE.convert(
                project.getVcsCoordinates().getStartDate()));
      }

      if (project.getVcsCoordinates().getEndDate() == null) {
        resource.setEndDate(null);
      } else {
        resource.setEndDate(
            Jsr310Converters.DateToLocalDateConverter.INSTANCE.convert(
                project.getVcsCoordinates().getEndDate()));
      }

      resource.setVcsOnline(project.getVcsCoordinates().isOnline());
    }
    return resource;
  }

  Project updateEntity(ProjectResource resource, Project entity) {
    try {
      entity.setName(resource.getName());
      VcsCoordinates vcs = new VcsCoordinates(new URL(resource.getVcsUrl()));
      vcs.setUsername(resource.getVcsUser());
      vcs.setPassword(resource.getVcsPassword());

      // Check if dates in resource are null
      if (resource.getStartDate() == null) {
        vcs.setStartDate(null);
      } else {
        vcs.setStartDate(
            Jsr310Converters.LocalDateToDateConverter.INSTANCE.convert(resource.getStartDate()));
      }

      if (resource.getEndDate() == null) {
        vcs.setEndDate(null);
      } else {
        vcs.setEndDate(
            Jsr310Converters.LocalDateToDateConverter.INSTANCE.convert(resource.getEndDate()));
      }

      vcs.setOnline(resource.isVcsOnline());
      entity.setVcsCoordinates(vcs);
      return entity;
    } catch (MalformedURLException e) {
      throw new IllegalStateException("Invalid URL should have been caught earlier!", e);
    }
  }
}
