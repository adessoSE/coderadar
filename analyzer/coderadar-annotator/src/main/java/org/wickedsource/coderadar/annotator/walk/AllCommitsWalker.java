package org.wickedsource.coderadar.annotator.walk;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import org.gitective.core.BlobUtils;
import org.gitective.core.CommitUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wickedsource.coderadar.analyzer.api.Analyzer;
import org.wickedsource.coderadar.analyzer.api.FileMetrics;
import org.wickedsource.coderadar.annotator.analyze.FileAnalyzer;
import org.wickedsource.coderadar.annotator.annotate.MetricsProcessor;

import java.io.IOException;
import java.util.List;

/**
 * A RepositoryWalker that walks through ALL commits of a repository.
 */
public class AllCommitsWalker implements RepositoryWalker {

    private Logger logger = LoggerFactory.getLogger(AllCommitsWalker.class);

    private FileAnalyzer fileAnalyzer = new FileAnalyzer();

    @Override
    public void walk(Git gitClient, List<Analyzer> analyzers, MetricsProcessor metricsProcessor) {
        try {
            ObjectId lastCommitId = gitClient.getRepository().resolve(Constants.HEAD);
            RevWalk revWalk = new RevWalk(gitClient.getRepository());
            RevCommit currentCommit = revWalk.parseCommit(lastCommitId);

            while (currentCommit != null) {
                walkFilesInCommit(gitClient, currentCommit, analyzers, metricsProcessor);
                if (currentCommit.getParentCount() > 0) {
                    currentCommit = revWalk.parseCommit(currentCommit.getParent(0).getId()); //TODO: support multiple parents
                } else {
                    currentCommit = null;
                }
            }

            revWalk.dispose();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private RevCommit getLatestCommit(Git gitClient) throws GitAPIException {
        Iterable<RevCommit> commits = gitClient.log()
                .setMaxCount(1)
                .call();
        return commits.iterator().next();
    }

    private void walkFilesInCommit(Git gitClient, RevCommit commit, List<Analyzer> analyzers, MetricsProcessor metricsProcessor) throws IOException {
        commit = CommitUtils.getCommit(gitClient.getRepository(), commit.getId());
        logger.info("analyzing commit {}", commit.getName());
        DiffFormatter diffFormatter = new DiffFormatter(DisabledOutputStream.INSTANCE);
        diffFormatter.setRepository(gitClient.getRepository());
        diffFormatter.setDiffComparator(RawTextComparator.DEFAULT);
        diffFormatter.setDetectRenames(true);

        ObjectId parentId = null;
        if (commit.getParentCount() > 0) {
            // TODO: support multiple parents
            parentId = commit.getParent(0).getId();
        }

        List<DiffEntry> diffs = diffFormatter.scan(parentId, commit);
        for (DiffEntry diff : diffs) {
            String filePath = diff.getPath(DiffEntry.Side.NEW);
            byte[] fileContent = BlobUtils.getRawContent(gitClient.getRepository(), commit.getId(), filePath);
            FileMetrics metrics = fileAnalyzer.analyzeFile(analyzers, filePath, fileContent);
            FileMetricsWithChangeType metricsWithChangeType = new FileMetricsWithChangeType(metrics, diff.getChangeType());
            metricsProcessor.processMetrics(metricsWithChangeType, gitClient, commit.getId(), filePath);
        }
        metricsProcessor.onCommitFinished(gitClient, commit.getId());
    }
}
