package io.reflectoring.coderadar.rest.analysis;

import com.fasterxml.jackson.core.type.TypeReference;
import io.reflectoring.coderadar.analyzer.port.driver.StartAnalyzingCommand;
import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzerConfigurationEntity;
import io.reflectoring.coderadar.graph.analyzer.domain.MetricValueEntity;
import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.FileRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.MetricRepository;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.AnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.FileEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.FilePatternEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.FilePatternRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.graph.query.repository.GetAvailableMetricsInProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.create.CreateFilePatternCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.update.UpdateProjectCommand;
import io.reflectoring.coderadar.query.domain.MetricValueForCommit;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForCommitCommand;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class PaySimProjectIntegrationTest extends ControllerTestTemplate {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired private FileRepository fileRepository;
    @Autowired private CommitRepository commitRepository;
    @Autowired private FilePatternRepository filePatternRepository;
    @Autowired private AnalyzerConfigurationRepository analyzerConfigurationRepository;
    @Autowired private MetricRepository metricRepository;
    @Autowired private GetAvailableMetricsInProjectRepository getAvailableMetricsInProjectRepository;

    @Autowired private Session session;

    @BeforeAll
    static void setUp() throws IOException {
        FileUtils.deleteDirectory(new File("coderadar-workdir/PaySim"));
        ZipFile zipFile = new ZipFile(PaySimProjectIntegrationTest.class.getClassLoader().getResource("PaySim.zip").getPath());
        zipFile.extractAll("coderadar-workdir");
    }

    @AfterAll
    static void cleanUp() throws IOException {
        FileUtils.deleteDirectory(new File("coderadar-workdir/projects"));
        FileUtils.deleteDirectory(new File("coderadar-workdir/PaySim"));
    }

    /**
     * This test runs a full check on the PaySim project.
     * This includes saving, configuring, updating, analyzing and checking the values.
     */
    @Test
    @DirtiesContext
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void paySimProjectIntegrationTest() throws Exception {
        Long projectId = testSavingProject();
        testSavingFilePatterns(projectId);
        testSavingAnalyzerConfiguration(projectId);
        testAnalysis(projectId);
        testMetricValues(projectId);
        testUpdatingProject(projectId);
        testAnalysisAfterUpdate(projectId);
    }

    private void testAnalysisAfterUpdate(Long projectId) throws Exception {
        //Analyze again
        StartAnalyzingCommand startAnalyzingCommand = new StartAnalyzingCommand(new Date(0L), true);
        mvc().perform(post("/projects/" + projectId + "/analyze").content(toJson(startAnalyzingCommand)).contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        //Check values for latest (newest) commit
        List<String> availableMetrics = getAvailableMetricsInProjectRepository.getAvailableMetricsInProject(projectId);
        GetMetricsForCommitCommand getMetricsForCommitCommand = new GetMetricsForCommitCommand();
        getMetricsForCommitCommand.setMetrics(availableMetrics);
        getMetricsForCommitCommand.setCommit("5d7ba2de71dcce2746a75bc0cf668a129f023c5d");

        MvcResult result = mvc().perform(get("/projects/" + projectId + "/metricvalues/perCommit")
                .contentType(MediaType.APPLICATION_JSON).content(toJson(getMetricsForCommitCommand)))
                .andReturn();

        List<MetricValueForCommit> metricValuesForCommit = fromJson(new TypeReference<List<MetricValueForCommit>>() {},
                result.getResponse().getContentAsString());

        metricValuesForCommit.sort(Comparator.comparing(MetricValueForCommit::getMetricName));

        //Values correct?
        Assertions.assertEquals(4, metricValuesForCommit.size());
        Assertions.assertEquals(23L, metricValuesForCommit.get(0).getValue().longValue());
        Assertions.assertEquals(1022L, metricValuesForCommit.get(1).getValue().longValue());
        Assertions.assertEquals(1717L, metricValuesForCommit.get(2).getValue().longValue());
        Assertions.assertEquals(1362L, metricValuesForCommit.get(3).getValue().longValue());
    }

    private void testUpdatingProject(Long projectId) throws Exception {
        //Update project
        String testRepoURL = Paths.get("coderadar-workdir/Paysim").toAbsolutePath().normalize().toString();
        UpdateProjectCommand updateProjectCommand =
                new UpdateProjectCommand(
                        "PaySim", "username", "password", "file://" + testRepoURL, true, new SimpleDateFormat("dd/MM/yyyy").parse("01/05/2019")  ,null);
        mvc().perform(
                        post("/projects/" + projectId)
                                .content(toJson(updateProjectCommand))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        //Number of commits correct?
        List<CommitEntity> commitEntities = commitRepository.findByProjectIdWithFileRelationshipsSortedByTimestampAsc(projectId);
        Assertions.assertEquals(5, commitEntities.size());

        //Metric values deleted?
        List<MetricValueEntity> metricValues = metricRepository.findByProjectId(projectId);
        Assertions.assertEquals(0, metricValues.size());
    }

    private void testMetricValues(Long projectId) throws Exception {
        //Check values for latest (newest) commit
        List<String> availableMetrics = getAvailableMetricsInProjectRepository.getAvailableMetricsInProject(projectId);
        GetMetricsForCommitCommand getMetricsForCommitCommand = new GetMetricsForCommitCommand();
        getMetricsForCommitCommand.setMetrics(availableMetrics);
        getMetricsForCommitCommand.setCommit("5d7ba2de71dcce2746a75bc0cf668a129f023c5d");

        MvcResult result = mvc().perform(get("/projects/" + projectId + "/metricvalues/perCommit")
                .contentType(MediaType.APPLICATION_JSON).content(toJson(getMetricsForCommitCommand)))
                .andReturn();

        List<MetricValueForCommit> metricValuesForCommit = fromJson(new TypeReference<List<MetricValueForCommit>>() {},
                result.getResponse().getContentAsString());

        metricValuesForCommit.sort(Comparator.comparing(MetricValueForCommit::getMetricName));

        //Values correct?
        Assertions.assertEquals(4, metricValuesForCommit.size());
        Assertions.assertEquals(23L, metricValuesForCommit.get(0).getValue().longValue());
        Assertions.assertEquals(1022L, metricValuesForCommit.get(1).getValue().longValue());
        Assertions.assertEquals(1717L, metricValuesForCommit.get(2).getValue().longValue());
        Assertions.assertEquals(1362L, metricValuesForCommit.get(3).getValue().longValue());

        //Check values for second commit
        GetMetricsForCommitCommand getMetricsForCommitCommand2 = new GetMetricsForCommitCommand();
        getMetricsForCommitCommand2.setMetrics(availableMetrics);
        getMetricsForCommitCommand2.setCommit("f87b286a50fe303bda94203b216d302f8854d42b");

        MvcResult result2 = mvc().perform(get("/projects/" + projectId + "/metricvalues/perCommit")
                .contentType(MediaType.APPLICATION_JSON).content(toJson(getMetricsForCommitCommand2)))
                .andReturn();

        List<MetricValueForCommit> metricValuesForCommit2 = fromJson(new TypeReference<List<MetricValueForCommit>>() {},
                result2.getResponse().getContentAsString());

        metricValuesForCommit2.sort(Comparator.comparing(MetricValueForCommit::getMetricName));

        //Values correct?
        Assertions.assertEquals(4, metricValuesForCommit2.size());
        Assertions.assertEquals(549L, metricValuesForCommit2.get(0).getValue().longValue());
        Assertions.assertEquals(3903L, metricValuesForCommit2.get(1).getValue().longValue());
        Assertions.assertEquals(7170L, metricValuesForCommit2.get(2).getValue().longValue());
        Assertions.assertEquals(4914L, metricValuesForCommit2.get(3).getValue().longValue());
    }

    private void testAnalysis(Long projectId) throws Exception {
        //Start analysis
        StartAnalyzingCommand startAnalyzingCommand = new StartAnalyzingCommand(new Date(0L), true);
        mvc().perform(post("/projects/" + projectId + "/analyze").content(toJson(startAnalyzingCommand)).contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        //Commits analyzed?
        Collection<CommitEntity> commits = commitRepository.findByProjectIdWithAllRelationshipsSortedByTimestampAsc(projectId);

        for (CommitEntity commit : commits) {
            Assertions.assertTrue(commit.isAnalyzed());
        }

        //Metrics available?
        List<String> availableMetrics = getAvailableMetricsInProjectRepository.getAvailableMetricsInProject(projectId);
        Assertions.assertEquals(4, availableMetrics.size());
        Assertions.assertTrue(availableMetrics.contains("coderadar:size:sloc:java"));
        Assertions.assertTrue(availableMetrics.contains("coderadar:size:loc:java"));
        Assertions.assertTrue(availableMetrics.contains("coderadar:size:eloc:java"));
        Assertions.assertTrue(availableMetrics.contains("coderadar:size:cloc:java"));

        //Metric values there?
        List<MetricValueEntity> metricValues = metricRepository.findByProjectId(projectId);
        Assertions.assertEquals(2136, metricValues.size());
    }

    private void testSavingAnalyzerConfiguration(Long projectId) throws Exception {
        //Add analyzer configuration
        CreateAnalyzerConfigurationCommand analyzerConfigurationCommand =
                new CreateAnalyzerConfigurationCommand("io.reflectoring.coderadar.analyzer.loc.LocAnalyzerPlugin", true);
        mvc().perform(post("/projects/" + projectId + "/analyzers")
                .content(toJson(analyzerConfigurationCommand))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        //Configuration saved?
        AnalyzerConfigurationEntity analyzerConfiguration =
                analyzerConfigurationRepository.findByProjectId(projectId).get(0);

        //Configuration correct?
        Assertions.assertEquals("io.reflectoring.coderadar.analyzer.loc.LocAnalyzerPlugin", analyzerConfiguration.getAnalyzerName());
        Assertions.assertTrue(analyzerConfiguration.getEnabled());
    }

    private void testSavingFilePatterns(Long projectId) throws Exception {
        //Add file patterns
        CreateFilePatternCommand filePatternCommand =
                new CreateFilePatternCommand("**/*.java", InclusionType.INCLUDE);
        mvc()
                .perform(
                        post("/projects/" + projectId + "/filePatterns")
                                .content(toJson(filePatternCommand))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        //Patterns saved?
        FilePatternEntity filePattern = filePatternRepository.findByProjectId(projectId).get(0);

        //Patterns correct?
        Assertions.assertEquals("**/*.java", filePattern.getPattern());
        Assertions.assertEquals(InclusionType.INCLUDE, filePattern.getInclusionType());
    }

    private Long testSavingProject() throws Exception {
        String testRepoURL = Paths.get("coderadar-workdir/Paysim").toAbsolutePath().normalize().toString();
        CreateProjectCommand command =
                new CreateProjectCommand(
                        "PaySim", "username", "password", "file://" + testRepoURL, false, null, null);

        mvc()
                .perform(post("/projects").contentType(MediaType.APPLICATION_JSON).content(toJson(command)))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        //Is the project saved?
        List<ProjectEntity> projectEntities = projectRepository.findAll();
        Assertions.assertEquals(1, projectEntities.size());
        Long projectId = projectEntities.get(0).getId();
        Assertions.assertEquals("PaySim", projectEntities.get(0).getName());

        //Number of commits correct?
        List<CommitEntity> commitEntities = commitRepository.findByProjectIdWithFileRelationshipsSortedByTimestampAsc(projectId);
        Assertions.assertEquals(99, commitEntities.size());

        //Number of files correct?
        List<FileEntity> fileEntities = fileRepository.findAllinProject(projectId);
        Assertions.assertEquals(239, fileEntities.size());

        return projectId;
    }
}
