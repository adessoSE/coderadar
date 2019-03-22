package org.wickedsource.coderadar.filepattern.rest;

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
import org.wickedsource.coderadar.filepattern.domain.FilePatternRepository;
import org.wickedsource.coderadar.project.rest.ProjectVerifier;
import org.wickedsource.coderadar.projectadministration.domain.FilePattern;
import org.wickedsource.coderadar.projectadministration.domain.Project;

@Controller
@Transactional
@RequestMapping(path = "/projects/{projectId}/files")
public class FilePatternController {

  private FilePatternRepository filePatternRepository;

  private ProjectVerifier projectVerifier;

  @Autowired
  public FilePatternController(
      FilePatternRepository filePatternRepository, ProjectVerifier projectVerifier) {
    this.filePatternRepository = filePatternRepository;
    this.projectVerifier = projectVerifier;
  }

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<FilePatternResource> getFilePatterns(@PathVariable Long projectId) {
    projectVerifier.checkProjectExistsOrThrowException(projectId);
    FilePatternResourceAssembler assembler = new FilePatternResourceAssembler(projectId);
    List<FilePattern> filePatternList = filePatternRepository.findByProjectId(projectId);
    FilePatternResource resource = assembler.toResource(filePatternList);
    return new ResponseEntity<>(resource, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<FilePatternResource> setFilePatterns(
      @PathVariable Long projectId, @Valid @RequestBody FilePatternResource resource) {
    Project project = projectVerifier.loadProjectOrThrowException(projectId);
    FilePatternResourceAssembler assembler = new FilePatternResourceAssembler(projectId);
    filePatternRepository.deleteByProjectId(projectId);
    List<FilePattern> filePatterns = assembler.toEntity(resource, project);
    Iterable<FilePattern> savedFilePatterns = filePatternRepository.saveAll(filePatterns);
    FilePatternResource savedResource = assembler.toResource(savedFilePatterns);
    return new ResponseEntity<>(savedResource, HttpStatus.CREATED);
  }
}
