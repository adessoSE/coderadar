package io.reflectoring.coderadar.rest.integration.project;

import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.rest.IdResponse;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.util.Date;

import static io.reflectoring.coderadar.rest.integration.JsonHelper.fromJson;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
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
            })
            .andDo(documentCreateProject());
  }

  @Test
  void createProjectReturnsErrorOnInvalidData() throws Exception {
      ConstrainedFields fields = fields(CreateProjectCommand.class);

      CreateProjectCommand command =
        new CreateProjectCommand(
            "project", "username", "password", "invalid", true, new Date(), new Date());
    mvc()
        .perform(post("/projects").contentType(MediaType.APPLICATION_JSON).content(toJson(command)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
            /*.andDo(
                    document(
                            "projects/create/error400",
                            responseFields(
                                    fields
                                            .withPath("errorMessage")
                                            .description(
                                                    "Short message describing what went wrong. In case of validation errors, the detailed validation error messages can be found in the fieldErrors array."),
                                    fields
                                            .withPath("fieldErrors")
                                            .description(
                                                    "List of fields in the JSON payload of a request that had invalid values. May be empty. In this case, the 'message' field should contain an explanation of what went wrong."),
                                    fields
                                            .withPath("fieldErrors[].field")
                                            .description(
                                                    "Name of the field in the JSON payload of the request that had an invalid value."),
                                    fields
                                            .withPath("fieldErrors[].message")
                                            .description("Reason why the value is invalid."))));*/
  }

    private ResultHandler documentCreateProject() {
        ConstrainedFields fields = fields(CreateProjectCommand.class);
        return document(
                "projects/create",
                requestFields(
                        fields.withPath("name").description("The name of the project to be analyzed."),
                        fields
                                .withPath("vcsUrl")
                                .description(
                                        "The URL to the version control repository where the project's source files are kept."),
                        fields
                                .withPath("vcsUsername")
                                .description(
                                        "The user name used to access the version control system of your project. Needs read access only. Don't provide this field if anonymous access is possible."),
                        fields
                                .withPath("vcsPassword")
                                .description(
                                        "The password of the version control system user. This password has to be stored in plain text for coderadar to be usable, so make sure to provide a user with only reading permissions. Don't provide this field if anonymous access is possible."),
                        fields
                                .withPath("vcsOnline")
                                .description(
                                        "Set to false if you want no interaction with a remote repository for this project. True by default."),
                        fields
                                .withPath("startDate")
                                .description(
                                        "The start date of the range of commits which should be analyzed by coderadar. Leave empty to start at the first commit."),
                        fields
                                .withPath("endDate")
                                .description(
                                        "The end date of the range of commits which should be analyzed by coderadar. Leave empty to automatically process all new incoming commits.")));
    }
}
