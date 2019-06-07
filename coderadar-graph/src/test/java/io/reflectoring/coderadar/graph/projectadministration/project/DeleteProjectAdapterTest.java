package io.reflectoring.coderadar.graph.projectadministration.project;

import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.DeleteAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.DeleteFilePatternRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.DeleteModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.DeleteProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.service.DeleteProjectAdapter;
import io.reflectoring.coderadar.graph.query.repository.GetCommitsInProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Delete project")
class DeleteProjectAdapterTest {
  private DeleteProjectRepository deleteProjectRepository = mock(DeleteProjectRepository.class);
  private DeleteModuleRepository deleteModuleRepository = mock(DeleteModuleRepository.class);
  private DeleteFilePatternRepository deleteFilePatternRepository =
      mock(DeleteFilePatternRepository.class);
  private DeleteAnalyzerConfigurationRepository deleteAnalyzerConfigurationRepository =
      mock(DeleteAnalyzerConfigurationRepository.class);
  private GetCommitsInProjectRepository getCommitsInProjectRepository =
      mock(GetCommitsInProjectRepository.class);

  private DeleteProjectAdapter deleteProjectAdapter;

  @BeforeEach
  void setUp() {
    when(deleteProjectRepository.findById(anyLong())).thenReturn(Optional.of(new Project()));
    deleteProjectAdapter =
        new DeleteProjectAdapter(
            deleteProjectRepository,
            deleteModuleRepository,
            deleteFilePatternRepository,
            deleteAnalyzerConfigurationRepository,
            getCommitsInProjectRepository);
  }

  @Test
  @DisplayName("Should delete project when passing a valid project entity")
  void shouldDeleteProjectWhenPassingAValidProjectEntity() {
    doNothing().when(deleteProjectRepository).delete(isA(Project.class));

    deleteProjectAdapter.delete(new Project());

    verify(deleteProjectRepository, times(1)).delete(any(Project.class));
  }

  @Test
  @DisplayName("Should delete project when passing a valid project id")
  void shouldDeleteProjectWhenPassingAValidProjectId() {
    doNothing().when(deleteProjectRepository).deleteById(isA(Long.class));

    deleteProjectAdapter.delete(1L);

    verify(deleteProjectRepository, times(1)).deleteById(any(Long.class));
  }
}
