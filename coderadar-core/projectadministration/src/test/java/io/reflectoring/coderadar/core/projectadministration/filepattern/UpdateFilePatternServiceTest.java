package io.reflectoring.coderadar.core.projectadministration.filepattern;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.core.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.GetFilePatternPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.UpdateFilePatternPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.update.UpdateFilePatternCommand;
import io.reflectoring.coderadar.core.projectadministration.service.filepattern.UpdateFilePatternService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class UpdateFilePatternServiceTest {
  @Mock private GetFilePatternPort getFilePatternPort;
  @Mock private UpdateFilePatternPort updateFilePatternPort;
  private UpdateFilePatternService testSubject;

  @BeforeEach
  void setup() {
    testSubject = new UpdateFilePatternService(getFilePatternPort, updateFilePatternPort);
  }

  @Test
  void updateFilePatternWithIdOne() {
    FilePattern filePattern = new FilePattern();
    filePattern.setId(1L);
    filePattern.setPattern("**/*.java");
    filePattern.setInclusionType(InclusionType.INCLUDE);

    Mockito.when(getFilePatternPort.get(1L)).thenReturn(filePattern);

    UpdateFilePatternCommand command =
        new UpdateFilePatternCommand("**/*.java", InclusionType.INCLUDE);
    testSubject.updateFilePattern(command, filePattern.getId());

    Mockito.verify(updateFilePatternPort, Mockito.times(1)).updateFilePattern(filePattern);
  }
}
