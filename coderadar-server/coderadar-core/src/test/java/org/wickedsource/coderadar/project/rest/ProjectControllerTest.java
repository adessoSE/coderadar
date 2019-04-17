package org.wickedsource.coderadar.project.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.EMPTY;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.Projects.*;
import static org.wickedsource.coderadar.factories.resources.ResourceFactory.projectResource;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.toJson;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.toJsonWithoutLinks;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.*;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultHandler;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;
import org.wickedsource.coderadar.testframework.template.ControllerTestTemplate;

public class ProjectControllerTest extends ControllerTestTemplate {

  @Autowired private ProjectRepository projectRepository;

  @Test
  @DatabaseSetup(EMPTY)
  public void createProjectSuccessfully() throws Exception {
    ProjectResource project = projectResource().validProjectResource();
    mvc()
        .perform(
            post("/projects")
                .content(toJsonWithoutLinks(project))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(containsResource(ProjectResource.class))
        .andDo(documentCreateProject());

    Optional<Project> savedProjectOptional = projectRepository.findById(1L);
    assertThat(savedProjectOptional.isPresent()).isTrue();
    Project savedProject = savedProjectOptional.get();

    assertThat(savedProject).isNotNull();
    assertThat(savedProject.getWorkdirName()).isNotEmpty();
    assertThat(savedProject.getName()).isEqualTo(project.getName());
    assertThat(savedProject.getVcsCoordinates().getUsername()).isEqualTo(project.getVcsUser());
    assertThat(savedProject.getVcsCoordinates().getPassword()).isEqualTo(project.getVcsPassword());
    assertThat(savedProject.getVcsCoordinates().getUrl().toString()).isEqualTo(project.getVcsUrl());
  }

  private ResultHandler documentCreateProject() {
    ConstrainedFields fields = fields(ProjectResource.class);
    return document(
        "projects/create",
        requestFields(
            fields.withPath("id").description("The id of the project."),
            fields.withPath("name").description("The name of the project to be analyzed."),
            fields
                .withPath("vcsUrl")
                .description(
                    "The URL to the version control repository where the project's source files are kept."),
            fields
                .withPath("vcsUser")
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

  @Test
  @DatabaseSetup(SINGLE_PROJECT)
  @ExpectedDatabase(SINGLE_PROJECT)
  public void createProjectWithValidationError() throws Exception {
    ProjectResource projectResource = projectResource().validProjectResource();
    projectResource.setName(null);
    projectResource.setVcsUrl("invalid url");

    ConstrainedFields fields = fields(ProjectResource.class);
    mvc()
        .perform(
            post("/projects")
                .content(toJson(projectResource))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(validationErrorForField("name"))
        .andExpect(validationErrorForField("vcsUrl"))
        .andDo(
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
                        .description("Reason why the value is invalid."))));
  }

  @Test
  @DatabaseSetup(SINGLE_PROJECT)
  @ExpectedDatabase(SINGLE_PROJECT_2)
  public void updateProject() throws Exception {
    ProjectResource projectResource = projectResource().validProjectResource2();
    mvc()
        .perform(
            post("/projects/1")
                .content(toJson(projectResource))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(containsResource(ProjectResource.class))
        .andDo(document("projects/update"));
  }

  @Test
  @DatabaseSetup(PROJECT_LIST)
  @ExpectedDatabase(PROJECT_LIST)
  public void updateProjectWhenNameExistsFails() throws Exception {
    ProjectResource projectResource = projectResource().validProjectResource2();
    mvc()
        .perform(
            post("/projects/1")
                .content(toJson(projectResource))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(500))
        .andExpect(
            result -> {
              Assertions.assertEquals(
                  "{\"errorMessage\":\"Project with name 'name2' already exists. Please choose another name.\"}",
                  result.getResponse().getContentAsString());
            })
        .andDo(document("projects/update"));
  }

  @Test
  @DatabaseSetup(SINGLE_PROJECT)
  @ExpectedDatabase(SINGLE_PROJECT)
  public void getProjectSuccessfully() throws Exception {
    mvc()
        .perform(get("/projects/1"))
        .andExpect(status().isOk())
        .andExpect(containsResource(ProjectResource.class))
        .andDo(document("projects/get"));
  }

  @Test
  @DatabaseSetup(SINGLE_PROJECT)
  @ExpectedDatabase(EMPTY)
  public void deleteProject() throws Exception {
    mvc()
        .perform(delete("/projects/1"))
        .andExpect(status().isOk())
        .andDo(document("projects/delete"));
  }

  @Test
  @DatabaseSetup(PROJECT_LIST)
  @ExpectedDatabase(PROJECT_LIST)
  public void getProjectsSuccessfully() throws Exception {
    mvc()
        .perform(get("/projects"))
        .andExpect(status().isOk())
        .andExpect(containsResource(List.class))
        .andDo(document("projects/list"));
  }

  @Test
  @DatabaseSetup(EMPTY)
  @ExpectedDatabase(EMPTY)
  public void getProjectError() throws Exception {
    mvc()
        .perform(get("/projects/1"))
        .andExpect(status().isNotFound())
        .andDo(document("projects/get/error404"));
  }
}
