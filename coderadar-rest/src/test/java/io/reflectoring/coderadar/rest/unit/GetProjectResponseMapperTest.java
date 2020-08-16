package io.reflectoring.coderadar.rest.unit;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.rest.GetProjectResponseMapper;
import io.reflectoring.coderadar.rest.domain.GetProjectResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GetProjectResponseMapperTest {

  @Test
  void testProjectResponseMapper() {
    List<Project> projects = new ArrayList<>();
    projects.add(
        new Project()
            .setName("testProject1")
            .setVcsUsername("testUsername1")
            .setVcsPassword("testPassword1")
            .setId(1L)
            .setVcsStart(new Date(123L))
            .setVcsUrl("testUrl1"));

    projects.add(
        new Project()
            .setName("testProject2")
            .setVcsUsername("testUsername2")
            .setVcsPassword("testPassword2")
            .setId(2L)
            .setVcsStart(new Date(345L))
            .setVcsUrl("testUrl2"));

    List<GetProjectResponse> responses = GetProjectResponseMapper.mapProjects(projects);
    Assertions.assertEquals(2L, responses.size());

    Assertions.assertEquals("testProject1", responses.get(0).getName());
    Assertions.assertEquals(1L, responses.get(0).getId());
    Assertions.assertEquals(new Date(123L), responses.get(0).getStartDate());
    Assertions.assertEquals("testUsername1", responses.get(0).getVcsUsername());
    Assertions.assertNull(responses.get(0).getVcsPassword());
    Assertions.assertEquals("testUrl1", responses.get(0).getVcsUrl());

    Assertions.assertEquals("testProject2", responses.get(1).getName());
    Assertions.assertEquals(2L, responses.get(1).getId());
    Assertions.assertEquals(new Date(345L), responses.get(1).getStartDate());
    Assertions.assertEquals("testUsername2", responses.get(1).getVcsUsername());
    Assertions.assertNull(responses.get(1).getVcsPassword());
    Assertions.assertEquals("testUrl2", responses.get(1).getVcsUrl());
  }

  @Test
  void testSingleProjectResponseMapper() {
    Project testProject =
        new Project()
            .setName("testProject1")
            .setVcsUsername("testUsername1")
            .setVcsPassword("testPassword1")
            .setId(1L)
            .setVcsStart(new Date(123L))
            .setVcsUrl("testUrl1");

    GetProjectResponse response = GetProjectResponseMapper.mapProject(testProject);

    Assertions.assertEquals("testProject1", response.getName());
    Assertions.assertEquals(1L, response.getId());
    Assertions.assertEquals(new Date(123L), response.getStartDate());
    Assertions.assertEquals("testUsername1", response.getVcsUsername());
    Assertions.assertNull(response.getVcsPassword());
    Assertions.assertEquals("testUrl1", response.getVcsUrl());
  }
}
