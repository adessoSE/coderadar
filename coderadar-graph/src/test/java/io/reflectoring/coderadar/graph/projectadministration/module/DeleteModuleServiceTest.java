package io.reflectoring.coderadar.graph.projectadministration.module;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.DeleteModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.service.DeleteModuleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Delete module")
class DeleteModuleServiceTest {
  @Mock private DeleteModuleRepository deleteModuleRepository;

  @InjectMocks private DeleteModuleService deleteModuleService;

  @Test
  @DisplayName("Should delete module when passing a valid module entity")
  void shouldDleteModuleWhenPassingAValidModuleEntity() {
    doNothing().when(deleteModuleRepository).delete(isA(Module.class));
    deleteModuleService.delete(new Module());
    verify(deleteModuleRepository, times(1)).delete(any(Module.class));
  }

  @Test
  @DisplayName("Should delete module when passing a valid module id")
  void shouldDeleteModuleWhenPassingAValidModuleId() {
    doNothing().when(deleteModuleRepository).deleteById(isA(Long.class));
    deleteModuleService.delete(1L);
    verify(deleteModuleRepository, times(1)).deleteById(any(Long.class));
  }
}
