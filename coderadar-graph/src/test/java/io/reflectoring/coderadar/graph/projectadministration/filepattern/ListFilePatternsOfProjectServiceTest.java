package io.reflectoring.coderadar.graph.projectadministration.filepattern;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.ListFilePatternsOfProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.service.ListFilePatternsOfProjectService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DisplayName("List file patterns of project")
public class ListFilePatternsOfProjectServiceTest {
    @Mock private ListFilePatternsOfProjectRepository listFilePatternsOfProjectRepository;
    @InjectMocks private ListFilePatternsOfProjectService listFilePatternsOfProjectService;

    @Test
    @DisplayName("Should return list of file patterns when passing valid argument")
    void shouldReturnListOfFilePatternsWhenPassingValidArgument() {
        List<FilePattern> returnedList = listFilePatternsOfProjectService.listFilePatterns(1L);
        Assertions.assertThat(returnedList).isNotNull();
    }
}
