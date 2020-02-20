package io.reflectoring.coderadar.rest.project;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class CreateProjectControllerIntegrationTest extends ControllerTestTemplate {
  @Autowired private ProjectRepository projectRepository;
  @Autowired private FileRepository fileRepository;
  @Autowired private CommitRepository commitRepository;

  @Test
  void createProjectSuccessfully() throws Exception {
    URL testRepoURL = this.getClass().getClassLoader().getResource("test-repository");
    CreateProjectCommand command =
        new CreateProjectCommand(
            "project", "username", "password", testRepoURL.toString(), false, null, null);
    mvc()
        .perform(post("/projects").contentType(MediaType.APPLICATION_JSON).content(toJson(command)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andDo(
            result -> {
              // FileUtils.deleteDirectory(new File("coderadar-workdir"));
              Long id =
                  fromJson(result.getResponse().getContentAsString(), IdResponse.class).getId();
              ProjectEntity project = projectRepository.findById(id).get();
              Assertions.assertEquals("project", project.getName());
              Assertions.assertEquals("username", project.getVcsUsername());
              Assertions.assertEquals("password", project.getVcsPassword());
              Assertions.assertEquals(testRepoURL.toString(), project.getVcsUrl());
              Assertions.assertFalse(project.isVcsOnline());
              List<CommitEntity> commits = commitRepository.findByProjectId(id);
              Assertions.assertEquals(13, commits.size());
              List<FileEntity> files = fileRepository.findAllinProject(id);
              Assertions.assertEquals(8, files.size());
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
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andDo(document("projects/create/error400"));
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
                .type("Date")
                .description(
                    "The start date of the range of commits which should be analyzed by coderadar. Leave empty to start at the first commit."),
            fields
                .withPath("endDate")
                .type("Date")
                .description(
                    "The end date of the range of commits which should be analyzed by coderadar. Leave empty to automatically process all new incoming commits.")));
  }
}
