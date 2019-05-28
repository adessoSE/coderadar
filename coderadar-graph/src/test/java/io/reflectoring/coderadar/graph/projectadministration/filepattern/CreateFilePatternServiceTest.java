package io.reflectoring.coderadar.graph.projectadministration.filepattern;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.CreateFilePatternRepository;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.service.CreateFilePatternService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Create file pattern")
class CreateFilePatternServiceTest {
  private CreateFilePatternRepository createFilePatternRepository =
      mock(CreateFilePatternRepository.class);

  @Test
  @DisplayName("Should return long when passing a valid argument")
  void shouldReturnLongWhenPassingAValidArgument() {
    CreateFilePatternService createFilePatternService =
        new CreateFilePatternService(createFilePatternRepository);

    FilePattern filePattern = new FilePattern();
    filePattern.setId(2L);

    when(createFilePatternRepository.save(any())).thenReturn(filePattern);
    Long returnedId = createFilePatternService.createFilePattern(filePattern);
    Assertions.assertThat(returnedId).isNotNull();
    Assertions.assertThat(returnedId).isEqualTo(2L);
  }
}
