package org.wickedsource.coderadar.job.scan.commit;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.profiler.Profiler;
import org.wickedsource.coderadar.CoderadarConfiguration;
import org.wickedsource.coderadar.GitTestTemplate;
import org.wickedsource.coderadar.IntegrationTest;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.core.WorkdirManager;
import org.wickedsource.coderadar.factories.Factories;
import org.wickedsource.coderadar.job.LocalGitRepositoryUpdater;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;
import org.wickedsource.coderadar.project.domain.VcsCoordinates;
import org.wickedsource.coderadar.project.domain.VcsType;
import org.wickedsource.coderadar.vcs.git.GitRepositoryChecker;
import org.wickedsource.coderadar.vcs.git.GitRepositoryCloner;
import org.wickedsource.coderadar.vcs.git.GitRepositoryUpdater;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.mockito.Mockito.*;

public class CommitScannerIntegrationTest extends GitTestTemplate {

    private Logger logger = LoggerFactory.getLogger(ScanCommitsJob.class);

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private CommitRepository commitRepository;

    @Spy
    private GitRepositoryChecker gitChecker;

    @Spy
    private GitRepositoryCloner gitCloner;

    @Spy
    private GitRepositoryUpdater gitUpdater;

    @Mock
    private WorkdirManager workdirManager;

    private LocalGitRepositoryUpdater updater;

    private CoderadarConfiguration config;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        config = createConfig();
        mock(workdirManager);
        updater = new LocalGitRepositoryUpdater(gitUpdater, gitCloner, gitChecker, workdirManager);
    }

    @After
    public void cleanup() {
        try {
            if (!Boolean.valueOf(System.getProperty("coderadar.keepTempFiles"))) {
                FileUtils.deleteDirectory(config.getWorkdir().toFile());
            }
        } catch (IOException e) {
            logger.warn("could not delete temp dir at {}", config.getWorkdir());
        }
    }

    @Test
    @Category(IntegrationTest.class)
    public void scan() {
        Project project = Factories.project().validProject();
        Profiler profiler = new Profiler("Scanner");
        profiler.setLogger(logger);
        CommitScanner scanner = new CommitScanner(commitRepository, updater, projectRepository);
        when(projectRepository.findOne(project.getId())).thenReturn(createProject());
        profiler.start("scanning without local repository present");
        File repoRoot = scanner.scan(project).getParentFile();
        Assert.assertTrue(gitChecker.isRepository(repoRoot.toPath()));
        // scanning again should be fairly quick, since the repository is already cloned
        profiler.start("re-scanning with local repository present from last test");
        scanner.scan(project);
        Assert.assertTrue(gitChecker.isRepository(repoRoot.toPath()));

        verify(commitRepository, atLeast(20)).save(any(Commit.class));
        profiler.stop().log();
    }

    private CoderadarConfiguration createConfig() {
        File dir = createTempDir();

        CoderadarConfiguration config = new CoderadarConfiguration();
        config.setMaster(true);
        config.setSlave(true);
        config.setWorkdir(dir.toPath());
        return config;
    }

    private Project createProject() {
        try {
            Project project = new Project();
            project.setId(1L);
            project.setName("coderadar");
            VcsCoordinates vcs = new VcsCoordinates();
            vcs.setUrl(new URL("https://github.com/thombergs/diffparser.git"));
            vcs.setType(VcsType.GIT);
            project.setVcsCoordinates(vcs);
            return project;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}