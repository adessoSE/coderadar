package io.reflectoring.coderadar.rest.integration.project;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.projectadministration.port.driver.project.get.GetProjectResponse;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static io.reflectoring.coderadar.rest.integration.JsonHelper.fromJson;
import static io.reflectoring.coderadar.rest.integration.ResultMatchers.containsResource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class ListProjectsControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private CreateProjectRepository createProjectRepository;

  @BeforeEach
  public void setUp() {
    createProjectRepository.deleteAll();

    ProjectEntity testProject = new ProjectEntity();
    testProject.setVcsUrl("https://valid.url");
    testProject.setName("project");
    testProject.setVcsEnd(new Date());
    testProject.setVcsStart(new Date());
    testProject.setVcsOnline(true);
    testProject.setVcsPassword("testPassword");
    testProject.setVcsUsername("testUser");

    ProjectEntity testProject2 = new ProjectEntity();
    testProject2.setVcsUrl("https://valid.url");
    testProject2.setName("project");
    testProject2.setVcsEnd(new Date());
    testProject2.setVcsStart(new Date());
    testProject2.setVcsOnline(true);
    testProject2.setVcsPassword("testPassword");
    testProject2.setVcsUsername("testUser");

    createProjectRepository.save(testProject);
    createProjectRepository.save(testProject2);
  }

  @Test
  void listAllProjects() throws Exception {
    mvc()
        .perform(get("/projects"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(containsResource(GetProjectResponse[].class))
        .andExpect(
            result -> {
              GetProjectResponse[] responses =
                  fromJson(result.getResponse().getContentAsString(), GetProjectResponse[].class);
              Assertions.assertEquals(2, responses.length);
            })
            .andDo(document("projects/list"));
  }
}
