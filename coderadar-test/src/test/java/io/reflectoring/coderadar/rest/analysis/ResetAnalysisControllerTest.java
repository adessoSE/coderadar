package io.reflectoring.coderadar.rest.analysis;

import io.reflectoring.coderadar.graph.analyzer.domain.FindingEntity;
import io.reflectoring.coderadar.graph.analyzer.domain.MetricValueEntity;
import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.FindingRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.MetricRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.create.CreateFilePatternCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.ErrorMessageResponse;
import io.reflectoring.coderadar.rest.domain.IdResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.net.URL;
import java.util.List;
import java.util.Objects;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ResetAnalysisControllerTest extends ControllerTestTemplate {

    @Autowired private CommitRepository commitRepository;
    @Autowired private MetricRepository metricRepository;
    @Autowired private FindingRepository findingRepository;
    @Autowired private Session session;

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

        ConstrainedFields fields = fields(StartAnalyzingCommand.class);
        StartAnalyzingCommand startAnalyzingCommand = new StartAnalyzingCommand(null, true);
        mvc().perform(post("/projects/" + projectId + "/analyze").content(toJson(startAnalyzingCommand)).contentType(MediaType.APPLICATION_JSON))
                .andDo(document("analysis/start",
                        requestFields(
                                fields
                                        .withPath("from")
                                        .type("Date")
                                        .description("Date (in milliseconds since epoch) from which to start analyzing commits. Commits before this date are ignored during analysis. If no date is specified, all commits will be analyzed."),
                                fields
                                        .withPath("rescan")
                                        .description("Set this to true when setting the AnalyzingJob for a project if you want to delete all existing analysis results and restart analysis with the new AnalyzingJob. You will get an error response if you try rescanning a project while there are running or queued analysis jobs (i.e. when the previous scan over all commits is not yet finished).")
                                )));

        mvc().perform(get("/projects/" + projectId + "/analyzingStatus"))
                .andDo(document("analysis/status",
                        responseFields(fieldWithPath("status").description("Whether the Analyzing Job is started or not."))
                ));

        session.clear();

        List<CommitEntity> commits = commitRepository.findByProjectId(projectId);
        for (CommitEntity commit : commits) {
            Assertions.assertTrue(commit.isAnalyzed());
        }
        List<MetricValueEntity> metricValues = metricRepository.findByProjectId(projectId);
        Assertions.assertEquals(40, metricValues.size());

        mvc().perform(post("/projects/" + projectId + "/analyze/reset"))
                .andDo(document("analysis/reset"));

        session.clear();

        commits = commitRepository.findByProjectId(projectId);
        for (CommitEntity commit : commits) {
            Assertions.assertFalse(commit.isAnalyzed());
        }

        metricValues = metricRepository.findByProjectId(projectId);
        Assertions.assertEquals(0, metricValues.size());
    }

    @Test
    void resetAnalyzedFlagAndDeleteMetricValuesAndFindings() throws Exception {
        CreateAnalyzerConfigurationCommand createAnalyzerConfigurationCommand = new CreateAnalyzerConfigurationCommand("io.reflectoring.coderadar.analyzer.checkstyle.CheckstyleSourceCodeFileAnalyzerPlugin", true);
        mvc().perform(post("/projects/" + projectId + "/analyzers").content(toJson(createAnalyzerConfigurationCommand)).contentType(MediaType.APPLICATION_JSON));

        mvc().perform(post("/projects/" + projectId + "/analyze").contentType(MediaType.APPLICATION_JSON));

        session.clear();

        List<MetricValueEntity> metricValues = metricRepository.findByProjectId(projectId);
        Assertions.assertFalse(metricValues.isEmpty());

        List<FindingEntity> findings = findingRepository.findByProjectId(projectId);
        Assertions.assertFalse(findings.isEmpty());

        List<CommitEntity> commits = commitRepository.findByProjectId(projectId);
        for (CommitEntity commit : commits) {
            Assertions.assertTrue(commit.isAnalyzed());
        }

        mvc().perform(post("/projects/" + projectId + "/analyze/reset"));

        session.clear();

        metricValues = metricRepository.findByProjectId(projectId);
        Assertions.assertEquals(0, metricValues.size());

        findings = findingRepository.findByProjectId(projectId);
        Assertions.assertEquals(0, findings.size());

        commits = commitRepository.findByProjectId(projectId);
        for (CommitEntity commit : commits) {
            Assertions.assertFalse(commit.isAnalyzed());
        }
    }

    @Test
    void returnsErrorWhenProjectWithIdDoesNotExist() throws Exception {
        MvcResult result = mvc().perform(post("/projects/123/analyze/reset"))
                .andExpect(status().isNotFound())
                .andReturn();

        ErrorMessageResponse response = fromJson(result.getResponse().getContentAsString(), ErrorMessageResponse.class);

        Assertions.assertEquals("Project with id 123 not found.", response.getErrorMessage());
    }
}
