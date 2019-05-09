package io.reflectoring.coderadar.core.projectadministration.filepattern;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.core.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.CreateFilePatternPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.create.CreateFilePatternCommand;
import io.reflectoring.coderadar.core.projectadministration.service.filepattern.CreateFilePatternService;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class CreateFilePatternServiceTest {
  @Mock private GetProjectPort getProjectPort;
  @Mock private CreateFilePatternPort createFilePatternPort;

  @InjectMocks private CreateFilePatternService testSubject;

  @Test
  void returnsNewFilePatternId() {
    Project project = new Project();
    project.setId(1L);
    project.setName("project name");

    Mockito.when(getProjectPort.get(1L)).thenReturn(Optional.of(project));

    FilePattern filePattern = new FilePattern();
    filePattern.setPattern("**/*.java");
    filePattern.setInclusionType(InclusionType.INCLUDE);
    filePattern.setProject(project);
    Mockito.when(createFilePatternPort.createFilePattern(filePattern)).thenReturn(1L);

    CreateFilePatternCommand command =
        new CreateFilePatternCommand("**/*.java", InclusionType.INCLUDE);
    Long filePatternId = testSubject.createFilePattern(command, project.getId());

    Assertions.assertEquals(1L, filePatternId.longValue());
  }
}
