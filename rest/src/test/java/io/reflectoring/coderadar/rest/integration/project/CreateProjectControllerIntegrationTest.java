package io.reflectoring.coderadar.rest.integration.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.rest.IdResponse;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.util.Date;

import static io.reflectoring.coderadar.rest.integration.JsonHelper.fromJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class CreateProjectControllerIntegrationTest extends ControllerTestTemplate {
  @Autowired private CreateProjectRepository createProjectRepository;

  @Test
  void createProjectSuccessfully() throws Exception {
    CreateProjectCommand command =
        new CreateProjectCommand(
            "project", "username", "password", "https://valid.url", true, new Date(), new Date());
    mvc()
        .perform(post("/projects").contentType(MediaType.APPLICATION_JSON).content(toJson(command)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andDo(
            result -> {
              FileUtils.deleteDirectory(new File("coderadar-workdir"));
              Long id =
                  fromJson(result.getResponse().getContentAsString(), IdResponse.class).getId();
              Project project = createProjectRepository.findById(id).get();
              Assertions.assertEquals("project", project.getName());
              Assertions.assertEquals("username", project.getVcsUsername());
              Assertions.assertEquals("password", project.getVcsPassword());
              Assertions.assertEquals("https://valid.url", project.getVcsUrl());
              Assertions.assertTrue(project.isVcsOnline());
            });
  }

  @Test
  void createProjectReturnsErrorOnInvalidData() throws Exception {
    CreateProjectCommand command =
        new CreateProjectCommand(
            "project", "username", "password", "invalid", true, new Date(), new Date());
    mvc()
        .perform(post("/projects").contentType(MediaType.APPLICATION_JSON).content(toJson(command)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}
