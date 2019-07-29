package io.reflectoring.coderadar.graph.projectadministration.module;

import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.GetModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.UpdateModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.service.UpdateModuleAdapter;
import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Update module")
class UpdateModuleAdapterTest {
  private UpdateModuleRepository updateModuleRepository = mock(UpdateModuleRepository.class);

  private GetModuleRepository getModuleRepository = mock(GetModuleRepository.class);

  private UpdateModuleAdapter updateModuleAdapter;

  @BeforeEach
  void setUp() {
    updateModuleAdapter = new UpdateModuleAdapter(getModuleRepository, updateModuleRepository);
  }

  @Test
  @DisplayName("Should throw exception when a module with the passing ID doesn't exists")
  void shouldThrowExceptionWhenAModuleWithThePassingIdDoesntExists() {
    Assertions.assertThrows(
        ModuleNotFoundException.class, () -> updateModuleAdapter.updateModule(new Module()));
  }

  @Test
  @DisplayName("Should update project when a module with the passing ID exists")
  void shouldUpdateProjectWhenAModuleWithThePassingIdExists() {
    ModuleEntity mockedOldItem = new ModuleEntity();
    mockedOldItem.setId(1L);
    mockedOldItem.setPath("/dev/null");
    when(getModuleRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedOldItem));

    ModuleEntity mockedItem = new ModuleEntity();
    mockedItem.setId(1L);
    mockedItem.setPath("/opt");
    when(updateModuleRepository.save(any(ModuleEntity.class))).thenReturn(mockedItem);

    Module project = new Module();
    project.setId(1L);
    project.setPath("/opt");
    updateModuleAdapter.updateModule(project);

    verify(updateModuleRepository, times(1)).save(mockedItem);
    Assertions.assertNotEquals(mockedOldItem, project);
  }
}
