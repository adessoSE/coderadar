package io.reflectoring.coderadar.projectadministration.filepattern;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.DeleteFilePatternPort;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.GetFilePatternPort;
import io.reflectoring.coderadar.projectadministration.service.filepattern.DeleteFilePatternService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteFilePatternServiceTest {

  @Mock private DeleteFilePatternPort port;
  @Mock private GetFilePatternPort getFilePatternPort;

  private DeleteFilePatternService testSubject;

  @BeforeEach
  void setUp() {
    this.testSubject = new DeleteFilePatternService(port, getFilePatternPort);
    FilePattern filePattern = new FilePattern();
    filePattern.setInclusionType(InclusionType.EXCLUDE);
    when(getFilePatternPort.get(anyLong())).thenReturn(filePattern);
  }

  @Test
  void deleteFilePatternDeletesPatternWithGivenId() {
    // given
    long patternId = 1L;

    // when
    testSubject.delete(patternId, 2L);

    // then
    verify(port).delete(1L);
  }
}
