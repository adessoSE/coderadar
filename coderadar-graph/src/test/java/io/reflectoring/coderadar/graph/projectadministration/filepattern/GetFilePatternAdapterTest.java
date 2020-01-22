package io.reflectoring.coderadar.graph.projectadministration.filepattern;

import io.reflectoring.coderadar.graph.projectadministration.domain.FilePatternEntity;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.adapter.GetFilePatternAdapter;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.FilePatternRepository;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Get file pattern")
public class GetFilePatternAdapterTest {
  private FilePatternRepository filePatternRepository = mock(FilePatternRepository.class);

  @Test
  @DisplayName("Should return file pattern when passing a valid argument")
  void shouldReturnFilePatternWhenPassingAValidArgument() {
    GetFilePatternAdapter getFilePatternAdapter = new GetFilePatternAdapter(filePatternRepository);

    when(filePatternRepository.findById(anyLong()))
        .thenReturn(Optional.of(new FilePatternEntity()));
    FilePattern returnedFilePattern = getFilePatternAdapter.get(1L);
    Assertions.assertNotNull(returnedFilePattern);
  }
}
