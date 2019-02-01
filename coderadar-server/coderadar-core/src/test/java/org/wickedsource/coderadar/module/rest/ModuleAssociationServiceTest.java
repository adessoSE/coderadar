package org.wickedsource.coderadar.module.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.Modules.MODULE_ASSOCIATION;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.wickedsource.coderadar.commit.domain.*;
import org.wickedsource.coderadar.commit.event.CommitToFileAssociatedEvent;
import org.wickedsource.coderadar.file.domain.File;
import org.wickedsource.coderadar.file.domain.FileRepository;
import org.wickedsource.coderadar.module.domain.Module;
import org.wickedsource.coderadar.module.domain.ModuleRepository;
import org.wickedsource.coderadar.testframework.template.IntegrationTestTemplate;

@Transactional
public class ModuleAssociationServiceTest extends IntegrationTestTemplate {

  @Autowired private ModuleRepository moduleRepository;

  @Autowired private CommitToFileAssociationRepository commitToFileAssociationRepository;

  @Autowired private ModuleAssociationService moduleAssociationService;

  @Autowired private CommitRepository commitRepository;

  @Autowired private FileRepository fileRepository;

  @Test
  @DatabaseSetup(MODULE_ASSOCIATION)
  public void associateModuleToFiles() {
    Module serverModule = moduleRepository.findByIdAndProjectId(5L, 1L); // module "server"
    Module server2Module = moduleRepository.findByIdAndProjectId(6L, 1L); // module "server2"

    moduleAssociationService.associate(serverModule);
    assertThat(moduleRepository.findFilesByModuleId(serverModule.getId())).hasSize(4);
    assertThat(moduleRepository.findFilesByModuleId(server2Module.getId())).hasSize(0);

    moduleAssociationService.associate(server2Module);
    assertThat(moduleRepository.findFilesByModuleId(serverModule.getId())).hasSize(4);
    assertThat(moduleRepository.findFilesByModuleId(server2Module.getId())).hasSize(1);

    moduleAssociationService.disassociate(serverModule);
    assertThat(moduleRepository.findFilesByModuleId(serverModule.getId())).isEmpty();
    assertThat(moduleRepository.findFilesByModuleId(server2Module.getId())).isNotEmpty();

    moduleAssociationService.disassociate(server2Module);
    assertThat(moduleRepository.findFilesByModuleId(server2Module.getId())).isEmpty();
    assertThat(moduleRepository.findFilesByModuleId(server2Module.getId())).isEmpty();
  }

  @Test
  @DatabaseSetup(MODULE_ASSOCIATION)
  public void associateFileToModules() {
    Optional<Commit> commit = commitRepository.findById(1L);
    Optional<File> file = fileRepository.findById(4L);
    Assertions.assertTrue(commit.isPresent());
    Assertions.assertTrue(file.isPresent());

    Optional<CommitToFileAssociation> ctf =
        commitToFileAssociationRepository.findById(new CommitToFileId(commit.get(), file.get()));
    Assertions.assertTrue(ctf.isPresent());

    CommitToFileAssociatedEvent event = new CommitToFileAssociatedEvent(ctf.get());
    moduleAssociationService.associate(event);

    // the file should be associated with modules "server" (5), "server/module1" (1) and
    // "server/module1/submodule" (4)
    assertThat(moduleRepository.findFilesByModuleId(5L)).hasSize(1);
    assertThat(moduleRepository.findFilesByModuleId(1L)).hasSize(1);
    assertThat(moduleRepository.findFilesByModuleId(4L)).hasSize(1);

    assertThat(moduleRepository.findFilesByModuleId(2L)).isEmpty();
    assertThat(moduleRepository.findFilesByModuleId(3L)).isEmpty();
    assertThat(moduleRepository.findFilesByModuleId(6L)).isEmpty();
  }

  @Test
  @DatabaseSetup(MODULE_ASSOCIATION)
  public void associateFileToModulesWithSimilarNames() {
    Optional<Commit> commit = commitRepository.findById(1L);
    Optional<File> file = fileRepository.findById(6L);
    Assertions.assertTrue(commit.isPresent());
    Assertions.assertTrue(file.isPresent());

    Optional<CommitToFileAssociation> ctf =
        commitToFileAssociationRepository.findById(new CommitToFileId(commit.get(), file.get()));
    Assertions.assertTrue(ctf.isPresent());

    CommitToFileAssociatedEvent event = new CommitToFileAssociatedEvent(ctf.get());
    moduleAssociationService.associate(event);

    // the file should be associated with modules "server2" only and NOT with module "server"
    assertThat(moduleRepository.findFilesByModuleId(5L)).hasSize(0);
    assertThat(moduleRepository.findFilesByModuleId(6L)).hasSize(1);
  }
}
