package io.reflectoring.coderadar.graph.projectadministration.module;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.core.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.graph.exception.InvalidArgumentException;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.GetModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.UpdateModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.service.UpdateModuleService;
import java.util.Optional;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UpdateModuleServiceTest {
  @Mock private UpdateModuleRepository updateModuleRepository;

  @Mock private GetModuleRepository getModuleRepository;

  @InjectMocks private UpdateModuleService updateModuleService;

  @Test
  public void withInvalidArgumentShouldThrowInvalidArgumentException() {
    Assertions.assertThrows(
        InvalidArgumentException.class, () -> updateModuleService.updateModule(null));
  }

  @Test
  public void withNoPersistedModuleShouldThrowModuleNotFoundException() {
    Assertions.assertThrows(
        ModuleNotFoundException.class, () -> updateModuleService.updateModule(new Module()));
  }

  @Test
  public void withPersistedModuleShouldUpdateModule() {
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
