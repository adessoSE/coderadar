package io.reflectoring.coderadar.graph.projectadministration.filepattern;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.UpdateFilePatternRepository;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.service.UpdateFilePatternService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Update file pattern")
public class UpdateFilePatternServiceTest {
  private UpdateFilePatternRepository updateFilePatternRepository =
      mock(UpdateFilePatternRepository.class);
  private UpdateFilePatternService updateFilePatternService;

  @BeforeEach
  void setUp() {
    updateFilePatternService = new UpdateFilePatternService(updateFilePatternRepository);
  }
}
