package org.wickedsource.coderadar.analyzer.rest;

import org.eclipse.jgit.api.Git;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wickedsource.coderadar.analyzer.levelizedStructureMap.DependencyTreeService;
import org.wickedsource.coderadar.analyzer.service.AnalyzerPluginRegistry;
import org.wickedsource.coderadar.job.LocalGitRepositoryManager;
import org.wickedsource.coderadar.project.domain.ProjectRepository;
import org.wickedsource.coderadar.project.rest.ProjectResource;
import org.wickedsource.coderadar.project.rest.ProjectResourceAssembler;
import org.wickedsource.coderadar.project.rest.ProjectVerifier;

import java.util.List;

@Controller
@Transactional
@RequestMapping(path = "/analyzers")
public class AnalyzerController {

  private AnalyzerPluginRegistry analyzerRegistry;
  private DependencyTreeService dependencyTreeService;
  private ProjectVerifier projectVerifier;
  private LocalGitRepositoryManager gitRepositoryManager;
  private final ProjectResourceAssembler projectAssembler;
  private final ProjectRepository projectRepository;

  @Autowired
  public AnalyzerController(AnalyzerPluginRegistry analyzerRegistry, DependencyTreeService dependencyTreeService, ProjectVerifier projectVerifier, LocalGitRepositoryManager gitRepositoryManager, ProjectResourceAssembler projectAssembler, ProjectRepository projectRepository) {
    this.analyzerRegistry = analyzerRegistry;
    this.dependencyTreeService = dependencyTreeService;
    this.projectVerifier = projectVerifier;
    this.gitRepositoryManager = gitRepositoryManager;
    this.projectAssembler = projectAssembler;
    this.projectRepository = projectRepository;
  }

  @SuppressWarnings("unchecked")
  @RequestMapping(method = RequestMethod.GET, produces = "application/hal+json")
  public ResponseEntity<List<AnalyzerResource>> listAnalyzers() {
    List<String> analyzerPage = analyzerRegistry.getAvailableAnalyzers();
    AnalyzerResourceAssembler assembler = new AnalyzerResourceAssembler();
    return new ResponseEntity<>(assembler.toResourceList(analyzerPage), HttpStatus.OK);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{projectId}/strucutreMap/{commitName}")
  public ResponseEntity<Object> getDependencyTree(@PathVariable("projectId") Long projectId, @PathVariable("commitName") String commitName) {
    Git git = new Git(gitRepositoryManager.getLocalGitRepository(projectId).getRepository());
    ProjectResource resource = projectAssembler.toResource(projectRepository.findById(projectId).get());
    return ResponseEntity.ok(dependencyTreeService.getDependencyTree(git.getRepository(), commitName, "org/wickedsource/coderadar", resource.getName()));
  }
}
