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
@RequestMapping(path = "/projects/{projectId}/files")
public class ProjectFilesController {

    private ProjectFilesRepository projectFilesRepository;

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectFilesController(ProjectFilesRepository projectFilesRepository, ProjectRepository projectRepository) {
        this.projectFilesRepository = projectFilesRepository;
        this.projectRepository = projectRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ProjectFilesResource> getProjectFiles(@PathVariable Long projectId) {
        if (projectRepository.countById(projectId) == 0) {
            throw new ValidationException("projectId", "Project does not exist");
        } else {
            ProjectFilesResourceAssembler assembler = new ProjectFilesResourceAssembler(projectId);
            List<ProjectFiles> projectFilesList = projectFilesRepository.findByProjectId(projectId);
            ProjectFilesResource resource = assembler.toResource(projectFilesList);
            return new ResponseEntity<>(resource, HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ProjectFilesResource> setProjectFiles(@PathVariable Long projectId, @Valid @RequestBody ProjectFilesResource resource) {
        Project project = projectRepository.findOne(projectId);
        if (project == null) {
            throw new ValidationException("projectId", "Project does not exist");
        } else {
            ProjectFilesResourceAssembler assembler = new ProjectFilesResourceAssembler(projectId);
            projectFilesRepository.deleteByProjectId(projectId);
            List<ProjectFiles> projectFiles = assembler.toEntity(resource, project);
            Iterable<ProjectFiles> savedProjectFiles = projectFilesRepository.save(projectFiles);
            ProjectFilesResource savedResource = assembler.toResource(savedProjectFiles);
            return new ResponseEntity<>(savedResource, HttpStatus.CREATED);
        }
    }

}
