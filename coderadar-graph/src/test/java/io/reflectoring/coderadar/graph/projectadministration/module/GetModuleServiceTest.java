package io.reflectoring.coderadar.graph.projectadministration.module;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.GetModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.service.GetModuleService;
import java.util.Optional;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GetModuleServiceTest {
  @Mock private GetModuleRepository getModuleRepository;

  @InjectMocks private GetModuleService getModuleService;

  @Test
  public void withModuleIdShouldReturnModuleEntityAsOptional() {
    Module mockedItem = new Module();
    mockedItem.setId(1L);
    when(getModuleRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedItem));

    Optional<Module> returned = getModuleService.get(1L);

    verify(getModuleRepository, times(1)).findById(1L);
    verifyNoMoreInteractions(getModuleRepository);
    Assertions.assertTrue(returned.isPresent());
    Assertions.assertEquals(new Long(1L), returned.get().getId());
  }

  @Test
  public void withNoPersistedModuleShouldReturnEmptyOptional() {
    Optional<Module> mockedItem = Optional.empty();
    when(getModuleRepository.findById(any(Long.class))).thenReturn(mockedItem);

    Optional<Module> returned = getModuleService.get(1L);

    verify(getModuleRepository, times(1)).findById(1L);
    verifyNoMoreInteractions(getModuleRepository);
    Assertions.assertFalse(returned.isPresent());
  }
}
