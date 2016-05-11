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
import java.util.List;


@Controller
@ExposesResourceFor(Project.class)
@Transactional
public class ProjectController {

    private ProjectRepository projectRepository;

    private ProjectResourceAssembler projectAssembler;

    @Autowired
    public ProjectController(ProjectRepository projectRepository, ProjectResourceAssembler projectAssembler) {
        this.projectRepository = projectRepository;
        this.projectAssembler = projectAssembler;
    }

    @RequestMapping(path = "/projects", method = RequestMethod.GET)
    public ResponseEntity<List<ProjectResource>> getProjects() {
        Iterable<Project> projects = projectRepository.findAll();
        return new ResponseEntity<>(projectAssembler.toResourceList(projects), HttpStatus.OK);
    }

    @RequestMapping(path = "/projects", method = RequestMethod.POST)
    public ResponseEntity<ProjectResource> createProject(@Valid @RequestBody ProjectResource projectResource) {
        Project project = projectAssembler.toEntity(projectResource);
        Project savedProject = projectRepository.save(project);
        ProjectResource resultResource = projectAssembler.toResource(savedProject);
        return new ResponseEntity<>(resultResource, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/projects/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProjectResource> getProject(@PathVariable Long id) {
        Project project = projectRepository.findOne(id);
        if (project != null) {
            ProjectResource resultResource = projectAssembler.toResource(project);
            return new ResponseEntity<>(resultResource, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/projects/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteProject(@PathVariable Long id) {
        projectRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/projects/{id}", method = RequestMethod.POST)
    public ResponseEntity<ProjectResource> updateProject(@Valid @RequestBody ProjectResource projectResource, @PathVariable Long id) {
        Project project = projectAssembler.toEntity(projectResource);
        project.setId(id);
        Project savedProject = projectRepository.save(project);
        ProjectResource resultResource = projectAssembler.toResource(savedProject);
        return new ResponseEntity<>(resultResource, HttpStatus.CREATED);
    }

}
