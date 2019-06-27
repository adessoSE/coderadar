package io.reflectoring.coderadar.graph.analyzer;

import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.analyzer.AnalyzingJobNotStartedException;
import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzingJobEntity;
import io.reflectoring.coderadar.graph.analyzer.repository.GetAnalyzingStatusRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.StartAnalyzingRepository;
import io.reflectoring.coderadar.graph.analyzer.service.StopAnalyzingAdapter;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Stop analyzing")
class StopAnalyzingAdapterTest {
  private StartAnalyzingRepository stopAnalyzingRepository = mock(StartAnalyzingRepository.class);

  private GetProjectRepository getProjectRepository = mock(GetProjectRepository.class);

  private GetAnalyzingStatusRepository getAnalyzingStatusRepository =
      mock(GetAnalyzingStatusRepository.class);

  private StopAnalyzingAdapter stopAnalyzingAdapter;

  @BeforeEach
  void setUp() {
    stopAnalyzingAdapter =
        new StopAnalyzingAdapter(
            getAnalyzingStatusRepository, stopAnalyzingRepository, getProjectRepository);
  }

  @Test
  @DisplayName("Should throw exception when a project with the passing ID doesn't exists")
  void shouldThrowExceptionWhenAProjectWithThePassingIdDoesntExists() {
    Assertions.assertThrows(ProjectNotFoundException.class, () -> stopAnalyzingAdapter.stop(1L));
  }

  @Test
  @DisplayName("Should throw exception when no active analyzing job exists")
  void shouldThrowExceptionWhenNoActiveAnalzingJobExists() {
    ProjectEntity mockProject = new ProjectEntity();
    mockProject.setId(1L);
    AnalyzingJobEntity mockItem = new AnalyzingJobEntity();
    mockItem.setId(10L);
    mockItem.setActive(false);
    when(getProjectRepository.findById(1L)).thenReturn(Optional.of(mockProject));
    when(getAnalyzingStatusRepository.findByProjectId(1L)).thenReturn(Optional.of(mockItem));

    Assertions.assertThrows(
        AnalyzingJobNotStartedException.class, () -> stopAnalyzingAdapter.stop(1L));
  }

  @Test
  @DisplayName("Should stop analyzing job when a active analzing job exists")
  void withActiveAnalyzingJobShouldStopAnalyzingJob() {
    ProjectEntity mockProject = new ProjectEntity();
    mockProject.setId(1L);
    AnalyzingJobEntity mockItem = new AnalyzingJobEntity();
    mockItem.setId(10L);
    mockItem.setActive(true);
    when(getProjectRepository.findById(1L)).thenReturn(Optional.of(mockProject));
    when(getAnalyzingStatusRepository.findByProjectId(1L)).thenReturn(Optional.of(mockItem));

    stopAnalyzingAdapter.stop(1L);
    verify(stopAnalyzingRepository, times(1)).save(mockItem);
  }
}
