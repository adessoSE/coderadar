package io.reflectoring.coderadar.graph.analyzer;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.core.analyzer.port.driver.StartAnalyzingCommand;
import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzingJob;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.graph.analyzer.repository.StartAnalyzingRepository;
import io.reflectoring.coderadar.graph.analyzer.service.StartAnalyzingService;
import io.reflectoring.coderadar.graph.exception.ProjectNotFoundException;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Start analyzing")
class StartAnalyzingServiceTest {
  @Mock private StartAnalyzingRepository startAnalyzingRepository;

  @Mock private GetProjectRepository getProjectRepository;

  @InjectMocks private StartAnalyzingService startAnalyzingService;

  @Test
  @DisplayName("Should throw exception when a project with the passing ID doesn't exists")
  void shouldThrowExceptionWhenAProjectWithThePassingIdDoesntExists() {
    Assertions.assertThrows(
        ProjectNotFoundException.class,
        () -> startAnalyzingService.start(new StartAnalyzingCommand(1L, new Date(), true)));
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

    StartAnalyzingCommand item = new StartAnalyzingCommand(1L, mockDate, true);
    Long idFromItem = startAnalyzingService.start(item);

    verify(getProjectRepository, times(1)).findById(1L);
    verify(startAnalyzingRepository, times(1)).save(any(AnalyzingJob.class));
    verifyNoMoreInteractions(getProjectRepository);
    verifyNoMoreInteractions(startAnalyzingRepository);
    org.assertj.core.api.Assertions.assertThat(idFromItem).isEqualTo(10L);
  }
}
