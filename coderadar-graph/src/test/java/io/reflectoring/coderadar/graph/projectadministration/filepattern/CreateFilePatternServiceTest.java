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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DisplayName("Create file pattern")
public class CreateFilePatternServiceTest {
  @Mock private CreateFilePatternRepository createFilePatternRepository;
  @InjectMocks private CreateFilePatternService createFilePatternService;

  @Test
  @DisplayName("Should return long when passing a valid argument")
  void shouldReturnLongWhenPassingAValidArgument() {
    FilePattern filePattern = new FilePattern();
    filePattern.setId(2L);

    when(createFilePatternRepository.save(any())).thenReturn(filePattern);
    Long returnedId = createFilePatternService.createFilePattern(filePattern);
    Assertions.assertThat(returnedId).isNotNull();
    Assertions.assertThat(returnedId).isEqualTo(2L);
  }
}
