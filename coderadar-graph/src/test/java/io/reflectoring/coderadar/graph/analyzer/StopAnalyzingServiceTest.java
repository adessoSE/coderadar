package io.reflectoring.coderadar.graph.analyzer;

import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.analyzer.AnalyzingJobNotStartedException;
import io.reflectoring.coderadar.analyzer.domain.AnalyzingJob;
import io.reflectoring.coderadar.graph.analyzer.repository.GetAnalyzingStatusRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.StopAnalyzingRepository;
import io.reflectoring.coderadar.graph.analyzer.service.StopAnalyzingService;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Stop analyzing")
class StopAnalyzingServiceTest {
  private StopAnalyzingRepository stopAnalyzingRepository = mock(StopAnalyzingRepository.class);

  private GetProjectRepository getProjectRepository = mock(GetProjectRepository.class);

  private GetAnalyzingStatusRepository getAnalyzingStatusRepository =
      mock(GetAnalyzingStatusRepository.class);

  private StopAnalyzingService stopAnalyzingService;

  @BeforeEach
  void setUp() {
    stopAnalyzingService =
        new StopAnalyzingService(
            getAnalyzingStatusRepository, stopAnalyzingRepository, getProjectRepository);
  }

  @Test
  @DisplayName("Should throw exception when a project with the passing ID doesn't exists")
  void shouldThrowExceptionWhenAProjectWithThePassingIdDoesntExists() {
    Assertions.assertThrows(ProjectNotFoundException.class, () -> stopAnalyzingService.stop(1L));
  }

  @Test
  @DisplayName("Should throw exception when no active analyzing job exists")
  void shouldThrowExceptionWhenNoActiveAnalzingJobExists() {
    Project mockProject = new Project();
    mockProject.setId(1L);
    AnalyzingJob mockItem = new AnalyzingJob();
    mockItem.setId(10L);
    mockItem.setActive(false);
    when(getProjectRepository.findById(1L)).thenReturn(Optional.of(mockProject));
    when(getAnalyzingStatusRepository.findByProject_Id(1L)).thenReturn(Optional.of(mockItem));

    Assertions.assertThrows(
        AnalyzingJobNotStartedException.class, () -> stopAnalyzingService.stop(1L));
  }

  @Test
  @DisplayName("Should stop analyzing job when a active analzing job exists")
  void withActiveAnalyzingJobShouldStopAnalyzingJob() {
    Project mockProject = new Project();
    mockProject.setId(1L);
    AnalyzingJob mockItem = new AnalyzingJob();
    mockItem.setId(10L);
    mockItem.setActive(true);
    when(getProjectRepository.findById(1L)).thenReturn(Optional.of(mockProject));
    when(getAnalyzingStatusRepository.findByProject_Id(1L)).thenReturn(Optional.of(mockItem));

    stopAnalyzingService.stop(1L);
    verify(stopAnalyzingRepository, times(1)).save(mockItem);
  }
}
