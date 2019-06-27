package io.reflectoring.coderadar.graph.projectadministration.filepattern;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.ListFilePatternsOfProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.service.ListFilePatternsOfProjectAdapter;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("List file patterns of project")
class ListFilePatternsOfProjectAdapterTest {

  private ListFilePatternsOfProjectRepository listFilePatternsOfProjectRepository =
      mock(ListFilePatternsOfProjectRepository.class);
  private GetProjectRepository getProjectRepository = mock(GetProjectRepository.class);

  @Test
  @DisplayName("Should return list of file patterns when passing valid argument")
  void shouldReturnListOfFilePatternsWhenPassingValidArgument() {
    ListFilePatternsOfProjectAdapter listFilePatternsOfProjectAdapter =
        new ListFilePatternsOfProjectAdapter(
            listFilePatternsOfProjectRepository, getProjectRepository);

    when(getProjectRepository.findById(anyLong()))
        .thenReturn(java.util.Optional.of(new ProjectEntity()));
    Collection<FilePattern> returnedList = listFilePatternsOfProjectAdapter.listFilePatterns(1L);
    Assertions.assertThat(returnedList).isNotNull();
  }
}
