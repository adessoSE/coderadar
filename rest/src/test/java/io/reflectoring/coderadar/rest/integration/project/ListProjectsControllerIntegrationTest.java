package io.reflectoring.coderadar.rest.integration.project;

import static io.reflectoring.coderadar.rest.integration.ResultMatchers.containsResource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.get.GetProjectResponse;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class ListProjectsControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private CreateProjectRepository createProjectRepository;

  @BeforeEach
  public void setUp() {
    Project testProject = new Project();
    testProject.setVcsUrl("https://valid.url");
    testProject.setName("project");
    testProject.setVcsEnd(new Date());
    testProject.setVcsStart(new Date());
    testProject.setVcsOnline(true);
    testProject.setVcsPassword("testPassword");
    testProject.setVcsUsername("testUser");

    Project testProject2 = new Project();
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
        .andExpect(containsResource(GetProjectResponse[].class));
  }
}
