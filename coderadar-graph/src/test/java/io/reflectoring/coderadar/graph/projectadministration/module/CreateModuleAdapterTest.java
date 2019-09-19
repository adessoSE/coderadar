package io.reflectoring.coderadar.graph.projectadministration.module;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.graph.analyzer.domain.FileEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.ModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.service.CreateModuleAdapter;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.service.ProjectStatusAdapter;
import io.reflectoring.coderadar.projectadministration.ModuleAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ModulePathInvalidException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.neo4j.ogm.session.Session;
import org.springframework.core.task.TaskExecutor;

@DisplayName("Create module")
class CreateModuleAdapterTest {
  private ModuleRepository moduleRepository = mock(ModuleRepository.class);
  private ProjectRepository projectRepository = mock(ProjectRepository.class);
  private final TaskExecutor taskExecutor = mock(TaskExecutor.class);
  private ProjectStatusAdapter projectStatusAdapter = mock(ProjectStatusAdapter.class);
  private Session session = mock(Session.class);

  @Test
  @DisplayName("Should return ID when saving a module")
  void shouldReturnIdWhenSavingAModule()
      throws ModulePathInvalidException, ModuleAlreadyExistsException,
          ProjectIsBeingProcessedException {
    CreateModuleAdapter createModuleAdapter =
        new CreateModuleAdapter(moduleRepository, projectRepository, session);

    ProjectEntity mockedProject = new ProjectEntity();
    FileEntity mockedFile = new FileEntity();
    mockedFile.setPath("src/Main.java");
    mockedProject.getFiles().add(mockedFile);
    ModuleEntity mockedItem = new ModuleEntity();
    mockedItem.setId(1L);
    mockedItem.setPath("src/");
    mockedItem.setProject(mockedProject);
    Module newItem = new Module();
    newItem.setId(1L);
    newItem.setPath("src/");
    when(moduleRepository.save(any(ModuleEntity.class))).thenReturn(mockedItem);
    when(moduleRepository.findById(anyLong())).thenReturn(java.util.Optional.of(mockedItem));

    when(projectRepository.findById(anyLong())).thenReturn(java.util.Optional.of(mockedProject));
    createModuleAdapter.createModule(newItem.getId(), 1L);

    // verify(createModuleRepository, times(1)).save(any());

  }
}
