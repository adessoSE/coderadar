package io.reflectoring.coderadar.rest.integration.project;

import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class ListProjectsControllerIntegrationTest extends ControllerTestTemplate {

  @Test
  void listAllProjects() throws Exception {
    mvc().perform(get("/projects"));
  }
}
