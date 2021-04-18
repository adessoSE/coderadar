package io.reflectoring.coderadar.rest.commitScan;

import com.google.common.io.Files;
import io.reflectoring.coderadar.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.projectadministration.service.project.CreateProjectService;
import io.reflectoring.coderadar.projectadministration.service.project.ScanProjectScheduler;
import io.reflectoring.coderadar.query.port.driven.GetCommitsInProjectPort;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ChangeCommitHistoryIntegrationTest extends ControllerTestTemplate {

  @Autowired private CreateProjectService createProjectService;

  @Autowired private ScanProjectScheduler scanProjectScheduler;

  @Autowired private GetProjectPort getProjectPort;

  @Autowired private GetCommitsInProjectPort getCommitsInProjectPort;

  private Project project;

  File temp;

  @BeforeEach
  void setUp() throws URISyntaxException, IOException {
    URL testRepoURL = this.getClass().getClassLoader().getResource("test-commit-history");

    temp = Files.createTempDir();
    temp.deleteOnExit();
    FileUtils.copyDirectory(new File(testRepoURL.toURI()), temp);

    CreateProjectCommand command =
        new CreateProjectCommand(
            "testProject", null, null, temp.toURI().toString(), false, null, "master");
    project = getProjectPort.get(createProjectService.createProject(command));
  }

  @Test
  void pushAddsCommitTest() throws GitAPIException, IOException {
    Git git =
        Git.cloneRepository()
            .setURI(temp.toURI().toString())
            .setDirectory(Files.createTempDir())
            .setBare(false)
            .call();

    List<File> fileToEdit =
        Arrays.stream(git.getRepository().getWorkTree().listFiles())
            .filter(file -> file.getName().equals("CoderadarApplication.java"))
            .collect(Collectors.toList());
    editFileContent(fileToEdit.get(0));

    git.add().addFilepattern(".").call();
    git.commit().setMessage("testCommit").call();
    git.push().add("master").call();

    Assertions.assertEquals(
        3,
        getCommitsInProjectPort
            .getCommitsSortedByTimestampDescWithNoRelationships(project.getId(), "master")
            .size());

    scanProjectScheduler.checkForNewCommits(project);

    Assertions.assertEquals(
        4,
        getCommitsInProjectPort
            .getCommitsSortedByTimestampDescWithNoRelationships(project.getId(), "master")
            .size());
  }

  @Test
  void forcePushDeletesCommitTest() throws GitAPIException {
    Git git =
        Git.cloneRepository()
            .setURI(temp.toURI().toString())
            .setDirectory(Files.createTempDir())
            .call();
    git.reset().setRef("5f9024680de7b2fac6029688e6e1cdf8e5113131").call();
    git.push().setForce(true).add("master").call();

    Assertions.assertEquals(
        3,
        getCommitsInProjectPort
            .getCommitsSortedByTimestampDescWithNoRelationships(project.getId(), "master")
            .size());

    scanProjectScheduler.checkForNewCommits(project);

    Assertions.assertEquals(
        1,
        getCommitsInProjectPort
            .getCommitsSortedByTimestampDescWithNoRelationships(project.getId(), "master")
            .size());
  }

  void editFileContent(File file) throws IOException {
    List<String> lines = new ArrayList<>();
    String line;
    FileReader fr = new FileReader(file);
    BufferedReader br = new BufferedReader(fr);
    while ((line = br.readLine()) != null) {
      if (line.contains("Application")) line = line.replace("application", " ");
      lines.add(line);
    }
    FileWriter fw = new FileWriter(file);
    BufferedWriter out = new BufferedWriter(fw);
    out.write(lines.toString());
  }
}
