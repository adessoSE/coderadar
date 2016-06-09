package org.wickedsource.coderadar.job.analyze;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import org.gitective.core.BlobUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.analyzer.api.FileMetrics;
import org.wickedsource.coderadar.analyzer.api.Metric;
import org.wickedsource.coderadar.analyzer.api.SourceCodeFileAnalyzerPlugin;
import org.wickedsource.coderadar.analyzer.domain.AnalyzerConfiguration;
import org.wickedsource.coderadar.analyzer.domain.AnalyzerConfigurationRepository;
import org.wickedsource.coderadar.analyzer.domain.AnalyzerPluginRegistry;
import org.wickedsource.coderadar.analyzer.match.FileMatchingPattern;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.core.WorkdirManager;
import org.wickedsource.coderadar.file.domain.File;
import org.wickedsource.coderadar.file.domain.FileRepository;
import org.wickedsource.coderadar.filepattern.domain.FilePattern;
import org.wickedsource.coderadar.filepattern.domain.FilePatternRepository;
import org.wickedsource.coderadar.filepattern.domain.FileType;
import org.wickedsource.coderadar.metric.domain.MetricValue;
import org.wickedsource.coderadar.metric.domain.MetricValueId;
import org.wickedsource.coderadar.metric.domain.MetricValueRepository;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.vcs.git.GitCommitFinder;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Service
public class CommitAnalyzer {

    private Logger logger = LoggerFactory.getLogger(CommitAnalyzer.class);

    private CommitRepository commitRepository;

    private WorkdirManager workdirManager;

    private FilePatternRepository filePatternRepository;

    private GitCommitFinder commitFinder;

    private AnalyzerPluginRegistry analyzerRegistry;

    private AnalyzerConfigurationRepository analyzerConfigurationRepository;

    private FileAnalyzer fileAnalyzer;

    private FileRepository fileRepository;

    private MetricValueRepository metricValueRepository;

    private static final Set<DiffEntry.ChangeType> CHANGES_TO_ANALYZE = EnumSet.of(DiffEntry.ChangeType.ADD, DiffEntry.ChangeType.COPY, DiffEntry.ChangeType.MODIFY);

    @Autowired
    public CommitAnalyzer(CommitRepository commitRepository, WorkdirManager workdirManager, FilePatternRepository filePatternRepository, GitCommitFinder commitFinder, AnalyzerPluginRegistry analyzerRegistry, AnalyzerConfigurationRepository analyzerConfigurationRepository, FileAnalyzer fileAnalyzer, FileRepository fileRepository, MetricValueRepository metricValueRepository) {
        this.commitRepository = commitRepository;
        this.workdirManager = workdirManager;
        this.filePatternRepository = filePatternRepository;
        this.commitFinder = commitFinder;
        this.analyzerRegistry = analyzerRegistry;
        this.analyzerConfigurationRepository = analyzerConfigurationRepository;
        this.fileAnalyzer = fileAnalyzer;
        this.fileRepository = fileRepository;
        this.metricValueRepository = metricValueRepository;
    }

    public void analyzeCommit(Long commitId) {
        Commit commit = commitRepository.findOne(commitId);
        if (commit == null) {
            throw new IllegalArgumentException(String.format("commit with ID %d does not exist!", commitId));
        }

        Path gitRoot = workdirManager.getLocalGitRoot(commit.getProject().getId());
        try {
            Git gitClient = Git.open(gitRoot.toFile());
            walkFilesInCommit(gitClient, commit, getAnalyzersForProject(commit.getProject()));
        } catch (IOException e) {
            throw new IllegalStateException(String.format("error opening git root %s", gitRoot));
        }
        commit.setAnalyzed(Boolean.TRUE);
    }

    private List<SourceCodeFileAnalyzerPlugin> getAnalyzersForProject(Project project) {
        List<SourceCodeFileAnalyzerPlugin> analyzers = new ArrayList<>();
        List<AnalyzerConfiguration> configs = analyzerConfigurationRepository.findByProjectId(project.getId());
        for (AnalyzerConfiguration config : configs) {
            analyzers.add(analyzerRegistry.getAnalyzer(config.getAnalyzerName()));
        }
        return analyzers;
    }

    private void walkFilesInCommit(Git gitClient, Commit commit, List<SourceCodeFileAnalyzerPlugin> analyzers) throws IOException {
        if (analyzers.isEmpty()) {
            logger.warn("skipping analysis of commit {} since there are no analyzers configured for project {}!", commit.getName(), commit.getProject().getName());
            return;
        }
        logger.info("starting analysis of commit {}", commit.getName());

        RevCommit gitCommit = commitFinder.findCommit(gitClient, commit.getName());
        if (gitCommit == null) {
            throw new IllegalArgumentException(String.format("commit with name %s was not found in git root %s", commit.getName(), gitClient.getRepository().getDirectory()));
        }

        DiffFormatter diffFormatter = new DiffFormatter(DisabledOutputStream.INSTANCE);
        diffFormatter.setRepository(gitClient.getRepository());
        diffFormatter.setDiffComparator(RawTextComparator.DEFAULT);
        diffFormatter.setDetectRenames(true);

        ObjectId parentId = null;
        if (gitCommit.getParentCount() > 0) {
            // TODO: support multiple parents
            parentId = gitCommit.getParent(0).getId();
        }

        List<FilePattern> sourceFilePatterns = filePatternRepository.findByProjectIdAndFileType(commit.getProject().getId(), FileType.SOURCE);
        FileMatchingPattern pattern = toFileMatchingPattern(sourceFilePatterns);

        List<DiffEntry> diffs = diffFormatter.scan(parentId, gitCommit);
        for (DiffEntry diff : diffs) {
            String filepath = diff.getPath(DiffEntry.Side.NEW);

            if (!shouldBeAnalyzed(diff.getChangeType())) {
                logger.debug("skipping analysis of file {} because of changetype {}", filepath, diff.getChangeType());
                continue;
            }
            if (!pattern.matches(filepath)) {
                logger.debug("skipping analysis of file {} because it does not match source file pattern of project {}", filepath, commit.getProject().getName());
                continue;
            }

            byte[] fileContent = BlobUtils.getRawContent(gitClient.getRepository(), gitCommit.getId(), filepath);
            FileMetrics metrics = fileAnalyzer.analyzeFile(analyzers, filepath, fileContent);
            storeMetrics(commit, filepath, metrics);
            // TODO: store findings (i.e. code pointers)
        }
    }

    private boolean shouldBeAnalyzed(DiffEntry.ChangeType changeType) {
        return CHANGES_TO_ANALYZE.contains(changeType);
    }

    private void storeMetrics(Commit commit, String filePath, FileMetrics metrics) {
        File file = fileRepository.findInCommit(filePath, commit.getName());
        if (file == null) {
            throw new IllegalStateException(String.format("file %s not found for commit %s in database!", filePath, commit.getName()));
        }

        for (Metric metric : metrics.getMetrics()) {
            MetricValueId id = new MetricValueId(commit, file, metric.getId());
            MetricValue metricValue = new MetricValue(id, metrics.getMetricCount(metric));
            metricValueRepository.save(metricValue);
        }
    }

    private FileMatchingPattern toFileMatchingPattern(List<FilePattern> filePatternList) {
        FileMatchingPattern pattern = new FileMatchingPattern();
        for (FilePattern files : filePatternList) {
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
