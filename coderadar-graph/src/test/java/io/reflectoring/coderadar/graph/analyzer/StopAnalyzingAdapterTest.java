package io.reflectoring.coderadar.graph.analyzer;

import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.analyzer.AnalyzingJobNotStartedException;
import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzingJobEntity;
import io.reflectoring.coderadar.graph.analyzer.repository.AnalyzingJobRepository;
import io.reflectoring.coderadar.graph.analyzer.service.StopAnalyzingAdapter;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Stop analyzing")
class StopAnalyzingAdapterTest {
  private AnalyzingJobRepository analyzingJobRepository = mock(AnalyzingJobRepository.class);

  private ProjectRepository projectRepository = mock(ProjectRepository.class);

  private StopAnalyzingAdapter stopAnalyzingAdapter;

  @BeforeEach
  void setUp() {
    stopAnalyzingAdapter = new StopAnalyzingAdapter(analyzingJobRepository, projectRepository);
  }

  @Test
  @DisplayName("Should throw exception when a project with the passing ID doesn't exists")
  void shouldThrowExceptionWhenAProjectWithThePassingIdDoesntExists() {
    Assertions.assertThrows(ProjectNotFoundException.class, () -> stopAnalyzingAdapter.stop(1L));
  }

  // TODO: Do we need the whole job thing anymore????
  @Test
  @DisplayName("Should throw exception when no active analyzing job exists")
  void shouldThrowExceptionWhenNoActiveAnalzingJobExists() {
    ProjectEntity mockProject = new ProjectEntity();
    mockProject.setId(1L);
    AnalyzingJobEntity mockItem = new AnalyzingJobEntity();
    mockItem.setId(10L);
    mockItem.setActive(false);
    when(projectRepository.findProjectById(1L)).thenReturn(Optional.of(mockProject));
    when(analyzingJobRepository.findByProjectId(1L)).thenReturn(Optional.of(mockItem));

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
    when(projectRepository.findProjectById(1L)).thenReturn(Optional.of(mockProject));
    when(analyzingJobRepository.findByProjectId(1L)).thenReturn(Optional.of(mockItem));

    stopAnalyzingAdapter.stop(1L);
    verify(analyzingJobRepository, times(1)).save(mockItem);
  }
}
