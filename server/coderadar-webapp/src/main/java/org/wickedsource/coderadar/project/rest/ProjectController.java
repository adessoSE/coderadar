package org.wickedsource.coderadar.project.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;

import javax.validation.Valid;


@Controller
@ExposesResourceFor(Project.class)
@RequestMapping("/projects")
@Transactional
public class ProjectController {

    private ProjectRepository projectRepository;

    private ProjectResourceAssembler projectAssembler;

    @Autowired
    public ProjectController(ProjectRepository projectRepository, ProjectResourceAssembler projectAssembler) {
        this.projectRepository = projectRepository;
        this.projectAssembler = projectAssembler;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ProjectResource> createProject(@Valid @RequestBody ProjectResource projectResource) {
        Project project = projectAssembler.toEntity(projectResource);
        Project savedProject = projectRepository.save(project);
        ProjectResource resultResource = projectAssembler.toResource(savedProject);
        return new ResponseEntity<>(resultResource, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProjectResource> getProject(@PathVariable Long id) {
        Project project = projectRepository.findOne(id);
        if (project != null) {
            ProjectResource resultResource = projectAssembler.toResource(project);
            return new ResponseEntity<>(resultResource, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    public ProjectRepository getProjectRepository() {
        return projectRepository;
    }

    public ProjectResourceAssembler getProjectAssembler() {
        return projectAssembler;
    }
}
