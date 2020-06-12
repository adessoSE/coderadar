package io.reflectoring.coderadar.projectadministration.project;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ListProjectsPort;
import io.reflectoring.coderadar.projectadministration.service.project.ListProjectsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ListProjectsServiceTest {

  @Mock private ListProjectsPort listProjectsPort;

  private ListProjectsService testSubject;

  @BeforeEach
  void setUp() {
    this.testSubject = new ListProjectsService(listProjectsPort);
  }

  @Test
  void returnsTwoProjects() {
    // given
    Date startDate = new Date();
    Date endDate = new Date();

    Project expectedResponse1 =
        new Project()
            .setId(1L)
            .setName("project 1")
            .setVcsUrl("http://github.com")
            .setVcsUsername("username1")
            .setVcsPassword("password1")
            .setVcsOnline(true)
            .setVcsStart(startDate);

    Project expectedResponse2 =
        new Project()
            .setId(2L)
            .setName("project 2")
            .setVcsUrl("http://bitbucket.org")
            .setVcsUsername("username2")
            .setVcsPassword("password2")
            .setVcsOnline(false)
            .setVcsStart(startDate);

    Mockito.when(listProjectsPort.getProjects())
        .thenReturn(Arrays.asList(expectedResponse1, expectedResponse2));

    // when
    List<Project> actualResponses = testSubject.listProjects();

    // then
    assertThat(actualResponses).containsExactly(expectedResponse1, expectedResponse2);
  }
}
