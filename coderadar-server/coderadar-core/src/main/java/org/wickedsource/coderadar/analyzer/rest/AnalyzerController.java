package org.wickedsource.coderadar.analyzer.rest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.wickedsource.coderadar.analyzer.levelizedStructureMap.DependencyTreeService;
import org.wickedsource.coderadar.analyzer.service.AnalyzerPluginRegistry;
import org.wickedsource.coderadar.job.LocalGitRepositoryManager;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.rest.ProjectVerifier;

@Controller
@Transactional
@RequestMapping(path = "/analyzers")
public class AnalyzerController {

  private AnalyzerPluginRegistry analyzerRegistry;
  private DependencyTreeService dependencyTreeService;
  private ProjectVerifier projectVerifier;
  private LocalGitRepositoryManager gitRepositoryManager;

  @Autowired
  public AnalyzerController(AnalyzerPluginRegistry analyzerRegistry, DependencyTreeService dependencyTreeService, ProjectVerifier projectVerifier, LocalGitRepositoryManager gitRepositoryManager) {
    this.analyzerRegistry = analyzerRegistry;
    this.dependencyTreeService = dependencyTreeService;
    this.projectVerifier = projectVerifier;
    this.gitRepositoryManager = gitRepositoryManager;
  }

  @SuppressWarnings("unchecked")
  @RequestMapping(method = RequestMethod.GET, produces = "application/hal+json")
  public ResponseEntity<List<AnalyzerResource>> listAnalyzers() {
    List<String> analyzerPage = analyzerRegistry.getAvailableAnalyzers();
    AnalyzerResourceAssembler assembler = new AnalyzerResourceAssembler();
    return new ResponseEntity<>(assembler.toResourceList(analyzerPage), HttpStatus.OK);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{projectId}/strucutreMap/{commitName}")
  public ResponseEntity<String> getDependencyTree(@PathVariable("projectId") Long projectId, @PathVariable("commitName") String commitName) {
    Repository projectDir = gitRepositoryManager.getLocalGitRepository(projectId).getRepository();

    try (RevWalk revWalk = new RevWalk(projectDir)) {
      ObjectId commitId = ObjectId.fromString(commitName);
      RevCommit commit = revWalk.parseCommit(commitId);
      try (TreeWalk treeWalk = new TreeWalk(projectDir)) {
        treeWalk.addTree(commit.getTree());
        treeWalk.setRecursive(false);
        List<File> files = new ArrayList<>();
        while (treeWalk.next()) {
          File f = new File(projectDir.getWorkTree().getPath() + "/" + treeWalk.getNameString());
          System.out.println(f.getPath());
          files.add(f);
        }
      }
      return ResponseEntity.ok(dependencyTreeService.getDependencyTree(projectDir.getWorkTree(), "org/wickedsource/coderadar"));
    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.ok(e.getMessage());
    }
  }
}
