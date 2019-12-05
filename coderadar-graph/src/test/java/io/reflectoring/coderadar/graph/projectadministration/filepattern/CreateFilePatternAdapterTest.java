package io.reflectoring.coderadar.graph.projectadministration.filepattern;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.reflectoring.coderadar.graph.projectadministration.domain.FilePatternEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.adapter.CreateFilePatternAdapter;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.FilePatternRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Create file pattern")
class CreateFilePatternAdapterTest {
  private FilePatternRepository filePatternRepository = mock(FilePatternRepository.class);
  private ProjectRepository projectRepository = mock(ProjectRepository.class);

  @Test
  @DisplayName("Should return long when passing a valid argument")
  void shouldReturnLongWhenPassingAValidArgument() {
    CreateFilePatternAdapter createFilePatternAdapter =
        new CreateFilePatternAdapter(projectRepository, filePatternRepository);

    FilePattern filePattern = new FilePattern();
    filePattern.setId(2L);
    FilePatternEntity filePatternEntity = new FilePatternEntity();
    filePatternEntity.setId(2L);
    when(projectRepository.findById(anyLong()))
        .thenReturn(java.util.Optional.of(new ProjectEntity()));
    when(filePatternRepository.save(any())).thenReturn(filePatternEntity);
    Long returnedId = createFilePatternAdapter.createFilePattern(filePattern, 1L);
    Assertions.assertThat(returnedId).isNotNull();
    Assertions.assertThat(returnedId).isEqualTo(2L);
  }
}
