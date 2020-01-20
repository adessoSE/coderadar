package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.create.CreateFilePatternCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.query.domain.MetricTree;
import io.reflectoring.coderadar.query.domain.MetricTreeNodeType;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForCommitCommand;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.ErrorMessageResponse;
import io.reflectoring.coderadar.rest.domain.IdResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;

import java.net.URL;
import java.util.Arrays;
import java.util.Objects;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetMetricsForAllFilesInCommitControllerTest extends ControllerTestTemplate {

    private Long projectId;

    @BeforeEach
    void setUp() throws Exception {
        URL testRepoURL =  this.getClass().getClassLoader().getResource("test-repository");
        CreateProjectCommand command1 =
                new CreateProjectCommand(
                        "test-project", "username", "password", Objects.requireNonNull(testRepoURL).toString(), false, null, null);
        MvcResult result = mvc().perform(post("/projects").contentType(MediaType.APPLICATION_JSON).content(toJson(command1))).andReturn();

        projectId = fromJson(result.getResponse().getContentAsString(), IdResponse.class).getId();

        CreateFilePatternCommand command2 =
                new CreateFilePatternCommand("**/*.java", InclusionType.INCLUDE);
        mvc()
                .perform(
                        post("/projects/" + projectId + "/filePatterns")
                                .content(toJson(command2))
                                .contentType(MediaType.APPLICATION_JSON));

        CreateAnalyzerConfigurationCommand command3 = new CreateAnalyzerConfigurationCommand("io.reflectoring.coderadar.analyzer.loc.LocAnalyzerPlugin", true);
        mvc().perform(post("/projects/" + projectId + "/analyzers").content(toJson(command3)).contentType(MediaType.APPLICATION_JSON));

        mvc().perform(post("/projects/" + projectId + "/analyze").contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void returnsMetricTree() throws Exception {
        GetMetricsForCommitCommand command = new GetMetricsForCommitCommand();
        ConstrainedFields fields = fields(MetricTree.class);
        command.setMetrics(Arrays.asList("coderadar:size:loc:java", "coderadar:size:sloc:java", "coderadar:size:cloc:java", "coderadar:size:eloc:java"));
        command.setCommit("d3272b3793bc4b2bc36a1a3a7c8293fcf8fe27df");

        MvcResult result = mvc().perform(get("/projects/" + projectId + "/metricvalues/tree")
                .contentType(MediaType.APPLICATION_JSON).content(toJson(command)))
                .andDo(document(
                        "metrics/tree",
                        requestFields(
                                fields.withPath("commit").description("Commit to get the metrics for."),
                                fields.withPath("metrics").description("List of Metrics to query.")
                        ),
                        responseFields(
                                fieldWithPath("name")
                                        .description("The name of the file or module, containing the full path."),
                                fieldWithPath("type")
                                        .description("Either 'MODULE' if this node describes a module which can have child nodes or 'FILE' if this node describes a file (which has no child nodes)."),
                                subsectionWithPath("metrics")
                                        .description("Contains a map of metric values for each of the metrics specified in the query at the time of the commit specified in the request. If this node is a MODULE, the metrics are aggregated over all files within this module."),
                                subsectionWithPath("children")
                                        .description("If this node describes a MODULE, this field contains the list of child nodes of the same structure, which can be of type MODULE or FILE.")
                        )))
                .andReturn();

        MetricTree metricTree = fromJson(result.getResponse().getContentAsString(), MetricTree.class);

        Assertions.assertEquals("root", metricTree.getName());
        Assertions.assertEquals(MetricTreeNodeType.MODULE, metricTree.getType());
        Assertions.assertEquals(4, metricTree.getMetrics().size());
        Assertions.assertEquals(0L, metricTree.getMetrics().get(0).getValue());
        Assertions.assertEquals(8L, metricTree.getMetrics().get(1).getValue());
        Assertions.assertEquals(18L, metricTree.getMetrics().get(2).getValue());
        Assertions.assertEquals(15L, metricTree.getMetrics().get(3).getValue());

        MetricTree firstChild = metricTree.getChildren().get(0);

        Assertions.assertEquals("GetMetricsForCommitCommand.java", firstChild.getName());
        Assertions.assertEquals(MetricTreeNodeType.FILE, firstChild.getType());
        Assertions.assertTrue(firstChild.getChildren().isEmpty());
        Assertions.assertEquals(0L, firstChild.getMetrics().get(0).getValue());
        Assertions.assertEquals(7L, firstChild.getMetrics().get(1).getValue());
        Assertions.assertEquals(17L, firstChild.getMetrics().get(2).getValue());
        Assertions.assertEquals(14L, firstChild.getMetrics().get(3).getValue());

        MetricTree secondChild = metricTree.getChildren().get(1);

        Assertions.assertEquals("testModule1/NewRandomFile.java", secondChild.getName());
        Assertions.assertEquals(MetricTreeNodeType.FILE, secondChild.getType());
        Assertions.assertTrue(secondChild.getChildren().isEmpty());
        Assertions.assertEquals(0L, secondChild.getMetrics().get(0).getValue());
        Assertions.assertEquals(1L, secondChild.getMetrics().get(1).getValue());
        Assertions.assertEquals(1L, secondChild.getMetrics().get(2).getValue());
        Assertions.assertEquals(1L, secondChild.getMetrics().get(3).getValue());
    }

    @Test
    @DirtiesContext
    void returnsMetricTreeWithModule() throws Exception {
        CreateModuleCommand createModuleCommand = new CreateModuleCommand();
        createModuleCommand.setPath("testModule1");

        mvc().perform(post("/projects/" + projectId + "/modules")
                .contentType(MediaType.APPLICATION_JSON).content(toJson(createModuleCommand)));

        GetMetricsForCommitCommand command = new GetMetricsForCommitCommand();
        command.setMetrics(Arrays.asList("coderadar:size:loc:java", "coderadar:size:sloc:java", "coderadar:size:cloc:java", "coderadar:size:eloc:java"));
        command.setCommit("d3272b3793bc4b2bc36a1a3a7c8293fcf8fe27df");

        MvcResult result = mvc().perform(get("/projects/" + projectId + "/metricvalues/tree")
                .contentType(MediaType.APPLICATION_JSON).content(toJson(command)))
                .andReturn();

        MetricTree metricTree = fromJson(result.getResponse().getContentAsString(), MetricTree.class);

        Assertions.assertEquals("root", metricTree.getName());
        Assertions.assertEquals(MetricTreeNodeType.MODULE, metricTree.getType());
        Assertions.assertEquals(4, metricTree.getMetrics().size());
        Assertions.assertEquals(0L, metricTree.getMetrics().get(0).getValue());
        Assertions.assertEquals(8L, metricTree.getMetrics().get(1).getValue());
        Assertions.assertEquals(18L, metricTree.getMetrics().get(2).getValue());
        Assertions.assertEquals(15L, metricTree.getMetrics().get(3).getValue());

        MetricTree firstChild = metricTree.getChildren().get(0);

        Assertions.assertEquals("GetMetricsForCommitCommand.java", firstChild.getName());
        Assertions.assertEquals(MetricTreeNodeType.FILE, firstChild.getType());
        Assertions.assertTrue(firstChild.getChildren().isEmpty());
        Assertions.assertEquals(0L, firstChild.getMetrics().get(0).getValue());
        Assertions.assertEquals(7L, firstChild.getMetrics().get(1).getValue());
        Assertions.assertEquals(17L, firstChild.getMetrics().get(2).getValue());
        Assertions.assertEquals(14L, firstChild.getMetrics().get(3).getValue());

        MetricTree secondChild = metricTree.getChildren().get(1);

        Assertions.assertEquals("testModule1/", secondChild.getName());
        Assertions.assertEquals(MetricTreeNodeType.MODULE, secondChild.getType());
        Assertions.assertFalse(secondChild.getChildren().isEmpty());
        Assertions.assertEquals(0L, secondChild.getMetrics().get(0).getValue());
        Assertions.assertEquals(1L, secondChild.getMetrics().get(1).getValue());
        Assertions.assertEquals(1L, secondChild.getMetrics().get(2).getValue());
        Assertions.assertEquals(1L, secondChild.getMetrics().get(3).getValue());

        MetricTree thirdChild = secondChild.getChildren().get(0);

        Assertions.assertEquals("testModule1/NewRandomFile.java", thirdChild.getName());
        Assertions.assertEquals(MetricTreeNodeType.FILE, thirdChild.getType());
        Assertions.assertTrue(thirdChild.getChildren().isEmpty());
        Assertions.assertEquals(0L, thirdChild.getMetrics().get(0).getValue());
        Assertions.assertEquals(1L, thirdChild.getMetrics().get(1).getValue());
        Assertions.assertEquals(1L, thirdChild.getMetrics().get(2).getValue());
        Assertions.assertEquals(1L, thirdChild.getMetrics().get(3).getValue());
    }

    @Test
    void returnsErrorWhenProjectWithIdDoesNotExist() throws Exception {
        GetMetricsForCommitCommand command = new GetMetricsForCommitCommand();
        command.setMetrics(Arrays.asList("coderadar:size:loc:java", "coderadar:size:sloc:java", "coderadar:size:cloc:java", "coderadar:size:eloc:java"));
        command.setCommit("d3272b3793bc4b2bc36a1a3a7c8293fcf8fe27df");

        MvcResult result = mvc().perform(get("/projects/1234/metricvalues/tree")
                .contentType(MediaType.APPLICATION_JSON).content(toJson(command)))
                .andExpect(status().isNotFound())
                .andReturn();

        ErrorMessageResponse response = fromJson(result.getResponse().getContentAsString(), ErrorMessageResponse.class);

        Assertions.assertEquals("Project with id 1234 not found.", response.getErrorMessage());
    }
}
