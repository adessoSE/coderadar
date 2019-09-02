package io.reflectoring.coderadar.projectadministration.filepattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.ListFilePatternsOfProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get.GetFilePatternResponse;
import io.reflectoring.coderadar.projectadministration.service.filepattern.ListFilePatternsOfProjectService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ListFilePatternsOfProjectServiceTest {

  @Mock private ListFilePatternsOfProjectPort listPatternsPortMock;

  private ListFilePatternsOfProjectService testSubject;

  @BeforeEach
  void setUp() {
    this.testSubject = new ListFilePatternsOfProjectService(listPatternsPortMock);
  }

  @Test
  void returnsTwoFilePatternsFromProject() {
    // given
    long projectId = 123L;

    FilePattern filePattern1 =
        new FilePattern().setId(1L).setPattern("**/*.java").setInclusionType(InclusionType.INCLUDE);
    FilePattern filePattern2 =
        new FilePattern().setId(2L).setPattern("**/*.xml").setInclusionType(InclusionType.EXCLUDE);

    List<FilePattern> filePatterns = new ArrayList<>();
    filePatterns.add(filePattern1);
    filePatterns.add(filePattern2);

    GetFilePatternResponse expectedResponse1 =
        new GetFilePatternResponse(1L, "**/*.java", InclusionType.INCLUDE);
    GetFilePatternResponse expectedResponse2 =
        new GetFilePatternResponse(2L, "**/*.xml", InclusionType.EXCLUDE);

    when(listPatternsPortMock.listFilePatterns(projectId)).thenReturn(filePatterns);

    // when
    List<GetFilePatternResponse> actualResponse = testSubject.listFilePatterns(projectId);

    // then
    assertThat(actualResponse).containsExactly(expectedResponse1, expectedResponse2);
  }
}
