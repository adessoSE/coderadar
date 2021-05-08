package io.reflectoring.coderadar.projectadministration.filepattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import io.reflectoring.coderadar.domain.FilePattern;
import io.reflectoring.coderadar.domain.InclusionType;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.GetFilePatternPort;
import io.reflectoring.coderadar.projectadministration.service.filepattern.GetFilePatternService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetFilePatternServiceTest {

  @Mock private GetFilePatternPort port;

  private GetFilePatternService testSubject;

  @BeforeEach
  void setUp() {
    this.testSubject = new GetFilePatternService(port);
  }

  @Test
  void returnsGetFilePatternResponseForFilePattern() {
    // given
    long patternId = 123L;
    String pattern = "**/*.java";
    InclusionType inclusionType = InclusionType.EXCLUDE;

    FilePattern filePattern =
        new FilePattern().setId(patternId).setPattern(pattern).setInclusionType(inclusionType);

    FilePattern expectedResponse = new FilePattern(patternId, pattern, inclusionType);

    when(port.get(patternId)).thenReturn(filePattern);

    // when
    FilePattern actualResponse = testSubject.get(patternId);

    // then
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }
}
