package io.reflectoring.coderadar.graph.projectadministration.module;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.CreateModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.service.CreateModuleAdapter;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Create module")
class CreateModuleAdapterTest {
  private CreateModuleRepository createModuleRepository = mock(CreateModuleRepository.class);
  private GetProjectRepository getProjectRepository = mock(GetProjectRepository.class);

  @Test
  @DisplayName("Should return ID when saving a module")
  void shouldReturnIdWhenSavingAModule() {
    CreateModuleAdapter createModuleAdapter =
        new CreateModuleAdapter(createModuleRepository, getProjectRepository);

    ProjectEntity mockedProject = new ProjectEntity();
    ModuleEntity mockedItem = new ModuleEntity();
    mockedItem.setId(1L);
    mockedItem.setPath("src/");
    mockedItem.setProject(mockedProject);
    Module newItem = new Module();
    newItem.setPath("src/");
    when(createModuleRepository.save(any(ModuleEntity.class))).thenReturn(mockedItem);
    when(createModuleRepository.findById(anyLong())).thenReturn(java.util.Optional.of(mockedItem));

    when(getProjectRepository.findById(anyLong()))
        .thenReturn(java.util.Optional.of(new ProjectEntity()));
    Long returnedId = createModuleAdapter.createModule(newItem, 1L);

    verify(createModuleRepository, times(1)).save(any());
    verifyNoMoreInteractions(createModuleRepository);
    Assertions.assertEquals(new Long(1L), returnedId);
  }
}
