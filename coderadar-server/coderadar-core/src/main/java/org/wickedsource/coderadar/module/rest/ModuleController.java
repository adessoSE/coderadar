package org.wickedsource.coderadar.module.rest;

import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
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
@ExposesResourceFor(Module.class)
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
  public ResponseEntity<PagedResources<ModuleResource>> listModules(
      @PageableDefault Pageable pageable,
      PagedResourcesAssembler pagedResourcesAssembler,
      @PathVariable long projectId) {
    Project project = projectVerifier.loadProjectOrThrowException(projectId);
    Page<Module> page = moduleRepository.findByProjectId(projectId, pageable);
    ModuleResourceAssembler assembler = new ModuleResourceAssembler(project);
    PagedResources<ModuleResource> pagedResources =
        pagedResourcesAssembler.toResource(page, assembler);
    return new ResponseEntity<>(pagedResources, HttpStatus.OK);
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
