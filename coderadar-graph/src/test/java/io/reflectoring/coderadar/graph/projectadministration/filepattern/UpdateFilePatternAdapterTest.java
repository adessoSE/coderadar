package io.reflectoring.coderadar.graph.projectadministration.filepattern;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.graph.projectadministration.filepattern.adapter.UpdateFilePatternAdapter;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.FilePatternRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Update file pattern")
public class UpdateFilePatternAdapterTest {
  private FilePatternRepository filePatternRepository = mock(FilePatternRepository.class);
  private UpdateFilePatternAdapter updateFilePatternAdapter;

  @BeforeEach
  void setUp() {
    updateFilePatternAdapter = new UpdateFilePatternAdapter(filePatternRepository);
  }
}
