package io.reflectoring.coderadar.rest.commitScan;

import com.google.common.io.Files;
import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.projectadministration.service.project.CreateProjectService;
import io.reflectoring.coderadar.projectadministration.service.project.ScanProjectScheduler;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class ChangeCommitHistoryIntegrationTest extends ControllerTestTemplate {

    @Autowired
    private CreateProjectService createProjectService;

    @Autowired
    private CoderadarConfigurationProperties coderadarConfigurationProperties;

    @Autowired
    private ScanProjectScheduler scanProjectScheduler;

    @Autowired
    private GetProjectPort getProjectPort;

    private Project project;

    File temp;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        URL testRepoURL = this.getClass().getClassLoader().getResource("test-commit-history");

        temp = Files.createTempDir();
        temp.deleteOnExit();
        FileUtils.copyDirectory(new File(testRepoURL.toURI()), temp);

        CreateProjectCommand command = new CreateProjectCommand(
                "testProject",
                null,
                null,
                temp.toURI().toString(),
                false,
                null);
        project = getProjectPort.get(createProjectService.createProject(command));
    }

    @Test
    public void forcePushDeletesCommitTest() throws GitAPIException {
        Git git = Git.cloneRepository().setURI(temp.toURI().toString()).call();
        git.reset().setRef("5f9024680de7b2fac6029688e6e1cdf8e5113131").call();
        git.push().setForce(true).add("master").call();

        scanProjectScheduler.checkForNewCommits(project);
    }
}
