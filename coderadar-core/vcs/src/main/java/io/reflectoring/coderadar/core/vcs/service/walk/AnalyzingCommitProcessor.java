package io.reflectoring.coderadar.core.vcs.service.walk;

import io.reflectoring.coderadar.core.vcs.domain.CommitProcessor;
import io.reflectoring.coderadar.core.vcs.domain.RevCommitWithSequenceNumber;
import io.reflectoring.coderadar.core.vcs.service.ChangeTypeMapper;
import io.reflectoring.coderadar.core.vcs.service.MetricsProcessor;
import io.reflectoring.coderadar.plugin.api.SourceCodeFileAnalyzerPlugin;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import org.gitective.core.BlobUtils;
import org.gitective.core.CommitUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class AnalyzingCommitProcessor implements CommitProcessor {

  private Logger logger = LoggerFactory.getLogger(AnalyzingCommitProcessor.class);

  // private FileAnalyzer fileAnalyzer = new FileAnalyzer();

  private ChangeTypeMapper changeTypeMapper = new ChangeTypeMapper();

  private List<SourceCodeFileAnalyzerPlugin> analyzers;

  private MetricsProcessor metricsProcessor;

  public AnalyzingCommitProcessor(
      List<SourceCodeFileAnalyzerPlugin> analyzers, MetricsProcessor metricsProcessor) {
    this.analyzers = analyzers;
    this.metricsProcessor = metricsProcessor;
  }

  @Override
  public void processCommit(Git gitClient, RevCommitWithSequenceNumber commitWithSequenceNumber) {
    RevCommit commit = commitWithSequenceNumber.getCommit();
    try {
      walkFilesInCommit(gitClient, commit, analyzers, metricsProcessor);
    } catch (IOException e) {
      throw new IllegalStateException(
          String.format("error while processing GIT commit %s", commit.name()));
    }
  }

  private void walkFilesInCommit(
      Git gitClient,
      RevCommit commit,
      List<SourceCodeFileAnalyzerPlugin> analyzers,
      MetricsProcessor metricsProcessor)
      throws IOException {
    commit = CommitUtils.getCommit(gitClient.getRepository(), commit.getId());
    logger.info("starting analysis of commit {}", commit.getName());
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
      byte[] fileContent =
          BlobUtils.getRawContent(gitClient.getRepository(), commit.getId(), filePath);
      // TODO: FileAnalyzer needed
      // FileMetrics metrics = fileAnalyzer.analyzeFile(analyzers, filePath, fileContent);
      /*FileMetricsWithChangeType metricsWithChangeType =
          new FileMetricsWithChangeType(
              metrics, changeTypeMapper.jgitToCoderadar(diff.getChangeType()));
      metricsProcessor.processMetrics(metricsWithChangeType, gitClient, commit.getId(), filePath);*/
    }
    metricsProcessor.onCommitFinished(gitClient, commit.getId());
  }
}
