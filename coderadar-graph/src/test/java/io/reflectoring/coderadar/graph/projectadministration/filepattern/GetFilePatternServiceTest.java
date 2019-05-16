package io.reflectoring.coderadar.graph.projectadministration.filepattern;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.GetFilePatternRepository;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.service.GetFilePatternService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DisplayName("Get file pattern")
public class GetFilePatternServiceTest {
  @Mock private GetFilePatternRepository getFilePatternRepository;
  @InjectMocks private GetFilePatternService getFilePatternService;

  @Test
  @DisplayName("Should return file pattern when passing a valid argument")
  void shouldReturnFilePatternWhenPassingAValidArgument() {
    when(getFilePatternRepository.findById(anyLong())).thenReturn(Optional.of(new FilePattern()));
    Optional<FilePattern> returnedFilePattern = getFilePatternService.get(1L);
    Assertions.assertTrue(returnedFilePattern.isPresent());
  }
}
