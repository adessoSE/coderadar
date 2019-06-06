package io.reflectoring.coderadar.graph.projectadministration.filepattern;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.GetFilePatternRepository;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.service.GetFilePatternService;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Get file pattern")
public class GetFilePatternServiceTest {
  private GetFilePatternRepository getFilePatternRepository = mock(GetFilePatternRepository.class);

  @Test
  @DisplayName("Should return file pattern when passing a valid argument")
  void shouldReturnFilePatternWhenPassingAValidArgument() {
    GetFilePatternService getFilePatternService =
        new GetFilePatternService(getFilePatternRepository);

    when(getFilePatternRepository.findById(anyLong())).thenReturn(Optional.of(new FilePattern()));
    Optional<FilePattern> returnedFilePattern = getFilePatternService.get(1L);
    Assertions.assertTrue(returnedFilePattern.isPresent());
  }
}
