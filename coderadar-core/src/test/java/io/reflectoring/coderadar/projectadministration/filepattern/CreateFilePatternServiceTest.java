package io.reflectoring.coderadar.projectadministration.filepattern;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.CreateFilePatternPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.create.CreateFilePatternCommand;
import io.reflectoring.coderadar.projectadministration.service.filepattern.CreateFilePatternService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

class CreateFilePatternServiceTest {
  private GetProjectPort getProjectPort = mock(GetProjectPort.class);
  private CreateFilePatternPort createFilePatternPort = mock(CreateFilePatternPort.class);

  @Test
  void returnsNewFilePatternId() {
    CreateFilePatternService testSubject = new CreateFilePatternService(createFilePatternPort);

    Project project = new Project();
    project.setId(1L);
    project.setName("project name");

    Mockito.when(getProjectPort.get(1L)).thenReturn(project);

    FilePattern filePattern = new FilePattern();
    filePattern.setPattern("**/*.java");
    filePattern.setInclusionType(InclusionType.INCLUDE);
    Mockito.when(createFilePatternPort.createFilePattern(filePattern, 1L)).thenReturn(1L);

    CreateFilePatternCommand command =
        new CreateFilePatternCommand("**/*.java", InclusionType.INCLUDE);
    Long filePatternId = testSubject.createFilePattern(command, project.getId());

    Assertions.assertEquals(1L, filePatternId.longValue());
  }
}
