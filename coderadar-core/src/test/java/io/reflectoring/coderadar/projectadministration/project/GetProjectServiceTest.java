package io.reflectoring.coderadar.projectadministration.project;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.get.GetProjectResponse;
import io.reflectoring.coderadar.projectadministration.service.project.GetProjectService;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetProjectServiceTest {

  @Mock private GetProjectPort getProjectPortMock;

  private GetProjectService testSubject;

  @BeforeEach
  void setUp() {
    this.testSubject = new GetProjectService(getProjectPortMock);
  }

  @Test
  void returnsGetProjectResponseWithIdOne() {
    // given
    long projectId = 1L;
    String projectName = "project name";
    String workdirName = "workdir-name";
    String vcsUrl = "http://valid.url";
    String vcsUsername = "username";
    String vcsPassword = "password";
    Date startDate = new Date();
    Date endDate = new Date();

    Project project =
        new Project()
            .setId(projectId)
            .setName(projectName)
            .setWorkdirName(workdirName)
            .setVcsUrl(vcsUrl)
            .setVcsUsername(vcsUsername)
            .setVcsPassword(vcsPassword)
            .setVcsOnline(true)
            .setVcsStart(startDate)
            .setVcsEnd(endDate);

    GetProjectResponse expectedResponse =
        new GetProjectResponse()
            .setId(projectId)
            .setName(projectName)
            .setVcsUrl(vcsUrl)
            .setVcsUsername(vcsUsername)
            .setVcsPassword(vcsPassword)
            .setVcsOnline(true)
            .setStartDate(startDate)
            .setEndDate(endDate);

    when(getProjectPortMock.get(projectId)).thenReturn(project);

    // when
    GetProjectResponse actualResponse = testSubject.get(1L);

    // then
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }
}
