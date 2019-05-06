package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.graph.exception.ProjectNotFoundException;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.AddAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service.AddAnalyzerConfigurationService;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Add analyzer configuration")
class AddAnalyzerConfigurationServiceTest {
  @Mock private AddAnalyzerConfigurationRepository addAnalyzerConfigurationRepository;

  @Mock private GetProjectRepository getProjectRepository;

  @InjectMocks private AddAnalyzerConfigurationService addAnalyzerConfigurationService;

  @Test
  @DisplayName("Should throw exception when a project with the passing ID doesn't exists")
  void shouldThrowExceptionWhenAProjectWithThePassingIdDoesntExists() {
    Assertions.assertThrows(
        ProjectNotFoundException.class,
        () -> addAnalyzerConfigurationService.add(1L, new AnalyzerConfiguration()));
  }

  @Test
  @DisplayName("Should return ID when saving an analyzer configuration")
  void shouldReturnIdWhenSavingAnAnalyzerConfiguration() {
    AnalyzerConfiguration mockItem = new AnalyzerConfiguration();
    mockItem.setId(10L);
    Project mockProject = new Project();
    mockProject.setId(1L);
    when(getProjectRepository.findById(anyLong())).thenReturn(java.util.Optional.of(mockProject));
    when(addAnalyzerConfigurationRepository.save(any(AnalyzerConfiguration.class)))
        .thenReturn(mockItem);

    AnalyzerConfiguration item = new AnalyzerConfiguration();
    Long idFromItem = addAnalyzerConfigurationService.add(1L, item);

    verify(getProjectRepository, times(1)).findById(1L);
    verify(addAnalyzerConfigurationRepository, times(1)).save(item);
    verifyNoMoreInteractions(getProjectRepository);
    verifyNoMoreInteractions(addAnalyzerConfigurationRepository);
    org.assertj.core.api.Assertions.assertThat(idFromItem).isEqualTo(10L);
  }
}
