package org.wickedsource.coderadar.filepattern.rest;

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
import org.wickedsource.coderadar.filepattern.domain.FilePattern;
import org.wickedsource.coderadar.filepattern.domain.FilePatternRepository;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
@ExposesResourceFor(FilePattern.class)
@Transactional
@RequestMapping(path = "/projects/{projectId}/files")
public class FilePatternController {

    private FilePatternRepository filePatternRepository;

    private ProjectRepository projectRepository;

    @Autowired
    public FilePatternController(FilePatternRepository filePatternRepository, ProjectRepository projectRepository) {
        this.filePatternRepository = filePatternRepository;
        this.projectRepository = projectRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<FilePatternResource> getProjectFiles(@PathVariable Long projectId) {
        if (projectRepository.countById(projectId) == 0) {
            throw new ValidationException("projectId", "Project does not exist");
        } else {
            FilePatternResourceAssembler assembler = new FilePatternResourceAssembler(projectId);
            List<FilePattern> filePatternList = filePatternRepository.findByProjectId(projectId);
            FilePatternResource resource = assembler.toResource(filePatternList);
            return new ResponseEntity<>(resource, HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<FilePatternResource> setProjectFiles(@PathVariable Long projectId, @Valid @RequestBody FilePatternResource resource) {
        Project project = projectRepository.findOne(projectId);
        if (project == null) {
            throw new ValidationException("projectId", "Project does not exist");
        } else {
            FilePatternResourceAssembler assembler = new FilePatternResourceAssembler(projectId);
            filePatternRepository.deleteByProjectId(projectId);
            List<FilePattern> filePatterns = assembler.toEntity(resource, project);
            Iterable<FilePattern> savedFilePatterns = filePatternRepository.save(filePatterns);
            FilePatternResource savedResource = assembler.toResource(savedFilePatterns);
            return new ResponseEntity<>(savedResource, HttpStatus.CREATED);
        }
    }

}
