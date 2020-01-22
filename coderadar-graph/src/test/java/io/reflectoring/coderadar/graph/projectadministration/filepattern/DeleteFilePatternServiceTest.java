package io.reflectoring.coderadar.graph.projectadministration.filepattern;

import io.reflectoring.coderadar.graph.projectadministration.filepattern.adapter.DeleteFilePatternAdapter;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.FilePatternRepository;
import org.junit.jupiter.api.DisplayName;

import static org.mockito.Mockito.mock;

@DisplayName("Delete file pattern")
public class DeleteFilePatternServiceTest { // TODO
  private FilePatternRepository filePatternRepository = mock(FilePatternRepository.class);
  private DeleteFilePatternAdapter deleteFilePatternAdapter =
      new DeleteFilePatternAdapter(filePatternRepository);
}
