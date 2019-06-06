package io.reflectoring.coderadar.graph.projectadministration.module;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import io.reflectoring.coderadar.graph.projectadministration.module.repository.CreateModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.service.CreateModuleService;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Create module")
class CreateModuleServiceTest {
  private CreateModuleRepository createModuleRepository = mock(CreateModuleRepository.class);

  @Test
  @DisplayName("Should return ID when saving a module")
  void shouldReturnIdWhenSavingAModule() {
    CreateModuleService createModuleService = new CreateModuleService(createModuleRepository);

    Project mockedProject = new Project();
    Module mockedItem = new Module();
    mockedItem.setId(1L);
    mockedItem.setProject(mockedProject);
    Module newItem = new Module();
    when(createModuleRepository.save(any(Module.class))).thenReturn(mockedItem);

    Long returnedId = createModuleService.createModule(newItem);

    verify(createModuleRepository, times(1)).save(newItem);
    verifyNoMoreInteractions(createModuleRepository);
    Assertions.assertEquals(new Long(1L), returnedId);
  }
}
