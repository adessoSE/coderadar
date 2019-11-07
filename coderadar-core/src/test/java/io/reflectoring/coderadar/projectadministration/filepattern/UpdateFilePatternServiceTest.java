package io.reflectoring.coderadar.projectadministration.filepattern;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.GetFilePatternPort;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.UpdateFilePatternPort;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.update.UpdateFilePatternCommand;
import io.reflectoring.coderadar.projectadministration.service.filepattern.UpdateFilePatternService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateFilePatternServiceTest {

  @Mock private GetFilePatternPort getFilePatternPortMock;

  @Mock private UpdateFilePatternPort updateFilePatternPortMock;

  private UpdateFilePatternService testSubject;

  @BeforeEach
  void setUp() {
    this.testSubject =
        new UpdateFilePatternService(getFilePatternPortMock, updateFilePatternPortMock);
  }

  @Test
  void updateFilePatternUpdatesPatternAndInclusionType(@Mock FilePattern existingFilePatternMock) {
    // given
    long patternId = 1L;
    String newPattern = "**/*.java";
    InclusionType newInclusionType = InclusionType.EXCLUDE;

    UpdateFilePatternCommand command = new UpdateFilePatternCommand(newPattern, newInclusionType);

    when(getFilePatternPortMock.get(patternId)).thenReturn(existingFilePatternMock);

    // when
    testSubject.updateFilePattern(command, patternId);

    // then
    verify(existingFilePatternMock, never()).setId(anyLong());
    verify(existingFilePatternMock).setPattern(newPattern);
    verify(existingFilePatternMock).setInclusionType(newInclusionType);

    verify(updateFilePatternPortMock).updateFilePattern(existingFilePatternMock);
  }
}
