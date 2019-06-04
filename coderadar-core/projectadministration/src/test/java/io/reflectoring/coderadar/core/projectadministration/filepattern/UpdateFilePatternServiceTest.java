package io.reflectoring.coderadar.core.projectadministration.filepattern;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.core.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.GetFilePatternPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.UpdateFilePatternPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.update.UpdateFilePatternCommand;
import io.reflectoring.coderadar.core.projectadministration.service.filepattern.UpdateFilePatternService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UpdateFilePatternServiceTest {
  private GetFilePatternPort getFilePatternPort = mock(GetFilePatternPort.class);
  private UpdateFilePatternPort updateFilePatternPort = mock(UpdateFilePatternPort.class);

  @Test
  void updateFilePatternWithIdOne() {
    UpdateFilePatternService testSubject =
        new UpdateFilePatternService(getFilePatternPort, updateFilePatternPort);

    FilePattern filePattern = new FilePattern();
    filePattern.setId(1L);
    filePattern.setPattern("**/*.java");
    filePattern.setInclusionType(InclusionType.INCLUDE);

    Mockito.when(getFilePatternPort.get(1L)).thenReturn(Optional.of(filePattern));

    UpdateFilePatternCommand command =
        new UpdateFilePatternCommand("**/*.java", InclusionType.INCLUDE);
    testSubject.updateFilePattern(command, filePattern.getId());

    Mockito.verify(updateFilePatternPort, Mockito.times(1)).updateFilePattern(filePattern);
  }
}
