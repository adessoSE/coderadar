package org.wickedsource.coderadar.module.rest;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wickedsource.coderadar.core.common.ResourceNotFoundException;
import org.wickedsource.coderadar.core.rest.validation.UserException;
import org.wickedsource.coderadar.module.domain.Module;
import org.wickedsource.coderadar.module.domain.ModuleRepository;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.rest.ProjectVerifier;

@Controller
@Transactional
@RequestMapping(path = "/projects/{projectId}/modules")
public class ModuleController {

  private ProjectVerifier projectVerifier;

  private ModuleRepository moduleRepository;

  private ModuleAssociationService moduleAssociationService;

  @Autowired
  public ModuleController(
      ProjectVerifier projectVerifier,
      ModuleRepository moduleRepository,
      ModuleAssociationService moduleAssociationService) {
    this.projectVerifier = projectVerifier;
    this.moduleRepository = moduleRepository;
    this.moduleAssociationService = moduleAssociationService;
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<ModuleResource> createModule(
      @Valid @RequestBody ModuleResource moduleResource, @PathVariable Long projectId) {
    Project project = projectVerifier.loadProjectOrThrowException(projectId);
    if (moduleRepository.countByProjectIdAndPath(projectId, moduleResource.getModulePath()) > 0) {
      throw new UserException(
          String.format(
              "Module with path '%s' already exists for this project!",
              moduleResource.getModulePath()));
    }
    ModuleResourceAssembler assembler = new ModuleResourceAssembler(project);
    Module module = new Module();
    assembler.updateEntity(module, moduleResource);
    module = moduleRepository.save(module);
    moduleAssociationService.associate(module);
    return new ResponseEntity<>(assembler.toResource(module), HttpStatus.CREATED);
  }

  @RequestMapping(method = RequestMethod.GET, path = "/{moduleId}")
  public ResponseEntity<ModuleResource> getModule(
      @PathVariable Long moduleId, @PathVariable Long projectId) {
    Project project = projectVerifier.loadProjectOrThrowException(projectId);
    Module module = moduleRepository.findByIdAndProjectId(moduleId, projectId);
    if (module == null) {
      throw new ResourceNotFoundException();
    }
    ModuleResourceAssembler assembler = new ModuleResourceAssembler(project);
    ModuleResource resource = assembler.toResource(module);
    return new ResponseEntity<>(resource, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST, path = "/{moduleId}")
  public ResponseEntity<ModuleResource> updateModule(
      @Valid @RequestBody ModuleResource moduleResource,
      @PathVariable Long moduleId,
      @PathVariable Long projectId) {
    Project project = projectVerifier.loadProjectOrThrowException(projectId);
    Module module = moduleRepository.findByIdAndProjectId(moduleId, projectId);
    if (module == null) {
      throw new ResourceNotFoundException();
    }
    ModuleResourceAssembler assembler = new ModuleResourceAssembler(project);
    module = assembler.updateEntity(module, moduleResource);
    moduleAssociationService.reassociate(module);
    return new ResponseEntity<>(assembler.toResource(module), HttpStatus.OK);
  }

  @SuppressWarnings("unchecked")
  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<List<ModuleResource>> listModules(
      @PathVariable long projectId, Pageable pageable) {
    Project project = projectVerifier.loadProjectOrThrowException(projectId);
    List<Module> modules = moduleRepository.findByProjectId(projectId);
    ModuleResourceAssembler assembler = new ModuleResourceAssembler(project);
    List<ModuleResource> resources = assembler.toResourceList(modules);
    return new ResponseEntity<>(resources, HttpStatus.OK);
  }

  @RequestMapping(path = "/{moduleId}", method = RequestMethod.DELETE)
  public ResponseEntity<String> deleteModule(
      @PathVariable Long moduleId, @PathVariable Long projectId) {
    projectVerifier.checkProjectExistsOrThrowException(projectId);
    Optional<Module> module = moduleRepository.findById(moduleId);
    if (!module.isPresent()) {
      throw new ResourceNotFoundException();
    }
    moduleAssociationService.disassociate(module.get());
    moduleRepository.delete(module.get());
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
