package io.reflectoring.coderadar.graph.projectadministration.filepattern;

import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.UpdateFilePatternRepository;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.service.UpdateFilePatternAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.mockito.Mockito.mock;

@DisplayName("Update file pattern")
public class UpdateFilePatternAdapterTest {
  private UpdateFilePatternRepository updateFilePatternRepository =
      mock(UpdateFilePatternRepository.class);
  private UpdateFilePatternAdapter updateFilePatternAdapter;

  @BeforeEach
  void setUp() {
    updateFilePatternAdapter = new UpdateFilePatternAdapter(updateFilePatternRepository);
  }
}
