package io.reflectoring.coderadar.graph.projectadministration.filepattern;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.ListFilePatternsOfProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.service.ListFilePatternsOfProjectService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.mock;

@DisplayName("List file patterns of project")
class ListFilePatternsOfProjectServiceTest {
  private ListFilePatternsOfProjectRepository listFilePatternsOfProjectRepository =
      mock(ListFilePatternsOfProjectRepository.class);

  @Test
  @DisplayName("Should return list of file patterns when passing valid argument")
  void shouldReturnListOfFilePatternsWhenPassingValidArgument() {
    ListFilePatternsOfProjectService listFilePatternsOfProjectService =
        new ListFilePatternsOfProjectService(listFilePatternsOfProjectRepository);

    List<FilePattern> returnedList = listFilePatternsOfProjectService.listFilePatterns(1L);
    Assertions.assertThat(returnedList).isNotNull();
  }
}
