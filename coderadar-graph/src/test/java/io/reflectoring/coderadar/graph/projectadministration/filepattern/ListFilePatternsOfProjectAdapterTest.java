package io.reflectoring.coderadar.graph.projectadministration.filepattern;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.ListFilePatternsOfProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.service.ListFilePatternsOfProjectAdapter;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("List file patterns of project")
class ListFilePatternsOfProjectAdapterTest {

  private ListFilePatternsOfProjectRepository listFilePatternsOfProjectRepository =
      mock(ListFilePatternsOfProjectRepository.class);

  @Test
  @DisplayName("Should return list of file patterns when passing valid argument")
  void shouldReturnListOfFilePatternsWhenPassingValidArgument() {
    ListFilePatternsOfProjectAdapter listFilePatternsOfProjectAdapter =
        new ListFilePatternsOfProjectAdapter(listFilePatternsOfProjectRepository);

    List<FilePattern> returnedList = listFilePatternsOfProjectAdapter.listFilePatterns(1L);
    Assertions.assertThat(returnedList).isNotNull();
  }
}
