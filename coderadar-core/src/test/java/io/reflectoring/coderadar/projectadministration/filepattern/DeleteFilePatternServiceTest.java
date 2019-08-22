package io.reflectoring.coderadar.projectadministration.filepattern;

import static org.mockito.Mockito.verify;

import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.DeleteFilePatternPort;
import io.reflectoring.coderadar.projectadministration.service.filepattern.DeleteFilePatternService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteFilePatternServiceTest {

  @Mock private DeleteFilePatternPort port;

  private DeleteFilePatternService testSubject;

  @BeforeEach
  void setUp() {
    this.testSubject = new DeleteFilePatternService(port);
  }

  @Test
  void deleteFilePatternDeletesPatternWithGivenId() {
    // given
    long patternId = 1L;

    // when
    testSubject.delete(patternId);

    // then
    verify(port).delete(1L);
  }
}
