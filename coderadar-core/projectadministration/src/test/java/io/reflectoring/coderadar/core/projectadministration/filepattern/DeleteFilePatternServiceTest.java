package io.reflectoring.coderadar.core.projectadministration.filepattern;

import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.DeleteFilePatternPort;
import io.reflectoring.coderadar.core.projectadministration.service.filepattern.DeleteFilePatternService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class DeleteFilePatternServiceTest {
  @Mock private DeleteFilePatternPort port;
  private DeleteFilePatternService testSubject;

  @BeforeEach
  void setup() {
    testSubject = new DeleteFilePatternService(port);
  }

  @Test
  void deleteFilePatternWithIdOne() {
    testSubject.delete(1L);

    Mockito.verify(port, Mockito.times(1)).delete(1L);
  }
}
