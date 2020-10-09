package io.reflectoring.coderadar.rest.project;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.FileRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.FileEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.IdResponse;
import java.net.URL;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultHandler;

class CreateProjectControllerIntegrationTest extends ControllerTestTemplate {
  @Autowired private ProjectRepository projectRepository;
  @Autowired private FileRepository fileRepository;
  @Autowired private CommitRepository commitRepository;

  @Test
  void createProjectSuccessfully() throws Exception {
    URL testRepoURL = this.getClass().getClassLoader().getResource("test-repository");
    CreateProjectCommand command =
        new CreateProjectCommand(
            "project", "username", "password", testRepoURL.toString(), false, null, "master");
    mvc()
        .perform(
            post("/api/projects").contentType(MediaType.APPLICATION_JSON).content(toJson(command)))
        .andExpect(status().isCreated())
        .andDo(
            result -> {
              long id =
                  fromJson(result.getResponse().getContentAsString(), IdResponse.class).getId();
              ProjectEntity project = projectRepository.findById(id).get();
              Assertions.assertEquals("project", project.getName());
              Assertions.assertEquals("username", project.getVcsUsername());
              Assertions.assertNotEquals("password", new String(project.getVcsPassword()));
              Assertions.assertEquals(testRepoURL.toString(), project.getVcsUrl());
              Assertions.assertEquals("master", project.getDefaultBranch());
              List<CommitEntity> commits =
                  commitRepository.findByProjectIdAndBranchName(id, "master");
              Assertions.assertEquals(14, commits.size());
              List<FileEntity> files = fileRepository.findAllinProject(id);
              Assertions.assertEquals(9, files.size());
            })
        .andDo(documentCreateProject());
  }

  @Test
  void createProjectReturnsErrorOnInvalidData() throws Exception {
    CreateProjectCommand command =
        new CreateProjectCommand(
            "project", "username", "password", "invalid", true, new Date(), "master");
    mvc()
        .perform(
            post("/api/projects").contentType(MediaType.APPLICATION_JSON).content(toJson(command)))
        .andExpect(status().isBadRequest())
        .andDo(document("projects/create/error400"));
  }

  private ResultHandler documentCreateProject() {
    ConstrainedFields<CreateProjectCommand> fields = fields(CreateProjectCommand.class);
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
            fields.withPath("defaultBranch").description("The default branch for the project."),
            fields
                .withPath("startDate")
                .type("Date")
                .description(
                    "The start date of the range of commits which should be analyzed by coderadar. Leave empty to start at the first commit.")));
  }
}
