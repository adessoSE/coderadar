package io.reflectoring.coderadar.rest.integration.project;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.projectadministration.port.driver.project.get.GetProjectResponse;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static io.reflectoring.coderadar.rest.integration.JsonHelper.fromJson;
import static io.reflectoring.coderadar.rest.integration.ResultMatchers.containsResource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class GetProjectControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private CreateProjectRepository createProjectRepository;

  @Test
  void getProjectWithId() throws Exception {

    ProjectEntity testProject = new ProjectEntity();
    testProject.setVcsUrl("https://valid.url");
    testProject.setName("project");
    testProject.setVcsEnd(new Date());
    testProject.setVcsStart(new Date());
    testProject.setVcsOnline(true);
    testProject.setVcsPassword("testPassword");
    testProject.setVcsUsername("testUser");
    testProject = createProjectRepository.save(testProject);

    mvc()
        .perform(get("/projects/" + testProject.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(containsResource(GetProjectResponse.class))
        .andDo(
            result -> {
              GetProjectResponse response =
                  fromJson(result.getResponse().getContentAsString(), GetProjectResponse.class);
              Assertions.assertEquals("project", response.getName());
            })
            .andDo(document("projects/get"));
  }

  @Test
  void getProjectWithIdThrowsErrorWhenProjectDoesNotExist() throws Exception {
    mvc()
        .perform(get("/projects/1"))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(
            MockMvcResultMatchers.jsonPath("errorMessage").value("Project with id 1 not found."));
  }
}
