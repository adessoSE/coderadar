package io.reflectoring.coderadar.rest.query;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.create.CreateFilePatternCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.query.domain.FileTree;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.IdResponse;
import java.net.URL;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

public class GetFileTreeForCommitControllerTest extends ControllerTestTemplate {

  long projectId;

  @BeforeEach
  void setUp() throws Exception {
    URL testRepoURL = this.getClass().getClassLoader().getResource("test-repository");

    CreateProjectCommand command1 =
        new CreateProjectCommand(
            "test-project",
            "username",
            "password",
            Objects.requireNonNull(testRepoURL).toString(),
            false,
            null,
            null);
    MvcResult result =
        mvc()
            .perform(
                post("/projects").contentType(MediaType.APPLICATION_JSON).content(toJson(command1)))
            .andReturn();

    projectId = fromJson(result.getResponse().getContentAsString(), IdResponse.class).getId();
  }

  @Test
  void testGetFileTree() throws Exception {
    CreateFilePatternCommand command =
        new CreateFilePatternCommand("**/*.java", InclusionType.INCLUDE);
    mvc()
        .perform(
            post("/projects/" + projectId + "/filePatterns")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(command)))
        .andExpect(status().isCreated())
        .andReturn();

    MvcResult result =
        mvc()
            .perform(
                get(
                    "/projects/"
                        + projectId
                        + "/files/tree/d3272b3793bc4b2bc36a1a3a7c8293fcf8fe27df?changedOnly=false"))
            .andDo(
                document(
                    "files/tree",
                    responseFields(
                        fieldWithPath("path")
                            .description("The path or filename of the current node"),
                        subsectionWithPath("children")
                            .description(
                                "Contains any subdirectories and files in the current path"))))
            .andReturn();

    FileTree fileTree = fromJson(result.getResponse().getContentAsString(), FileTree.class);

    Assertions.assertEquals("/", fileTree.getPath());
    Assertions.assertEquals(2, fileTree.getChildren().size());
    Assertions.assertEquals(
        "GetMetricsForCommitCommand.java", fileTree.getChildren().get(0).getPath());
    Assertions.assertNull(fileTree.getChildren().get(0).getChildren());
    Assertions.assertEquals(
        "testModule1/NewRandomFile.java", fileTree.getChildren().get(1).getPath());
    Assertions.assertNull(fileTree.getChildren().get(1).getChildren());
  }

  @Test
  void testGetOnlyFilesChangedInCommit() throws Exception {
    CreateFilePatternCommand command =
        new CreateFilePatternCommand("**/*.java", InclusionType.INCLUDE);
    mvc()
        .perform(
            post("/projects/" + projectId + "/filePatterns")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(command)))
        .andExpect(status().isCreated())
        .andReturn();

    MvcResult result =
        mvc()
            .perform(
                get(
                    "/projects/"
                        + projectId
                        + "/files/tree/d3272b3793bc4b2bc36a1a3a7c8293fcf8fe27df?changedOnly=true"))
            .andReturn();

    FileTree fileTree = fromJson(result.getResponse().getContentAsString(), FileTree.class);

    Assertions.assertEquals("/", fileTree.getPath());
    Assertions.assertEquals(1, fileTree.getChildren().size());
    Assertions.assertEquals(
        "testModule1/NewRandomFile.java", fileTree.getChildren().get(0).getPath());
    Assertions.assertNull(fileTree.getChildren().get(0).getChildren());
  }
}
