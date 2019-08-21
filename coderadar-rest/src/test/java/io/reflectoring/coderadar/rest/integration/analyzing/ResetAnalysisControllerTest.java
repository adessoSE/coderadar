package io.reflectoring.coderadar.rest.integration.analyzing;

import io.reflectoring.coderadar.analyzer.port.driver.StartAnalyzingCommand;
import io.reflectoring.coderadar.graph.analyzer.domain.CommitEntity;
import io.reflectoring.coderadar.graph.analyzer.domain.FindingEntity;
import io.reflectoring.coderadar.graph.analyzer.domain.MetricValueEntity;
import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.FindingRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.MetricRepository;
import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.create.CreateFilePatternCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.rest.IdResponse;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static io.reflectoring.coderadar.rest.integration.JsonHelper.fromJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class ResetAnalysisControllerTest extends ControllerTestTemplate {

    @Autowired private CommitRepository commitRepository;
    @Autowired private MetricRepository metricRepository;
    @Autowired private FindingRepository findingRepository;

    private Long projectId;

    @BeforeEach
    void setUp() throws Exception {
        URL testRepoURL =  this.getClass().getClassLoader().getResource("test-repository");
        CreateProjectCommand createProjectCommand =
                new CreateProjectCommand(
                        "test-project", "username", "password", Objects.requireNonNull(testRepoURL).toString(), false, null, null);
        MvcResult result = mvc().perform(post("/projects").contentType(MediaType.APPLICATION_JSON).content(toJson(createProjectCommand))).andReturn();

        projectId = fromJson(result.getResponse().getContentAsString(), IdResponse.class).getId();

        CreateFilePatternCommand createFilePatternCommand =
                new CreateFilePatternCommand("**/*.java", InclusionType.INCLUDE);
        mvc()
                .perform(
                        post("/projects/" + projectId + "/filePatterns")
                                .content(toJson(createFilePatternCommand))
                                .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void resetAnalyzedFlagAndDeleteMetricValues() throws Exception {
        CreateAnalyzerConfigurationCommand createAnalyzerConfigurationCommand = new CreateAnalyzerConfigurationCommand("io.reflectoring.coderadar.analyzer.loc.LocAnalyzerPlugin", true);
        mvc().perform(post("/projects/" + projectId + "/analyzers").content(toJson(createAnalyzerConfigurationCommand)).contentType(MediaType.APPLICATION_JSON));

        StartAnalyzingCommand startAnalyzingCommand = new StartAnalyzingCommand(new Date(0L), true);
        mvc().perform(post("/projects/" + projectId + "/analyze").content(toJson(startAnalyzingCommand)).contentType(MediaType.APPLICATION_JSON));

        /*List<CommitEntity> commits = commitRepository.findByProjectId(projectId);
        for (CommitEntity commit : commits) {
            Assertions.assertTrue(commit.isAnalyzed());
        }*/
        List<MetricValueEntity> metricValues = metricRepository.findByProjectId(projectId);
        Assertions.assertEquals(40, metricValues.size());

        mvc().perform(post("/projects/" + projectId + "/analyze/reset"));

        /*List<CommitEntity> commits = commitRepository.findByProjectId(projectId);
        for (CommitEntity commit : commits) {
            Assertions.assertFalse(commit.isAnalyzed());
        }*/

        metricValues = metricRepository.findByProjectId(projectId);
        Assertions.assertEquals(0, metricValues.size());
    }

    @Test
    void resetAnalyzedFlagAndDeleteMetricValuesAndFindings() throws Exception {
        CreateAnalyzerConfigurationCommand createAnalyzerConfigurationCommand = new CreateAnalyzerConfigurationCommand("io.reflectoring.coderadar.analyzer.checkstyle.CheckstyleSourceCodeFileAnalyzerPlugin", true);
        mvc().perform(post("/projects/" + projectId + "/analyzers").content(toJson(createAnalyzerConfigurationCommand)).contentType(MediaType.APPLICATION_JSON));

        StartAnalyzingCommand startAnalyzingCommand = new StartAnalyzingCommand(new Date(0L), true);
        mvc().perform(post("/projects/" + projectId + "/analyze").content(toJson(startAnalyzingCommand)).contentType(MediaType.APPLICATION_JSON));

        List<MetricValueEntity> metricValues = metricRepository.findByProjectId(projectId);
        Assertions.assertEquals(91, metricValues.size());

        List<FindingEntity> findings = findingRepository.findByProjectId(projectId);
        Assertions.assertEquals(172, findings.size());

        /*List<CommitEntity> commits = commitRepository.findByProjectId(projectId);
        for (CommitEntity commit : commits) {
            Assertions.assertTrue(commit.isAnalyzed());
        }*/

        mvc().perform(post("/projects/" + projectId + "/analyze/reset"));

        metricValues = metricRepository.findByProjectId(projectId);
        Assertions.assertEquals(0, metricValues.size());

        findings = findingRepository.findByProjectId(projectId);
        Assertions.assertEquals(0, findings.size());

        /*List<CommitEntity> commits = commitRepository.findByProjectId(projectId);
        for (CommitEntity commit : commits) {
            Assertions.assertFalse(commit.isAnalyzed());
        }*/
    }
}
