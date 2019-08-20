package io.reflectoring.coderadar.rest.integration.query;

import io.reflectoring.coderadar.analyzer.port.driver.StartAnalyzingCommand;
import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.create.CreateFilePatternCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.query.domain.MetricTree;
import io.reflectoring.coderadar.query.domain.MetricValueForCommit;
import io.reflectoring.coderadar.query.domain.MetricsTreeNodeType;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForCommitCommand;
import io.reflectoring.coderadar.rest.IdResponse;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

import static io.reflectoring.coderadar.rest.integration.JsonHelper.fromJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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

        StartAnalyzingCommand command4 = new StartAnalyzingCommand(new Date(), true);
        mvc().perform(post("/projects/" + projectId + "/analyze").content(toJson(command4)).contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void returnsMetricTree() throws Exception {
        GetMetricsForCommitCommand command = new GetMetricsForCommitCommand();
        command.setMetrics(Arrays.asList("coderadar:size:loc:java", "coderadar:size:sloc:java", "coderadar:size:cloc:java", "coderadar:size:eloc:java"));
        command.setCommit("d3272b3793bc4b2bc36a1a3a7c8293fcf8fe27df");

        MvcResult result = mvc().perform(get("/projects/" + projectId + "/metricvalues/tree")
                .contentType(MediaType.APPLICATION_JSON).content(toJson(command)))
                .andReturn();

        MetricTree metricTree = fromJson(result.getResponse().getContentAsString(), MetricTree.class);

        metricTree.getMetrics().sort(Comparator.comparing(MetricValueForCommit::getMetricName));

        Assertions.assertEquals("root", metricTree.getName());
        Assertions.assertEquals(MetricsTreeNodeType.MODULE, metricTree.getType());
        Assertions.assertEquals(4, metricTree.getMetrics().size());
        Assertions.assertEquals(0L, metricTree.getMetrics().get(0).getValue().longValue());
        Assertions.assertEquals(8L, metricTree.getMetrics().get(1).getValue().longValue());
        Assertions.assertEquals(18L, metricTree.getMetrics().get(2).getValue().longValue());
        Assertions.assertEquals(15L, metricTree.getMetrics().get(3).getValue().longValue());

        MetricTree firstChild = metricTree.getChildren().get(0);

        firstChild.getMetrics().sort(Comparator.comparing(MetricValueForCommit::getMetricName));

        Assertions.assertEquals("GetMetricsForCommitCommand.java", firstChild.getName());
        Assertions.assertEquals(MetricsTreeNodeType.FILE, firstChild.getType());
        Assertions.assertTrue(firstChild.getChildren().isEmpty());
        Assertions.assertEquals(0L, firstChild.getMetrics().get(0).getValue().longValue());
        Assertions.assertEquals(7L, firstChild.getMetrics().get(1).getValue().longValue());
        Assertions.assertEquals(17L, firstChild.getMetrics().get(2).getValue().longValue());
        Assertions.assertEquals(14L, firstChild.getMetrics().get(3).getValue().longValue());

        MetricTree secondChild = metricTree.getChildren().get(1);

        secondChild.getMetrics().sort(Comparator.comparing(MetricValueForCommit::getMetricName));

        Assertions.assertEquals("testModule1/NewRandomFile.java", secondChild.getName());
        Assertions.assertEquals(MetricsTreeNodeType.FILE, secondChild.getType());
        Assertions.assertTrue(secondChild.getChildren().isEmpty());
        Assertions.assertEquals(0L, secondChild.getMetrics().get(0).getValue().longValue());
        Assertions.assertEquals(1L, secondChild.getMetrics().get(1).getValue().longValue());
        Assertions.assertEquals(1L, secondChild.getMetrics().get(2).getValue().longValue());
        Assertions.assertEquals(1L, secondChild.getMetrics().get(3).getValue().longValue());
    }

    @Test
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

        metricTree.getMetrics().sort(Comparator.comparing(MetricValueForCommit::getMetricName));

        Assertions.assertEquals("root", metricTree.getName());
        Assertions.assertEquals(MetricsTreeNodeType.MODULE, metricTree.getType());
        Assertions.assertEquals(4, metricTree.getMetrics().size());
        Assertions.assertEquals(0L, metricTree.getMetrics().get(0).getValue().longValue());
        Assertions.assertEquals(8L, metricTree.getMetrics().get(1).getValue().longValue());
        Assertions.assertEquals(18L, metricTree.getMetrics().get(2).getValue().longValue());
        Assertions.assertEquals(15L, metricTree.getMetrics().get(3).getValue().longValue());

        MetricTree firstChild = metricTree.getChildren().get(0);

        firstChild.getMetrics().sort(Comparator.comparing(MetricValueForCommit::getMetricName));

        Assertions.assertEquals("GetMetricsForCommitCommand.java", firstChild.getName());
        Assertions.assertEquals(MetricsTreeNodeType.FILE, firstChild.getType());
        Assertions.assertTrue(firstChild.getChildren().isEmpty());
        Assertions.assertEquals(0L, firstChild.getMetrics().get(0).getValue().longValue());
        Assertions.assertEquals(7L, firstChild.getMetrics().get(1).getValue().longValue());
        Assertions.assertEquals(17L, firstChild.getMetrics().get(2).getValue().longValue());
        Assertions.assertEquals(14L, firstChild.getMetrics().get(3).getValue().longValue());

        MetricTree secondChild = metricTree.getChildren().get(1);

        secondChild.getMetrics().sort(Comparator.comparing(MetricValueForCommit::getMetricName));

        Assertions.assertEquals("testModule1/", secondChild.getName());
        Assertions.assertEquals(MetricsTreeNodeType.MODULE, secondChild.getType());
        Assertions.assertFalse(secondChild.getChildren().isEmpty());
        Assertions.assertEquals(0L, secondChild.getMetrics().get(0).getValue().longValue());
        Assertions.assertEquals(1L, secondChild.getMetrics().get(1).getValue().longValue());
        Assertions.assertEquals(1L, secondChild.getMetrics().get(2).getValue().longValue());
        Assertions.assertEquals(1L, secondChild.getMetrics().get(3).getValue().longValue());

        MetricTree thirdChild = secondChild.getChildren().get(0);

        thirdChild.getMetrics().sort(Comparator.comparing(MetricValueForCommit::getMetricName));

        Assertions.assertEquals("testModule1/NewRandomFile.java", thirdChild.getName());
        Assertions.assertEquals(MetricsTreeNodeType.FILE, thirdChild.getType());
        Assertions.assertTrue(thirdChild.getChildren().isEmpty());
        Assertions.assertEquals(0L, thirdChild.getMetrics().get(0).getValue().longValue());
        Assertions.assertEquals(1L, thirdChild.getMetrics().get(1).getValue().longValue());
        Assertions.assertEquals(1L, thirdChild.getMetrics().get(2).getValue().longValue());
        Assertions.assertEquals(1L, thirdChild.getMetrics().get(3).getValue().longValue());
    }
}
