package io.reflectoring.coderadar.projectadministration.filepattern;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.DeleteFilePatternPort;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.GetFilePatternPort;
import io.reflectoring.coderadar.projectadministration.service.filepattern.DeleteFilePatternService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DeleteFilePatternServiceTest {
  private DeleteFilePatternPort port = mock(DeleteFilePatternPort.class);
  private GetFilePatternPort getFilePatternPort = mock(GetFilePatternPort.class);

  @Test
  void deleteFilePatternWithIdOne() {
    DeleteFilePatternService testSubject = new DeleteFilePatternService(port);

    Mockito.when(getFilePatternPort.get(anyLong())).thenReturn(new FilePattern());
    testSubject.delete(1L);

    Mockito.verify(port, Mockito.times(1)).delete(1L);
  }
}
