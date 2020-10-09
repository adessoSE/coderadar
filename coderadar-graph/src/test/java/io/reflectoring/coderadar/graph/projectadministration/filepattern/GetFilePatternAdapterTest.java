package io.reflectoring.coderadar.graph.projectadministration.filepattern;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.reflectoring.coderadar.graph.projectadministration.domain.FilePatternEntity;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.adapter.GetFilePatternAdapter;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.FilePatternRepository;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Get file pattern")
class GetFilePatternAdapterTest {
  private FilePatternRepository filePatternRepository = mock(FilePatternRepository.class);

  @Test
  @DisplayName("Should return file pattern when passing a valid argument")
  void shouldReturnFilePatternWhenPassingAValidArgument() {
    GetFilePatternAdapter getFilePatternAdapter = new GetFilePatternAdapter(filePatternRepository);

    when(filePatternRepository.findById(anyLong(), anyInt()))
        .thenReturn(Optional.of(new FilePatternEntity(1L, "test", InclusionType.INCLUDE)));
    FilePattern returnedFilePattern = getFilePatternAdapter.get(1L);
    Assertions.assertNotNull(returnedFilePattern);
  }
}
