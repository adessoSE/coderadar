package io.reflectoring.coderadar.rest.query;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.create.CreateFilePatternCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.query.domain.FileContentWithMetrics;
import io.reflectoring.coderadar.query.port.driver.filediff.GetFileDiffCommand;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.IdResponse;
import java.net.URL;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

public class GetFileDiffControllerTest extends ControllerTestTemplate {

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

    CreateFilePatternCommand command2 =
        new CreateFilePatternCommand("**/*.java", InclusionType.INCLUDE);
    mvc()
        .perform(
            post("/projects/" + projectId + "/filePatterns")
                .content(toJson(command2))
                .contentType(MediaType.APPLICATION_JSON));

    CreateAnalyzerConfigurationCommand command3 =
        new CreateAnalyzerConfigurationCommand(
            "io.reflectoring.coderadar.analyzer.loc.LocAnalyzerPlugin", true);
    mvc()
        .perform(
            post("/projects/" + projectId + "/analyzers")
                .content(toJson(command3))
                .contentType(MediaType.APPLICATION_JSON));

    mvc()
        .perform(
            post("/projects/" + projectId + "/master/analyze")
                .contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void testGetFileDiff() throws Exception {
    GetFileDiffCommand getFileDiffCommand =
        new GetFileDiffCommand(
            "e9f7ff6fdd8c0863fdb5b24c9ed35a3651e20382", "testModule1/NewRandomFile.java");

    MvcResult result =
        mvc()
            .perform(
                get("/projects/" + projectId + "/files/diff")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(getFileDiffCommand)))
            .andDo(
                document(
                    "files/diff",
                    requestFields(
                        fieldWithPath("commitHash")
                            .description("The commit whose file tree to search in."),
                        fieldWithPath("filepath").description("The path of the file")),
                    responseFields(
                        fieldWithPath("content")
                            .description("The diff against the same file in the parent commit"),
                        subsectionWithPath("metrics").description("Always null"))))
            .andReturn();

    FileContentWithMetrics fileContentWithMetrics =
        fromJson(result.getResponse().getContentAsString(), FileContentWithMetrics.class);

    Assertions.assertEquals(
        "@@ -1 +1,2 @@\n"
            + " public class this does not compile\n"
            + "+// This test still does not compile\n"
            + "\\ No newline at end of file\n",
        fileContentWithMetrics.getContent());

    Assertions.assertNull(fileContentWithMetrics.getMetrics());
  }

  @Test
  void testGetFileDiffIsEmptyIfFileNotChanged() throws Exception {
    GetFileDiffCommand getFileDiffCommand =
        new GetFileDiffCommand(
            "d3272b3793bc4b2bc36a1a3a7c8293fcf8fe27df", "GetMetricsForCommitCommand.java");

    MvcResult result =
        mvc()
            .perform(
                get("/projects/" + projectId + "/files/diff")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(getFileDiffCommand)))
            .andReturn();

    FileContentWithMetrics fileContentWithMetrics =
        fromJson(result.getResponse().getContentAsString(), FileContentWithMetrics.class);

    Assertions.assertEquals("", fileContentWithMetrics.getContent());

    Assertions.assertNull(fileContentWithMetrics.getMetrics());
  }
}
