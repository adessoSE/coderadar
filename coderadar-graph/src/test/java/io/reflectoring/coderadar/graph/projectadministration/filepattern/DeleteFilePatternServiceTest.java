package io.reflectoring.coderadar.graph.projectadministration.filepattern;

import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.DeleteFilePatternRepository;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.service.DeleteFilePatternService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Delete file pattern")
public class DeleteFilePatternServiceTest {
    @Mock private DeleteFilePatternRepository deleteFilePatternRepository;
    @InjectMocks private DeleteFilePatternService deleteFilePatternService;
}
