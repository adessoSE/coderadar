package io.reflectoring.coderadar.core.projectadministration.filepattern;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.core.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.CreateFilePatternPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.create.CreateFilePatternCommand;
import io.reflectoring.coderadar.core.projectadministration.service.filepattern.CreateFilePatternService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class CreateFilePatternServiceTest {
  @Mock private GetProjectPort getProjectPort;
  @Mock private CreateFilePatternPort createFilePatternPort;

  private CreateFilePatternService testSubject;

  @BeforeEach
  void setup() {
    testSubject = new CreateFilePatternService(getProjectPort, createFilePatternPort);
  }

  @Test
  void returnsNewFilePatternId() {
    Project project = new Project();
    project.setId(1L);
    project.setName("project name");

    Mockito.when(getProjectPort.get(1L)).thenReturn(project);

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
