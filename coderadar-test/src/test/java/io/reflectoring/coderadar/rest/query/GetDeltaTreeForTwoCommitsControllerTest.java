package io.reflectoring.coderadar.rest.query;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.create.CreateFilePatternCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.query.domain.Changes;
import io.reflectoring.coderadar.query.domain.DeltaTree;
import io.reflectoring.coderadar.query.domain.MetricTreeNodeType;
import io.reflectoring.coderadar.query.domain.MetricValueForCommit;
import io.reflectoring.coderadar.query.port.driver.deltatree.GetDeltaTreeForTwoCommitsCommand;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.ErrorMessageResponse;
import io.reflectoring.coderadar.rest.domain.IdResponse;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

class GetDeltaTreeForTwoCommitsControllerTest extends ControllerTestTemplate {

  private Long projectId;

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

    CreateFilePatternCommand command2 =
        new CreateFilePatternCommand("**/*.java", InclusionType.INCLUDE);
    mvc()
        .perform(
            post("/api/projects/" + projectId + "/filePatterns")
                .content(toJson(command2))
                .contentType(MediaType.APPLICATION_JSON));

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
  void returnsDeltaTreeForFirstAndLatestCommit() throws Exception {
    GetDeltaTreeForTwoCommitsCommand command = new GetDeltaTreeForTwoCommitsCommand();
    command.setMetrics(
        Arrays.asList(
            "coderadar:size:loc:java",
            "coderadar:size:sloc:java",
            "coderadar:size:cloc:java",
            "coderadar:size:eloc:java"));
    command.setCommit1("fd68136dd6489504e829b11f2fce1fe97c9f5c0c");
    command.setCommit2("d3272b3793bc4b2bc36a1a3a7c8293fcf8fe27df");

    ConstrainedFields fields = fields(GetDeltaTreeForTwoCommitsCommand.class);
    MvcResult result =
        mvc()
            .perform(
                get("/api/projects/" + projectId + "/metricvalues/deltaTree")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(command)))
            .andDo(
                document(
                    "metrics/deltaTree",
                    requestFields(
                        fields
                            .withPath("commit1")
                            .description("First commit to get the metrics for."),
                        fields
                            .withPath("commit2")
                            .description("Second commit to get the metrics for."),
                        fields.withPath("metrics").description("List of Metrics to query.")),
                    responseFields(
                        fieldWithPath("name")
                            .description(
                                "The name of the file or module, containing the full path."),
                        fieldWithPath("type")
                            .description(
                                "Either 'MODULE' if this node describes a module which can have child nodes or 'FILE' if this node describes a file (which has no child nodes)."),
                        subsectionWithPath("commit1Metrics")
                            .description(
                                "Contains a map of metric values for each of the metrics specified in the query at the time of the commit specified in the request. If this node is a MODULE, the metrics are aggregated over all files within this module."),
                        subsectionWithPath("commit2Metrics")
                            .description(
                                "Contains a map of metric values for each of the metrics specified in the query at the time of the commit specified in the request. If this node is a MODULE, the metrics are aggregated over all files within this module."),
                        subsectionWithPath("children")
                            .description(
                                "If this node describes a MODULE, this field contains the list of child nodes of the same structure, which can be of type MODULE or FILE."),
                        fieldWithPath("renamedFrom")
                            .type("String")
                            .description(
                                "The old path of the file, if it was renamed. Null otherwise. This attribute is only set for the new file."),
                        fieldWithPath("renamedTo")
                            .type("String")
                            .description(
                                "The new path of the file, if it was renamed. Null otherwise. This attribute is only set for the old file."),
                        fieldWithPath("changes")
                            .type("Changes")
                            .description(
                                "One of the following changes or none of them: 'renamed', 'modified', 'added', 'deleted'."))))
            .andReturn();

    DeltaTree deltaTree = fromJson(result.getResponse().getContentAsString(), DeltaTree.class);

    Assertions.assertEquals("root", deltaTree.getName());
    Assertions.assertEquals(MetricTreeNodeType.MODULE, deltaTree.getType());

    List<MetricValueForCommit> commit1Metrics = deltaTree.getCommit1Metrics();
    List<MetricValueForCommit> commit2Metrics = deltaTree.getCommit2Metrics();

    Assertions.assertEquals(8L, commit1Metrics.get(0).getValue());
    Assertions.assertEquals(12L, commit1Metrics.get(1).getValue());
    Assertions.assertEquals(10L, commit1Metrics.get(2).getValue());

    Assertions.assertEquals(8L, commit2Metrics.get(0).getValue());
    Assertions.assertEquals(18L, commit2Metrics.get(1).getValue());
    Assertions.assertEquals(15L, commit2Metrics.get(2).getValue());

    DeltaTree firstChild = deltaTree.getChildren().get(0); // Finding.java
    Assertions.assertEquals("Finding.java", firstChild.getName());
    Assertions.assertEquals(MetricTreeNodeType.FILE, firstChild.getType());
    Assertions.assertEquals(3, firstChild.getCommit1Metrics().size());
    Assertions.assertNull(firstChild.getCommit2Metrics());
    Assertions.assertEquals(Changes.CHANGES_DELETED, firstChild.getChanges());

    DeltaTree secondChild = deltaTree.getChildren().get(1); // GetMetricsForCommitCommand.java
    Assertions.assertEquals("GetMetricsForCommitCommand.java", secondChild.getName());
    Assertions.assertEquals(MetricTreeNodeType.FILE, secondChild.getType());
    Assertions.assertNull(secondChild.getCommit1Metrics());
    Assertions.assertEquals(3, secondChild.getCommit2Metrics().size());
    Assertions.assertEquals(Changes.CHANGES_ADDED, secondChild.getChanges());

    DeltaTree thirdChild = deltaTree.getChildren().get(2); // testModule1/NewRandomFile.java
    Assertions.assertEquals("testModule1/NewRandomFile.java", thirdChild.getName());
    Assertions.assertEquals(MetricTreeNodeType.FILE, thirdChild.getType());
    Assertions.assertNull(thirdChild.getCommit1Metrics());
    Assertions.assertEquals(3, thirdChild.getCommit2Metrics().size());
    Assertions.assertEquals(Changes.CHANGES_ADDED, thirdChild.getChanges());
  }

  @Test
  void returnsTreeWhenCommit1IsAfterCommit2() throws Exception {
    GetDeltaTreeForTwoCommitsCommand command = new GetDeltaTreeForTwoCommitsCommand();
    command.setMetrics(
        Arrays.asList(
            "coderadar:size:loc:java",
            "coderadar:size:sloc:java",
            "coderadar:size:cloc:java",
            "coderadar:size:eloc:java"));
    command.setCommit1("d3272b3793bc4b2bc36a1a3a7c8293fcf8fe27df");
    command.setCommit2("fd68136dd6489504e829b11f2fce1fe97c9f5c0c");

    MvcResult result =
        mvc()
            .perform(
                get("/api/projects/" + projectId + "/metricvalues/deltaTree")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(command)))
            .andReturn();

    DeltaTree deltaTree = fromJson(result.getResponse().getContentAsString(), DeltaTree.class);

    Assertions.assertEquals("root", deltaTree.getName());
    Assertions.assertEquals(MetricTreeNodeType.MODULE, deltaTree.getType());

    List<MetricValueForCommit> commit1Metrics = deltaTree.getCommit1Metrics();
    List<MetricValueForCommit> commit2Metrics = deltaTree.getCommit2Metrics();

    commit1Metrics.sort(Comparator.comparing(MetricValueForCommit::getMetricName));
    commit2Metrics.sort(Comparator.comparing(MetricValueForCommit::getMetricName));

    Assertions.assertEquals(8L, commit1Metrics.get(0).getValue());
    Assertions.assertEquals(12L, commit1Metrics.get(1).getValue());
    Assertions.assertEquals(10L, commit1Metrics.get(2).getValue());

    Assertions.assertEquals(8L, commit2Metrics.get(0).getValue());
    Assertions.assertEquals(18L, commit2Metrics.get(1).getValue());
    Assertions.assertEquals(15L, commit2Metrics.get(2).getValue());

    DeltaTree firstChild = deltaTree.getChildren().get(0); // Finding.java
    Assertions.assertEquals("Finding.java", firstChild.getName());
    Assertions.assertEquals(MetricTreeNodeType.FILE, firstChild.getType());
    Assertions.assertEquals(3, firstChild.getCommit1Metrics().size());
    Assertions.assertNull(firstChild.getCommit2Metrics());
    Assertions.assertEquals(Changes.CHANGES_DELETED, firstChild.getChanges());

    DeltaTree secondChild = deltaTree.getChildren().get(1); // GetMetricsForCommitCommand.java
    Assertions.assertEquals("GetMetricsForCommitCommand.java", secondChild.getName());
    Assertions.assertEquals(MetricTreeNodeType.FILE, secondChild.getType());
    Assertions.assertNull(secondChild.getCommit1Metrics());
    Assertions.assertEquals(3, secondChild.getCommit2Metrics().size());
    Assertions.assertEquals(Changes.CHANGES_ADDED, secondChild.getChanges());

    DeltaTree thirdChild = deltaTree.getChildren().get(2); // testModule1/NewRandomFile.java
    Assertions.assertEquals("testModule1/NewRandomFile.java", thirdChild.getName());
    Assertions.assertEquals(MetricTreeNodeType.FILE, thirdChild.getType());
    Assertions.assertNull(thirdChild.getCommit1Metrics());
    Assertions.assertEquals(3, thirdChild.getCommit2Metrics().size());
    Assertions.assertEquals(Changes.CHANGES_ADDED, thirdChild.getChanges());
  }

  @Test
  void returnsErrorWhenProjectWithIdDoesNotExist() throws Exception {
    GetDeltaTreeForTwoCommitsCommand command = new GetDeltaTreeForTwoCommitsCommand();
    command.setMetrics(
        Arrays.asList(
            "coderadar:size:loc:java",
            "coderadar:size:sloc:java",
            "coderadar:size:cloc:java",
            "coderadar:size:eloc:java"));
    command.setCommit1("fd68136dd6489504e829b11f2fce1fe97c9f5c0c");
    command.setCommit2("d3272b3793bc4b2bc36a1a3a7c8293fcf8fe27df");

    MvcResult result =
        mvc()
            .perform(
                get("/api/projects/1234/metricvalues/deltaTree")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(command)))
            .andExpect(status().isNotFound())
            .andReturn();

    ErrorMessageResponse response =
        fromJson(result.getResponse().getContentAsString(), ErrorMessageResponse.class);

    Assertions.assertEquals("Project with id 1234 not found.", response.getErrorMessage());
  }
}
