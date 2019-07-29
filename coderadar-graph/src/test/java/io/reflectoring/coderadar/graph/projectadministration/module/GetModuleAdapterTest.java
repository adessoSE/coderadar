package io.reflectoring.coderadar.graph.projectadministration.module;

import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.GetModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.service.GetModuleAdapter;
import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Get module")
class GetModuleAdapterTest {
  private GetModuleRepository getModuleRepository = mock(GetModuleRepository.class);

  private GetModuleAdapter getModuleAdapter;

  @BeforeEach
  void setUp() {
    getModuleAdapter = new GetModuleAdapter(getModuleRepository);
  }

  @Test
  @DisplayName("Should return module as optional when a module with the passing ID exists")
  void shouldReturnModuleAsOptionalWhenAModuleWithThePassingIdExists() {
    ModuleEntity mockedItem = new ModuleEntity();
    mockedItem.setId(1L);
    when(getModuleRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedItem));

    Module returned = getModuleAdapter.get(1L);

    verify(getModuleRepository, times(1)).findById(1L);
    verifyNoMoreInteractions(getModuleRepository);
    Assertions.assertNotNull(returned);
    Assertions.assertEquals(new Long(1L), returned.getId());
  }

  @Test
  @DisplayName(
      "Should return module as empty optional when a module with the passing ID doesn't exists")
  void shouldReturnModuleAsEmptyOptionalWhenAModuleWithThePassingIdDoesntExists() {
    Optional<ModuleEntity> mockedItem = Optional.empty();
    when(getModuleRepository.findById(any(Long.class))).thenReturn(mockedItem);

    Assertions.assertThrows(ModuleNotFoundException.class, () -> getModuleAdapter.get(1L));
    verify(getModuleRepository, times(1)).findById(1L);
    verifyNoMoreInteractions(getModuleRepository);
  }
}
