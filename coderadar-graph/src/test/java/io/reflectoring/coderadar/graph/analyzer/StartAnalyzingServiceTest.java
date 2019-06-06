package io.reflectoring.coderadar.graph.analyzer;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.analyzer.domain.AnalyzingJob;
import io.reflectoring.coderadar.analyzer.port.driver.StartAnalyzingCommand;
import io.reflectoring.coderadar.graph.analyzer.repository.StartAnalyzingRepository;
import io.reflectoring.coderadar.graph.analyzer.service.StartAnalyzingService;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Start analyzing")
class StartAnalyzingServiceTest {
  private StartAnalyzingRepository startAnalyzingRepository = mock(StartAnalyzingRepository.class);

  private GetProjectRepository getProjectRepository = mock(GetProjectRepository.class);

  private StartAnalyzingService startAnalyzingService;

  @BeforeEach
  void setUp() {
    startAnalyzingService =
        new StartAnalyzingService(getProjectRepository, startAnalyzingRepository);
  }

  @Test
  @DisplayName("Should throw exception when a project with the passing ID doesn't exists")
  void shouldThrowExceptionWhenAProjectWithThePassingIdDoesntExists() {
    Assertions.assertThrows(
        ProjectNotFoundException.class,
        () -> startAnalyzingService.start(new StartAnalyzingCommand(new Date(), true), 1L));
  }

  @Test
  @DisplayName("Should return ID when saving an analyzing job")
  void shouldReturnIdWhenSavingAnAnalyzingJob() {
    Date mockDate = new Date();
    Project mockProject = new Project();
    mockProject.setId(1L);
    AnalyzingJob mockItem = new AnalyzingJob();
    mockItem.setId(10L);
    mockItem.setActive(true);
    mockItem.setProject(mockProject);
    mockItem.setRescan(true);
    mockItem.setFrom(mockDate);
    when(startAnalyzingRepository.save(any(AnalyzingJob.class))).thenReturn(mockItem);
    when(getProjectRepository.findById(anyLong())).thenReturn(java.util.Optional.of(mockProject));

    StartAnalyzingCommand item = new StartAnalyzingCommand(mockDate, true);
    Long idFromItem = startAnalyzingService.start(item, 1L);

    verify(getProjectRepository, times(1)).findById(1L);
    verify(startAnalyzingRepository, times(1)).save(any(AnalyzingJob.class));
    verifyNoMoreInteractions(getProjectRepository);
    verifyNoMoreInteractions(startAnalyzingRepository);
    org.assertj.core.api.Assertions.assertThat(idFromItem).isEqualTo(10L);
  }
}
