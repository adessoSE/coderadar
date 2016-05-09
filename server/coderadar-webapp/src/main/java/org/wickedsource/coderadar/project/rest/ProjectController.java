package org.wickedsource.coderadar.project.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private EntityLinks entityLinks;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectResourceAssembler projectAssembler;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ProjectResource> createProject(@Valid @RequestBody ProjectResource projectResource) {
        Project project = projectAssembler.toEntity(projectResource);
        Project savedProject = projectRepository.save(project);
        ProjectResource resultResource = projectAssembler.toResource(savedProject);
        return new ResponseEntity<>(resultResource, HttpStatus.CREATED);
    }

}
