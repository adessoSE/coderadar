package io.reflectoring.coderadar.graph.projectadministration.module;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.graph.exception.InvalidArgumentException;
import io.reflectoring.coderadar.graph.exception.ProjectNotFoundException;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.CreateModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.service.CreateModuleService;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CreateModuleServiceTest {
  @Mock private CreateModuleRepository createModuleRepository;

  @Mock private GetProjectRepository getProjectRepository;

  @InjectMocks private CreateModuleService createModuleService;

  @Test
  public void withNoPersistedProjectShouldThrowProjectNotFoundException() {
    Assertions.assertThrows(
        ProjectNotFoundException.class, () -> createModuleService.createModule(1L, new Module()));
  }

  @Test
  public void withCreatedModuleShouldReturnModuleWithId() {
    Module mockedItem = new Module();
    mockedItem.setId(1L);
    Module newItem = new Module();
    Project mockedProject = new Project();
    when(getProjectRepository.findById(1L)).thenReturn(java.util.Optional.of(mockedProject));
    when(createModuleRepository.save(any(Module.class))).thenReturn(mockedItem);

    Long returnedId = createModuleService.createModule(1L, newItem);

    verify(createModuleRepository, times(1)).save(newItem);
    verifyNoMoreInteractions(createModuleRepository);
    Assertions.assertEquals(new Long(1L), returnedId);
  }
}
