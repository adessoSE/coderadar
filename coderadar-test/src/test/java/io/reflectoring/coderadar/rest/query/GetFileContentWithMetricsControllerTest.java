package io.reflectoring.coderadar.rest.query;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.AnalyzerConfigurationRepository;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.query.domain.FileContentWithMetrics;
import io.reflectoring.coderadar.query.port.driver.filecontent.GetFileContentWithMetricsCommand;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.IdResponse;
import java.net.URL;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

class GetFileContentWithMetricsControllerTest extends ControllerTestTemplate {

  long projectId;

  @Autowired private AnalyzerConfigurationRepository configurationRepository;

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
            "master");
    MvcResult result =
        mvc()
            .perform(
                post("/api/projects")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(command1)))
            .andReturn();

    projectId = fromJson(result.getResponse().getContentAsString(), IdResponse.class).getId();
    configurationRepository.deleteAll();

    CreateAnalyzerConfigurationCommand command3 =
        new CreateAnalyzerConfigurationCommand(
            "io.reflectoring.coderadar.analyzer.loc.LocAnalyzerPlugin", true);
    mvc()
        .perform(
            post("/api/projects/" + projectId + "/analyzers")
                .content(toJson(command3))
                .contentType(MediaType.APPLICATION_JSON));

    mvc()
        .perform(
            post("/api/projects/" + projectId + "/analyze")
                .contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void testGetFileContentWithMetrics() throws Exception {
    GetFileContentWithMetricsCommand getFileContentWithMetricsCommand =
        new GetFileContentWithMetricsCommand(
            "d3272b3793bc4b2bc36a1a3a7c8293fcf8fe27df", "GetMetricsForCommitCommand.java");

    MvcResult result =
        mvc()
            .perform(
                get("/api/projects/" + projectId + "/files/content")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(getFileContentWithMetricsCommand)))
            .andDo(
                document(
                    "files/content",
                    requestFields(
                        fieldWithPath("commitHash")
                            .description("The commit whose file tree to search in."),
                        fieldWithPath("filepath").description("The path of the file")),
                    responseFields(
                        fieldWithPath("content").description("The content of the file as a string"),
                        subsectionWithPath("metrics")
                            .description(
                                "A list of metrics containing the metrics name, value and location in the file"))))
            .andReturn();

    FileContentWithMetrics fileContentWithMetrics =
        fromJson(result.getResponse().getContentAsString(), FileContentWithMetrics.class);

    Assertions.assertEquals(
        "package io.reflectoring.coderadar.query.port.driver;\n"
            + "\n"
            + "import lombok.AllArgsConstructor;\n"
            + "import lombok.Data;\n"
            + "import lombok.NoArgsConstructor;\n"
            + "\n"
            + "import javax.validation.constraints.NotEmpty;\n"
            + "import javax.validation.constraints.NotNull;\n"
            + "import java.util.List;\n"
            + "\n"
            + "@Data\n"
            + "@NoArgsConstructor\n"
            + "@AllArgsConstructor\n"
            + "public class GetMetricsForCommitCommand {\n"
            + "  @NotNull @NotEmpty String commitChange;\n"
            + "  @NotNull List<String> metrics;\n"
            + "}\n",
        fileContentWithMetrics.getContent());

    Assertions.assertEquals(
        "coderadar:size:eloc:java", fileContentWithMetrics.getMetrics().get(0).getName());
    Assertions.assertEquals(7, fileContentWithMetrics.getMetrics().get(0).getValue());
    Assertions.assertTrue(fileContentWithMetrics.getMetrics().get(0).getFindings().isEmpty());

    Assertions.assertEquals(
        "coderadar:size:loc:java", fileContentWithMetrics.getMetrics().get(1).getName());
    Assertions.assertEquals(17, fileContentWithMetrics.getMetrics().get(1).getValue());
    Assertions.assertTrue(fileContentWithMetrics.getMetrics().get(1).getFindings().isEmpty());

    Assertions.assertEquals(
        "coderadar:size:sloc:java", fileContentWithMetrics.getMetrics().get(2).getName());
    Assertions.assertEquals(14, fileContentWithMetrics.getMetrics().get(2).getValue());
    Assertions.assertTrue(fileContentWithMetrics.getMetrics().get(2).getFindings().isEmpty());
  }
}
