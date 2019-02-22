package org.wickedsource.coderadar.project.rest;

import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.core.rest.AbstractResourceAssembler;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.VcsCoordinates;

@Component
public class ProjectResourceAssembler extends AbstractResourceAssembler<Project, ProjectResource> {

  @Override
  public ProjectResource toResource(Project project) {
    ProjectResource resource = new ProjectResource();
    resource.setName(project.getName());
    if (project.getVcsCoordinates() != null) {
      resource.setVcsUrl(project.getVcsCoordinates().getUrl().toString());
      resource.setVcsUser(project.getVcsCoordinates().getUsername());
      resource.setVcsPassword(project.getVcsCoordinates().getPassword());
      resource.setStartDate(
          Jsr310Converters.DateToLocalDateConverter.INSTANCE.convert(
              project.getVcsCoordinates().getStartDate()));
      resource.setEndDate(
          Jsr310Converters.DateToLocalDateConverter.INSTANCE.convert(
              project.getVcsCoordinates().getEndDate()));
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
      vcs.setStartDate(
          Jsr310Converters.LocalDateToDateConverter.INSTANCE.convert(resource.getStartDate()));
      vcs.setEndDate(
          Jsr310Converters.LocalDateToDateConverter.INSTANCE.convert(resource.getEndDate()));
      vcs.setOnline(resource.isVcsOnline());
      entity.setVcsCoordinates(vcs);
      return entity;
    } catch (MalformedURLException e) {
      throw new IllegalStateException("Invalid URL should have been caught earlier!", e);
    }
  }
}
