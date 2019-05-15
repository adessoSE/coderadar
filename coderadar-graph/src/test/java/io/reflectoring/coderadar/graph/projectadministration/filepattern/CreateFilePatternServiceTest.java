package io.reflectoring.coderadar.graph.projectadministration.filepattern;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.CreateFilePatternRepository;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.service.CreateFilePatternService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Create file pattern")
public class CreateFilePatternServiceTest {
  @Mock private CreateFilePatternRepository createFilePatternRepository;
  @InjectMocks private CreateFilePatternService createFilePatternService;

  @Test
  @DisplayName("Should return long when passing a valid argument")
  void shouldReturnLongWhenPassingAValidArgument() {
    FilePattern filePattern = new FilePattern();
    Long returnedId = createFilePatternService.createFilePattern(filePattern);
    Assertions.assertThat(returnedId).isNotNull();
  }
}
