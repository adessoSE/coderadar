package io.reflectoring.coderadar.core.projectadministration.filepattern;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.DeleteFilePatternPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.GetFilePatternPort;
import io.reflectoring.coderadar.core.projectadministration.service.filepattern.DeleteFilePatternService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class DeleteFilePatternServiceTest {
  @Mock private DeleteFilePatternPort port;
  @Mock private GetFilePatternPort getFilePatternPort;

  @InjectMocks private DeleteFilePatternService testSubject;

  @Test
  void deleteFilePatternWithIdOne() {
    Mockito.when(getFilePatternPort.get(anyLong())).thenReturn(java.util.Optional.of(new FilePattern()));
    testSubject.delete(1L);

    Mockito.verify(port, Mockito.times(1)).delete(1L);
  }
}
