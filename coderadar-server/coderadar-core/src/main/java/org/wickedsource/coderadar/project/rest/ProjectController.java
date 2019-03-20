package org.wickedsource.coderadar.project.rest;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectDeleter;
import org.wickedsource.coderadar.project.domain.ProjectRepository;

@Controller
@Transactional
@RequestMapping(path = "/projects")
public class ProjectController {

  private ProjectRepository projectRepository;

  private ProjectResourceAssembler projectAssembler;

  private ProjectVerifier projectVerifier;

  private ProjectDeleter projectDeleter;

  @Autowired
  public ProjectController(
      ProjectRepository projectRepository,
      ProjectResourceAssembler projectAssembler,
      ProjectVerifier projectVerifier,
      ProjectDeleter projectDeleter) {
    this.projectRepository = projectRepository;
    this.projectAssembler = projectAssembler;
    this.projectVerifier = projectVerifier;
    this.projectDeleter = projectDeleter;
  }

  @RequestMapping(method = RequestMethod.GET, produces = "application/hal+json")
  public ResponseEntity<List<ProjectResource>> getProjects() {
    Iterable<Project> projects = projectRepository.findAll();
    return new ResponseEntity<>(projectAssembler.toResourceList(projects), HttpStatus.OK);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<ProjectResource> getProject(@PathVariable Long id) {
    Project project = projectVerifier.loadProjectOrThrowException(id);
    ProjectResource resultResource = projectAssembler.toResource(project);
    return new ResponseEntity<>(resultResource, HttpStatus.OK);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<String> deleteProject(@PathVariable Long id) {
    projectVerifier.checkProjectExistsOrThrowException(id);
    projectDeleter.deleteProject(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.POST)
  public ResponseEntity<ProjectResource> updateProject(
      @Valid @RequestBody ProjectResource projectResource, @PathVariable Long id) {
    Project project = projectVerifier.loadProjectOrThrowException(id);
    project = projectAssembler.updateEntity(projectResource, project);
    Project savedProject = projectRepository.save(project);
    ProjectResource resultResource = projectAssembler.toResource(savedProject);
    return new ResponseEntity<>(resultResource, HttpStatus.OK);
  }
}
