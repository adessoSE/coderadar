package io.reflectoring.coderadar.graph.projectadministration.filepattern;

import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.UpdateFilePatternRepository;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.service.UpdateFilePatternService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Update file pattern")
public class UpdateFilePatternServiceTest {
    @Mock private UpdateFilePatternRepository updateFilePatternRepository;
    @InjectMocks private UpdateFilePatternService updateFilePatternService;
}
