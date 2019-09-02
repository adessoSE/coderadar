package io.reflectoring.coderadar.graph.projectadministration.filepattern;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.FilePatternRepository;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.service.DeleteFilePatternAdapter;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Delete file pattern")
public class DeleteFilePatternServiceTest { // TODO
  private FilePatternRepository filePatternRepository = mock(FilePatternRepository.class);
  private DeleteFilePatternAdapter deleteFilePatternAdapter =
      new DeleteFilePatternAdapter(filePatternRepository);
}
