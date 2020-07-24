package io.reflectoring.coderadar.rest.analysis;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzerConfigurationEntity;
import io.reflectoring.coderadar.graph.analyzer.domain.MetricValueEntity;
import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.FileRepository;
import io.reflectoring.coderadar.graph.analyzer.repository.MetricRepository;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.AnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.*;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.FilePatternRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.ModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.graph.query.repository.MetricQueryRepository;
import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.create.CreateFilePatternCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.update.UpdateProjectCommand;
import io.reflectoring.coderadar.query.domain.DeltaTree;
import io.reflectoring.coderadar.query.domain.MetricTreeNodeType;
import io.reflectoring.coderadar.query.domain.MetricValueForCommit;
import io.reflectoring.coderadar.query.port.driver.commitmetrics.GetMetricValuesOfCommitCommand;
import io.reflectoring.coderadar.query.port.driver.deltatree.GetDeltaTreeForTwoCommitsCommand;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import net.lingala.zip4j.ZipFile;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.*;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

class PaySimProjectIntegrationTest extends ControllerTestTemplate {

  @Autowired private ProjectRepository projectRepository;
  @Autowired private FileRepository fileRepository;
  @Autowired private CommitRepository commitRepository;
  @Autowired private FilePatternRepository filePatternRepository;
  @Autowired private AnalyzerConfigurationRepository analyzerConfigurationRepository;
  @Autowired private MetricRepository metricRepository;
  @Autowired private MetricQueryRepository metricQueryRepository;
  @Autowired private ModuleRepository moduleRepository;
  @Autowired private Session session;

  @BeforeAll
  static void setUp() throws IOException {
    FileUtils.deleteDirectory(new File("coderadar-workdir/PaySim"));
    ZipFile zipFile =
        new ZipFile(
            PaySimProjectIntegrationTest.class
                .getClassLoader()
                .getResource("PaySim.zip")
                .getPath());
    zipFile.extractAll("coderadar-workdir");
  }

  @AfterAll
  static void cleanUp() throws IOException {
    FileUtils.deleteDirectory(new File("coderadar-workdir/PaySim"));
  }

  /**
   * This test runs a full check on the PaySim project. This includes saving, configuring, updating,
   * analyzing and checking the values.
   */
  @Test
  @DirtiesContext
  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  void paySimProjectIntegrationTest() throws Exception {
    Long projectId = testSavingProject();
    testCreatingModule(projectId);
    testSavingFilePatterns(projectId);
    testSavingAnalyzerConfiguration(projectId);
    testAnalysis(projectId);
    testDeltaTree(projectId);
    testMetricValues(projectId);
    testUpdatingProject(projectId);
    testAnalysisAfterUpdate(projectId);
    testDeltaTree(projectId);
  }

  private void testCreatingModule(Long projectId) throws Exception {
    CreateModuleCommand command = new CreateModuleCommand("src/paysim");
    mvc()
        .perform(
            post("/api/projects/" + projectId + "/modules")
                .content(toJson(command))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andReturn();

    List<ModuleEntity> modules = moduleRepository.findModulesInProjectSortedDesc(projectId);
    Assertions.assertEquals("src/paysim/", modules.get(0).getPath());

    // Number of commits correct?
    List<CommitEntity> commitEntities = commitRepository.findByProjectId(projectId);
    Assertions.assertEquals(99, commitEntities.size());

    // Files there?
    List<FileEntity> fileEntities = fileRepository.findAllinProject(projectId);
    Assertions.assertFalse(fileEntities.isEmpty());
    session.clear();
  }

  private void testAnalysisAfterUpdate(Long projectId) throws Exception {
    // Analyze again
    mvc()
        .perform(
            post("/api/projects/" + projectId + "/analyze").contentType(MediaType.APPLICATION_JSON))
        .andReturn();

    // Check values for latest (newest) commit
    List<String> availableMetrics = metricQueryRepository.getAvailableMetricsInProject(projectId);
    GetMetricValuesOfCommitCommand getMetricValuesOfCommitCommand =
        new GetMetricValuesOfCommitCommand();
    getMetricValuesOfCommitCommand.setMetrics(availableMetrics);
    getMetricValuesOfCommitCommand.setCommit("5d7ba2de71dcce2746a75bc0cf668a129f023c5d");

    MvcResult result =
        mvc()
            .perform(
                get("/api/projects/" + projectId + "/metricvalues/perCommit")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(getMetricValuesOfCommitCommand)))
            .andReturn();

    List<MetricValueForCommit> metricValuesForCommit =
        fromJson(
            new TypeReference<List<MetricValueForCommit>>() {},
            result.getResponse().getContentAsString());

    metricValuesForCommit.sort(Comparator.comparing(MetricValueForCommit::getMetricName));

    // Values correct?
    Assertions.assertEquals(4, metricValuesForCommit.size());
    Assertions.assertEquals(23L, metricValuesForCommit.get(0).getValue());
    Assertions.assertEquals(1022L, metricValuesForCommit.get(1).getValue());
    Assertions.assertEquals(1717L, metricValuesForCommit.get(2).getValue());
    Assertions.assertEquals(1362L, metricValuesForCommit.get(3).getValue());
    session.clear();
  }

  private void testUpdatingProject(Long projectId) throws Exception {
    // Update project
    URL testRepoURL = new File("coderadar-workdir/PaySim").toURI().toURL();
    UpdateProjectCommand updateProjectCommand =
        new UpdateProjectCommand(
            "PaySim",
            "username",
            "password",
            testRepoURL.toString(),
            true,
            new SimpleDateFormat("dd/MM/yyyy").parse("01/05/2019"));
    mvc()
        .perform(
            post("/api/projects/" + projectId)
                .content(toJson(updateProjectCommand))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

    // Number of commits correct?
    List<CommitEntity> commitEntities = commitRepository.findByProjectId(projectId);
    Assertions.assertEquals(5, commitEntities.size());

    // Metric values deleted?
    List<MetricValueEntity> metricValues = metricRepository.findByProjectId(projectId);
    Assertions.assertEquals(0, metricValues.size());

    // Module still there?
    List<ModuleEntity> modules = moduleRepository.findModulesInProjectSortedDesc(projectId);
    Assertions.assertEquals("src/paysim/", modules.get(0).getPath());

    session.clear();
  }

  private void testMetricValues(Long projectId) throws Exception {
    // Check values for latest (newest) commit
    List<String> availableMetrics = metricQueryRepository.getAvailableMetricsInProject(projectId);
    GetMetricValuesOfCommitCommand getMetricValuesOfCommitCommand =
        new GetMetricValuesOfCommitCommand();
    getMetricValuesOfCommitCommand.setMetrics(availableMetrics);
    getMetricValuesOfCommitCommand.setCommit("5d7ba2de71dcce2746a75bc0cf668a129f023c5d");

    MvcResult result =
        mvc()
            .perform(
                get("/api/projects/" + projectId + "/metricvalues/perCommit")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(getMetricValuesOfCommitCommand)))
            .andReturn();

    List<MetricValueForCommit> metricValuesForCommit =
        fromJson(
            new TypeReference<List<MetricValueForCommit>>() {},
            result.getResponse().getContentAsString());

    metricValuesForCommit.sort(Comparator.comparing(MetricValueForCommit::getMetricName));

    // Values correct?
    Assertions.assertEquals(4, metricValuesForCommit.size());
    Assertions.assertEquals(23L, metricValuesForCommit.get(0).getValue());
    Assertions.assertEquals(1022L, metricValuesForCommit.get(1).getValue());
    Assertions.assertEquals(1717L, metricValuesForCommit.get(2).getValue());
    Assertions.assertEquals(1362L, metricValuesForCommit.get(3).getValue());

    // Check values for second commit
    GetMetricValuesOfCommitCommand getMetricValuesOfCommitCommand2 =
        new GetMetricValuesOfCommitCommand();
    getMetricValuesOfCommitCommand2.setMetrics(availableMetrics);
    getMetricValuesOfCommitCommand2.setCommit("f87b286a50fe303bda94203b216d302f8854d42b");

    MvcResult result2 =
        mvc()
            .perform(
                get("/api/projects/" + projectId + "/metricvalues/perCommit")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(getMetricValuesOfCommitCommand2)))
            .andReturn();

    List<MetricValueForCommit> metricValuesForCommit2 =
        fromJson(
            new TypeReference<List<MetricValueForCommit>>() {},
            result2.getResponse().getContentAsString());

    metricValuesForCommit2.sort(Comparator.comparing(MetricValueForCommit::getMetricName));

    // Values correct?
    Assertions.assertEquals(4, metricValuesForCommit2.size());
    Assertions.assertEquals(549L, metricValuesForCommit2.get(0).getValue());
    Assertions.assertEquals(3903L, metricValuesForCommit2.get(1).getValue());
    Assertions.assertEquals(7170L, metricValuesForCommit2.get(2).getValue());
    Assertions.assertEquals(4914L, metricValuesForCommit2.get(3).getValue());

    session.clear();
  }

  private void testDeltaTree(Long projectId) throws Exception {
    GetDeltaTreeForTwoCommitsCommand deltaTreeCommand = new GetDeltaTreeForTwoCommitsCommand();
    deltaTreeCommand.setMetrics(
        Arrays.asList(
            "coderadar:size:loc:java",
            "coderadar:size:sloc:java",
            "coderadar:size:cloc:java",
            "coderadar:size:eloc:java"));
    deltaTreeCommand.setCommit1("a50f58decff2d4bb81b9b533f0a8c917a2a3d6a8");
    deltaTreeCommand.setCommit2("9dab8c9b1eec30157a67fbb8834ca5bf3a56407c");

    MvcResult deltaTreeResult =
        mvc()
            .perform(
                get("/api/projects/" + projectId + "/metricvalues/deltaTree")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(deltaTreeCommand)))
            .andReturn();

    DeltaTree deltaTree =
        fromJson(deltaTreeResult.getResponse().getContentAsString(), DeltaTree.class);

    Assertions.assertEquals("root", deltaTree.getName());
    Assertions.assertEquals(MetricTreeNodeType.MODULE, deltaTree.getType());

    List<MetricValueForCommit> commit1Metrics = deltaTree.getCommit1Metrics();
    List<MetricValueForCommit> commit2Metrics = deltaTree.getCommit2Metrics();

    Assertions.assertEquals(23L, commit1Metrics.get(0).getValue());
    Assertions.assertEquals(1022L, commit1Metrics.get(1).getValue());
    Assertions.assertEquals(1721L, commit1Metrics.get(2).getValue());
    Assertions.assertEquals(1360L, commit1Metrics.get(3).getValue());

    Assertions.assertEquals(23L, commit2Metrics.get(0).getValue());
    Assertions.assertEquals(1022L, commit2Metrics.get(1).getValue());
    Assertions.assertEquals(1717L, commit2Metrics.get(2).getValue());
    Assertions.assertEquals(1362L, commit2Metrics.get(3).getValue());

    Assertions.assertEquals(1, deltaTree.getChildren().size());

    DeltaTree firstChild = deltaTree.getChildren().get(0);
    Assertions.assertEquals("src/paysim/", firstChild.getName());
    Assertions.assertEquals(MetricTreeNodeType.MODULE, firstChild.getType());
    Assertions.assertEquals(4, firstChild.getCommit1Metrics().size());
    Assertions.assertEquals(4, firstChild.getCommit2Metrics().size());
    Assertions.assertNull(firstChild.getChanges());

    Assertions.assertEquals(19, firstChild.getChildren().size());

    DeltaTree firstChangedFile = firstChild.getChildren().get(0);
    Assertions.assertEquals("src/paysim/PaySim.java", firstChangedFile.getName());
    Assertions.assertEquals(MetricTreeNodeType.FILE, firstChangedFile.getType());
    Assertions.assertEquals(4, firstChangedFile.getCommit1Metrics().size());
    Assertions.assertEquals(
        firstChangedFile.getCommit2Metrics().get(2).getValue(),
        firstChangedFile.getCommit1Metrics().get(2).getValue() - 3L);
    Assertions.assertTrue(firstChangedFile.getChanges().isModified());

    DeltaTree secondChangedFile = firstChild.getChildren().get(11);
    Assertions.assertEquals("src/paysim/output/KafkaOutput.java", secondChangedFile.getName());
    Assertions.assertEquals(MetricTreeNodeType.FILE, firstChangedFile.getType());
    Assertions.assertEquals(3, secondChangedFile.getCommit1Metrics().size());
    Assertions.assertEquals(
        secondChangedFile.getCommit2Metrics().get(1).getValue(),
        secondChangedFile.getCommit1Metrics().get(1).getValue() - 1L);
    Assertions.assertTrue(secondChangedFile.getChanges().isModified());

    for (int i = 1; i < 19; i++) {
      if (i == 11) {
        continue;
      }
      DeltaTree file = firstChild.getChildren().get(i);
      Assertions.assertEquals(MetricTreeNodeType.FILE, file.getType());
      if (file.getCommit1Metrics().stream()
          .anyMatch(m -> m.getMetricName().equals("coderadar:size:cloc:java"))) {
        Assertions.assertEquals(4, file.getCommit1Metrics().size());
        Assertions.assertEquals(file.getCommit2Metrics().get(2), file.getCommit1Metrics().get(2));
      } else {
        Assertions.assertEquals(3, file.getCommit1Metrics().size());
        Assertions.assertEquals(file.getCommit2Metrics().get(1), file.getCommit1Metrics().get(1));
      }
      Assertions.assertFalse(file.getChanges().isModified());
      Assertions.assertFalse(file.getChanges().isAdded());
      Assertions.assertFalse(file.getChanges().isRenamed());
      Assertions.assertFalse(file.getChanges().isDeleted());
    }
    session.clear();
  }

  private void testAnalysis(Long projectId) throws Exception {
    // Start analysis
    mvc()
        .perform(
            post("/api/projects/" + projectId + "/analyze").contentType(MediaType.APPLICATION_JSON))
        .andReturn();

    // Commits analyzed?
    session.clear();

    Collection<CommitEntity> commits = commitRepository.findByProjectId(projectId);

    for (CommitEntity commit : commits) {
      Assertions.assertTrue(commit.isAnalyzed());
    }

    // Metrics available?
    List<String> availableMetrics = metricQueryRepository.getAvailableMetricsInProject(projectId);
    Assertions.assertEquals(4, availableMetrics.size());
    Assertions.assertTrue(availableMetrics.contains("coderadar:size:sloc:java"));
    Assertions.assertTrue(availableMetrics.contains("coderadar:size:loc:java"));
    Assertions.assertTrue(availableMetrics.contains("coderadar:size:eloc:java"));
    Assertions.assertTrue(availableMetrics.contains("coderadar:size:cloc:java"));

    // Metric values there?
    List<MetricValueEntity> metricValues = metricRepository.findByProjectId(projectId);
    Assertions.assertFalse(metricValues.isEmpty());
    session.clear();
  }

  private void testSavingAnalyzerConfiguration(Long projectId) throws Exception {
    // Add analyzer configuration
    CreateAnalyzerConfigurationCommand analyzerConfigurationCommand =
        new CreateAnalyzerConfigurationCommand(
            "io.reflectoring.coderadar.analyzer.loc.LocAnalyzerPlugin", true);
    mvc()
        .perform(
            post("/api/projects/" + projectId + "/analyzers")
                .content(toJson(analyzerConfigurationCommand))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andReturn();

    // Configuration saved?
    AnalyzerConfigurationEntity analyzerConfiguration =
        analyzerConfigurationRepository.findByProjectId(projectId).get(0);

    // Configuration correct?
    Assertions.assertEquals(
        "io.reflectoring.coderadar.analyzer.loc.LocAnalyzerPlugin",
        analyzerConfiguration.getAnalyzerName());
    Assertions.assertTrue(analyzerConfiguration.getEnabled());
    session.clear();
  }

  private void testSavingFilePatterns(Long projectId) throws Exception {
    // Add file patterns
    CreateFilePatternCommand filePatternCommand =
        new CreateFilePatternCommand("**/*.java", InclusionType.INCLUDE);
    mvc()
        .perform(
            post("/api/projects/" + projectId + "/filePatterns")
                .content(toJson(filePatternCommand))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andReturn();

    // Patterns saved?
    FilePatternEntity filePattern = filePatternRepository.findByProjectId(projectId).get(0);

    // Patterns correct?
    Assertions.assertEquals("**/*.java", filePattern.getPattern());
    Assertions.assertEquals(InclusionType.INCLUDE, filePattern.getInclusionType());
    session.clear();
  }

  private Long testSavingProject() throws Exception {
    URL testRepoURL = new File("coderadar-workdir/PaySim").toURI().toURL();
    CreateProjectCommand command =
        new CreateProjectCommand(
            "PaySim", "username", "password", testRepoURL.toString(), false, null);

    mvc()
        .perform(
            post("/api/projects").contentType(MediaType.APPLICATION_JSON).content(toJson(command)))
        .andExpect(status().isCreated())
        .andReturn();

    // Is the project saved?
    List<ProjectEntity> projectEntities = projectRepository.findAll();
    Assertions.assertEquals(1, projectEntities.size());
    Long projectId = projectEntities.get(0).getId();
    Assertions.assertEquals("PaySim", projectEntities.get(0).getName());

    session.clear();

    // Number of commits correct?
    List<CommitEntity> commitEntities = commitRepository.findByProjectId(projectId);
    Assertions.assertEquals(99, commitEntities.size());

    // Files there?
    List<FileEntity> fileEntities = fileRepository.findAllinProject(projectId);
    Assertions.assertFalse(fileEntities.isEmpty());
    session.clear();
    return projectId;
  }
}
