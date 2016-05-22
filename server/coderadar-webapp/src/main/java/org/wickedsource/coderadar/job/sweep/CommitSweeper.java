package org.wickedsource.coderadar.job.sweep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.analyzer.Analyzer;
import org.wickedsource.coderadar.analyzer.AnalyzerConfiguration;
import org.wickedsource.coderadar.analyzer.api.FileSetMetrics;
import org.wickedsource.coderadar.analyzer.match.FileMatchingPattern;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.core.WorkdirManager;
import org.wickedsource.coderadar.projectfiles.domain.ProjectFiles;
import org.wickedsource.coderadar.projectfiles.domain.ProjectFilesRepository;
import org.wickedsource.coderadar.vcs.git.GitCommitFetcher;

import java.nio.file.Path;
import java.util.List;
import java.util.Properties;

@Service
public class CommitSweeper {

    private Logger logger = LoggerFactory.getLogger(CommitSweeper.class);

    private GitCommitFetcher fetcher;

    private CommitRepository commitRepository;

    private WorkdirManager workdirManager;

    private ProjectFilesRepository projectFilesRepository;

    @Autowired
    public CommitSweeper(GitCommitFetcher fetcher, CommitRepository commitRepository, WorkdirManager workdirManager, ProjectFilesRepository projectFilesRepository) {
        this.fetcher = fetcher;
        this.commitRepository = commitRepository;
        this.workdirManager = workdirManager;
        this.projectFilesRepository = projectFilesRepository;
    }

    public void sweepCommit(Long commitId) {
//        long start = System.currentTimeMillis();
//        logger.debug("starting sweep of commit {}", commitId);
//        Commit commit = commitRepository.findOne(commitId);
//        if (commit == null) {
//            throw new IllegalArgumentException(String.format("Commit with ID %d does not exist!", commitId));
//        }
//        Project project = commit.getProject();
//        VcsCoordinates vcsCoordinates = project.getVcsCoordinates();
//        Path workdir = workdirManager.getWorkdirForCommitSweep(project.getId());
//        fetcher.fetchCommit(commit.getName(), vcsCoordinates, workdir);
//        FileSetMetrics metrics = analyze(commit, workdir);
//
//        // TODO: store metrics to database
//
//        commit.setSweeped(true);
//        commitRepository.save(commit);
//
//        long duration = System.currentTimeMillis() - start;
//        logger.info("finished sweep of commit {} in {} milliseconds", commitId, duration);
    }

    private FileSetMetrics analyze(Commit commit, Path workdir) {
        AnalyzerConfiguration config = new AnalyzerConfiguration();
        // TODO: load pluginProperties from project configuration instead of hard coding them
        Properties pluginProperties = new Properties();
        pluginProperties.setProperty("org.wickedsource.coderadar.analyzer.checkstyle.CheckstyleSourceCodeFileAnalyzerPlugin.enabled", String.valueOf(Boolean.FALSE));
        pluginProperties.setProperty("org.wickedsource.coderadar.analyzer.findbugs.xsd.FindbugsAnalyzerPlugin.enabled", String.valueOf(Boolean.FALSE));
        pluginProperties.setProperty("org.wickedsource.coderadar.analyzer.loc.LocSourceCodeFileAnalyzerPlugin.enabled", String.valueOf(Boolean.TRUE));
        pluginProperties.setProperty("org.wickedsource.coderadar.analyzer.todo.TodoSourceCodeFileAnalyzerPlugin.enabled", String.valueOf(Boolean.TRUE));
        config.setPluginProperties(pluginProperties);

        List<ProjectFiles> projectFilesList = projectFilesRepository.findByProjectId(commit.getProject().getId());
        config.setSourceCodeFiles(sourceCodeFiles(workdir, projectFilesList));

        Analyzer analyzer = new Analyzer();
        return analyzer.analyze(config);
    }

    private FileMatchingPattern sourceCodeFiles(Path workdir, List<ProjectFiles> projectFilesList) {
        FileMatchingPattern pattern = new FileMatchingPattern(workdir);
        for (ProjectFiles files : projectFilesList) {
            switch (files.getInclusionType()) {
                case INCLUDE:
                    pattern.addIncludePattern(files.getPattern());
                    break;
                case EXCLUDE:
                    pattern.addExcludePattern(files.getPattern());
                    break;
                default:
                    throw new IllegalStateException(String.format("invalid InclusionType %s", files.getInclusionType()));
            }
        }
        return pattern;
    }

}
