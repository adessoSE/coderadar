package org.wickedsource.coderadar.project.rest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wickedsource.coderadar.projectadministration.port.driver.project.CreateProjectCommand;
import org.wickedsource.coderadar.projectadministration.port.driver.project.CreateProjectUseCase;

@Controller
@Transactional
@RequestMapping(path = "/projects")
public class CreateProjectController {
  private final CreateProjectUseCase useCase;

  @Autowired
  public CreateProjectController(CreateProjectUseCase useCase) {
    this.useCase = useCase;
  }

  @PostMapping
  public ResponseEntity<ProjectResource> createProject(
      @Valid @RequestBody ProjectResource resource) {
    CreateProjectCommand command = createProjectCommand(resource);
    command = useCase.createProject(command);
    ProjectResource result = toResource(command);
    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }

  private ProjectResource toResource(CreateProjectCommand command) {
    ProjectResource resource = new ProjectResource();
    resource.setId(command.getId());
    resource.setName(command.getName());
    resource.setVcsUser(command.getVcsUsername());
    resource.setVcsPassword(command.getVcsPassword());
    resource.setVcsUrl(command.getVcsUrl().toString());
    resource.setStartDate(
        Jsr310Converters.DateToLocalDateConverter.INSTANCE.convert(command.getStart()));
    resource.setEndDate(
        Jsr310Converters.DateToLocalDateConverter.INSTANCE.convert(command.getEnd()));
    return resource;
  }

  private CreateProjectCommand createProjectCommand(ProjectResource resource) {
    String workdir = UUID.randomUUID().toString();
    URL vcsUrl = createURL(resource);
    Date start = null;
    Date end = null;

    if (resource.getStartDate() != null) {
      start = Jsr310Converters.LocalDateToDateConverter.INSTANCE.convert(resource.getStartDate());
    }

    if (resource.getEndDate() != null) {
      end = Jsr310Converters.LocalDateToDateConverter.INSTANCE.convert(resource.getEndDate());
    }

    return new CreateProjectCommand(
        null,
        resource.getName(),
        workdir,
        resource.getVcsUser(),
        resource.getVcsPassword(),
        vcsUrl,
        resource.isVcsOnline(),
        start,
        end);
  }

  private URL createURL(ProjectResource resource) {
    try {
      return new URL(resource.getVcsUrl());
    } catch (MalformedURLException e) {
      throw new IllegalStateException("Invalid URL should have been caught earlier!", e);
    }
  }
}
