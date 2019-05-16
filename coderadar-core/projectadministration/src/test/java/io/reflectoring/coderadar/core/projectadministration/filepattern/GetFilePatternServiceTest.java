package io.reflectoring.coderadar.core.projectadministration.filepattern;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.core.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.GetFilePatternPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.get.GetFilePatternResponse;
import io.reflectoring.coderadar.core.projectadministration.service.filepattern.GetFilePatternService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
class GetFilePatternServiceTest {
  @Mock private GetFilePatternPort port;
  @InjectMocks private GetFilePatternService testSubject;

  @Test
  void returnsGetFilePatternResponseForFilePattern() {
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
