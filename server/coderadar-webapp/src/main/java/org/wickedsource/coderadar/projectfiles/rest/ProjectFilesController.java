package org.wickedsource.coderadar.projectfiles.rest;

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
import org.wickedsource.coderadar.core.rest.validation.ValidationException;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;
import org.wickedsource.coderadar.projectfiles.domain.ProjectFiles;
import org.wickedsource.coderadar.projectfiles.domain.ProjectFilesRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
@ExposesResourceFor(ProjectFiles.class)
@Transactional
public class ProjectFilesController {

    private ProjectFilesRepository projectFilesRepository;

    private ProjectFilesResourceAssembler projectFilesResourceAssembler;

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectFilesController(ProjectFilesRepository projectFilesRepository, ProjectFilesResourceAssembler projectFilesResourceAssembler, ProjectRepository projectRepository) {
        this.projectFilesRepository = projectFilesRepository;
        this.projectFilesResourceAssembler = projectFilesResourceAssembler;
        this.projectRepository = projectRepository;
    }

    @RequestMapping(path = "/projects/{id}/files", method = RequestMethod.GET)
    public ResponseEntity<List<ProjectFilesResource>> getProjectFiles(@PathVariable Long projectId) {
        if (projectRepository.countById(projectId) == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<ProjectFiles> projectFilesList = projectFilesRepository.findByProjectId(projectId);
            List<ProjectFilesResource> resourceList = projectFilesResourceAssembler.toResourceList(projectFilesList);
            return new ResponseEntity<>(resourceList, HttpStatus.OK);
        }
    }

    @RequestMapping(path = "/projects/{id}/files", method = RequestMethod.POST)
    public ResponseEntity<ProjectFilesResource> addFilesToProject(@Valid @RequestBody ProjectFilesResource projectFilesResource) {
        Project project = projectRepository.findOne(projectFilesResource.getProjectId());
        if (project == null) {
            throw new ValidationException("projectId", "Project does not exist");
        } else {
            ProjectFiles projectFiles = projectFilesResourceAssembler.toEntity(projectFilesResource, project);
            ProjectFiles savedProjectFiles = projectFilesRepository.save(projectFiles);
            ProjectFilesResource resource = projectFilesResourceAssembler.toResource(savedProjectFiles);
            return new ResponseEntity<>(resource, HttpStatus.CREATED);
        }
    }


}
