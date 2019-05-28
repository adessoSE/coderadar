package io.reflectoring.coderadar.graph.projectadministration.module;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.core.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.GetModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.UpdateModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.service.UpdateModuleService;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Update module")
class UpdateModuleServiceTest {
  private UpdateModuleRepository updateModuleRepository = mock(UpdateModuleRepository.class);

  private GetModuleRepository getModuleRepository = mock(GetModuleRepository.class);

  private UpdateModuleService updateModuleService;

  @BeforeEach
  void setUp() {
    updateModuleService = new UpdateModuleService(getModuleRepository, updateModuleRepository);
  }

  @Test
  @DisplayName("Should throw exception when a module with the passing ID doesn't exists")
  void shouldThrowExceptionWhenAModuleWithThePassingIdDoesntExists() {
    Assertions.assertThrows(
        ModuleNotFoundException.class, () -> updateModuleService.updateModule(new Module()));
  }

  @Test
  @DisplayName("Should update project when a module with the passing ID exists")
  void shouldUpdateProjectWhenAModuleWithThePassingIdExists() {
    Module mockedOldItem = new Module();
    mockedOldItem.setId(1L);
    mockedOldItem.setPath("/dev/null");
    when(getModuleRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedOldItem));

    Module mockedItem = new Module();
    mockedItem.setId(1L);
    mockedItem.setPath("/opt");
    when(updateModuleRepository.save(any(Module.class))).thenReturn(mockedItem);

    Module project = new Module();
    project.setId(1L);
    project.setPath("/opt");
    updateModuleService.updateModule(project);

    verify(updateModuleRepository, times(1)).save(mockedItem);
    Assertions.assertNotEquals(mockedOldItem, project);
  }
}
