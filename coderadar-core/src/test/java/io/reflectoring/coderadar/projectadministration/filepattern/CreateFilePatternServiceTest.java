package io.reflectoring.coderadar.projectadministration.filepattern;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.CreateFilePatternPort;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.create.CreateFilePatternCommand;
import io.reflectoring.coderadar.projectadministration.service.filepattern.CreateFilePatternService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateFilePatternServiceTest {

  @Mock private CreateFilePatternPort createFilePatternPort;

  private CreateFilePatternService testSubject;

  @BeforeEach
  void setUp() {
    this.testSubject = new CreateFilePatternService(createFilePatternPort);
  }

  @Test
  void returnsNewFilePatternId() {
    // given
    long projectId = 1L;
    long expectedFilePatternId = 1337L;
    String pattern = "**/*.java";
    InclusionType inclusionType = InclusionType.INCLUDE;

    FilePattern filePattern = new FilePattern().setPattern(pattern).setInclusionType(inclusionType);

    CreateFilePatternCommand command = new CreateFilePatternCommand(pattern, inclusionType);

    when(createFilePatternPort.createFilePattern(filePattern, projectId))
        .thenReturn(expectedFilePatternId);

    // when
    Long filePatternId = testSubject.createFilePattern(command, projectId);

    // then
    assertThat(filePatternId).isEqualTo(expectedFilePatternId);
  }
}
