package io.reflectoring.coderadar.rest.integration.project;

import static io.reflectoring.coderadar.rest.integration.ResultMatchers.containsResource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.get.GetProjectResponse;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

class GetProjectControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired
  private CreateProjectRepository createProjectRepository;

  private  Project testProject;

  @BeforeEach
  public void setUp() throws MalformedURLException {
    testProject = new Project();
    testProject.setVcsUrl(new URL("https://valid.url"));
    testProject.setName("project");
    testProject.setVcsEnd(new Date());
    testProject.setVcsStart(new Date());
    testProject.setVcsOnline(true);
    testProject.setVcsPassword("testPassword");
    testProject.setVcsUsername("testUser");
    createProjectRepository.save(testProject);
  }

  @Test
  void getProjectWithIdOne() throws Exception {
    mvc().perform(get("/projects/0"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(containsResource(GetProjectResponse.class));
  }

  @Test
  void getProjectWithIdThrowsErrorWhenProjectDoesNotExist() throws Exception {
    mvc().perform(get("/projects/1"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().string("Project with id 1 not found."));
  }
}
