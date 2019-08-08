package io.reflectoring.coderadar.graph.projectadministration.module;

import io.reflectoring.coderadar.graph.analyzer.domain.FileEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.CreateModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.ListModulesOfProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.service.CreateModuleAdapter;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.service.ProjectStatusAdapter;
import io.reflectoring.coderadar.projectadministration.ModuleAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ModulePathInvalidException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.task.TaskExecutor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Create module")
class CreateModuleAdapterTest {
  private CreateModuleRepository createModuleRepository = mock(CreateModuleRepository.class);
  private GetProjectRepository getProjectRepository = mock(GetProjectRepository.class);
  private ListModulesOfProjectRepository listModulesOfProjectRepository =
      mock(ListModulesOfProjectRepository.class);
  private final TaskExecutor taskExecutor = mock(TaskExecutor.class);
  private ProjectStatusAdapter projectStatusAdapter = mock(ProjectStatusAdapter.class);

  @Test
  @DisplayName("Should return ID when saving a module")
  void shouldReturnIdWhenSavingAModule()
      throws ModulePathInvalidException, ModuleAlreadyExistsException,
          ProjectIsBeingProcessedException {
    CreateModuleAdapter createModuleAdapter =
        new CreateModuleAdapter(createModuleRepository, getProjectRepository);

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
    when(createModuleRepository.save(any(ModuleEntity.class))).thenReturn(mockedItem);
    when(createModuleRepository.findById(anyLong())).thenReturn(java.util.Optional.of(mockedItem));

    when(getProjectRepository.findById(anyLong())).thenReturn(java.util.Optional.of(mockedProject));
    createModuleAdapter.createModule(newItem.getId(), 1L);

    // verify(createModuleRepository, times(1)).save(any());

  }
}
