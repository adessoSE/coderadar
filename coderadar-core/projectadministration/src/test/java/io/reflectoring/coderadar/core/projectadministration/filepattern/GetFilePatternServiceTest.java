package io.reflectoring.coderadar.core.projectadministration.filepattern;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.core.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.GetFilePatternPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.get.GetFilePatternResponse;
import io.reflectoring.coderadar.core.projectadministration.service.filepattern.GetFilePatternService;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GetFilePatternServiceTest {
  private GetFilePatternPort port = mock(GetFilePatternPort.class);

  @Test
  void returnsGetFilePatternResponseForFilePattern() {
    GetFilePatternService testSubject = new GetFilePatternService(port);

    FilePattern filePattern = new FilePattern();
    filePattern.setId(1L);
    filePattern.setPattern("**/*.java");
    filePattern.setInclusionType(InclusionType.INCLUDE);

    Mockito.when(port.get(1L)).thenReturn(Optional.of(filePattern));

    GetFilePatternResponse response = testSubject.get(1L);

    Assertions.assertEquals(filePattern.getId(), response.getId());
    Assertions.assertEquals(filePattern.getPattern(), response.getPattern());
    Assertions.assertEquals(filePattern.getInclusionType(), response.getInclusionType());
  }
}
