package io.reflectoring.coderadar.core.projectadministration.filepattern;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.DeleteFilePatternPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.GetFilePatternPort;
import io.reflectoring.coderadar.core.projectadministration.service.filepattern.DeleteFilePatternService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;

class DeleteFilePatternServiceTest {
  private DeleteFilePatternPort port = mock(DeleteFilePatternPort.class);
  private GetFilePatternPort getFilePatternPort = mock(GetFilePatternPort.class);

  @Test
  void deleteFilePatternWithIdOne() {
    DeleteFilePatternService testSubject = new DeleteFilePatternService(port, getFilePatternPort);

    Mockito.when(getFilePatternPort.get(anyLong()))
        .thenReturn(java.util.Optional.of(new FilePattern()));
    testSubject.delete(1L);

    Mockito.verify(port, Mockito.times(1)).delete(1L);
  }
}
