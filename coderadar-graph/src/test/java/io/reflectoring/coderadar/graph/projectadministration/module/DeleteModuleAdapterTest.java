package io.reflectoring.coderadar.graph.projectadministration.module;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.graph.projectadministration.module.repository.DeleteModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.service.DeleteModuleAdapter;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.DeleteProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Delete module")
class DeleteModuleAdapterTest {
  private DeleteModuleRepository deleteModuleRepository = mock(DeleteModuleRepository.class);
  private DeleteProjectRepository deleteProjectRepository = mock(DeleteProjectRepository.class);

  private DeleteModuleAdapter deleteModuleAdapter;

  @BeforeEach
  void setUp() {
    deleteModuleAdapter = new DeleteModuleAdapter(deleteModuleRepository, deleteProjectRepository);
  }

  @Test
  @DisplayName("Should delete module when passing a valid module entity")
  void shouldDleteModuleWhenPassingAValidModuleEntity() {
    doNothing().when(deleteModuleRepository).delete(isA(Module.class));
    deleteModuleAdapter.delete(new Module());
    verify(deleteModuleRepository, times(1)).delete(any(Module.class));
  }

  @Test
  @DisplayName("Should delete module when passing a valid module id")
  void shouldDeleteModuleWhenPassingAValidModuleId() {
    doNothing().when(deleteModuleRepository).deleteById(isA(Long.class));
    deleteModuleAdapter.delete(1L);
    verify(deleteModuleRepository, times(1)).deleteById(any(Long.class));
  }
}
