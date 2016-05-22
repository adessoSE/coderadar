package org.wickedsource.coderadar.job.scan.commit;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.job.LocalGitRepositoryUpdater;
import org.wickedsource.coderadar.metric.domain.SourceFile;
import org.wickedsource.coderadar.metric.domain.SourceFileRepository;
import org.wickedsource.coderadar.vcs.git.ChangeTypeMapper;
import org.wickedsource.coderadar.vcs.git.GitCommitFinder;

import java.io.IOException;
import java.util.List;

@Service
public class CommitScanner {

    private Logger logger = LoggerFactory.getLogger(CommitScanner.class);

    private LocalGitRepositoryUpdater updater;

    private CommitRepository commitRepository;

    private GitCommitFinder commitFinder;

    private ChangeTypeMapper changeTypeMapper = new ChangeTypeMapper();

    private SourceFileRepository sourceFileRepository;

    @Autowired
    public CommitScanner(LocalGitRepositoryUpdater updater, CommitRepository commitRepository, GitCommitFinder commitFinder, SourceFileRepository sourceFileRepository) {
        this.updater = updater;
        this.commitRepository = commitRepository;
        this.commitFinder = commitFinder;
        this.sourceFileRepository = sourceFileRepository;
    }

    public void scan(Long commitId) {
        try {
            Commit commit = commitRepository.findOne(commitId);
            if (commit == null) {
                throw new IllegalArgumentException(String.format("Commit with ID %d does not exist!", commitId));
            }
            Git gitClient = updater.updateLocalGitRepository(commit.getProject());
            walkFilesInCommit(gitClient, commit);
        } catch (IOException e) {
            throw new IllegalStateException(String.format("error while scanning commit %d", commitId));
        }
    }

    private void walkFilesInCommit(Git gitClient, Commit commit) throws IOException {
        logger.info("starting scan of commit {}", commit);
        RevCommit gitCommit = commitFinder.findCommit(gitClient, commit.getName());
        DiffFormatter diffFormatter = new DiffFormatter(DisabledOutputStream.INSTANCE);
        diffFormatter.setRepository(gitClient.getRepository());
        diffFormatter.setDiffComparator(RawTextComparator.DEFAULT);
        diffFormatter.setDetectRenames(true);

        ObjectId parentId = null;
        if (gitCommit.getParentCount() > 0) {
            // TODO: support multiple parents
            parentId = gitCommit.getParent(0).getId();
        }

        List<DiffEntry> diffs = diffFormatter.scan(parentId, gitCommit);
        int fileCounter = 0;
        for (DiffEntry diff : diffs) {
            SourceFile sourceFile = new SourceFile();
            sourceFile.setCommitName(commit.getName());
            sourceFile.setCommit(commit);
            sourceFile.setChangeType(changeTypeMapper.jgitToCoderadar(diff.getChangeType()));
            sourceFile.setFilepath(diff.getNewPath());
            if (diff.getChangeType() == DiffEntry.ChangeType.RENAME) {
                sourceFile.setFilepathBeforeRename(diff.getOldPath());
            }
            sourceFileRepository.save(sourceFile);
            fileCounter++;
        }
        logger.info("scanned {} files in commit {}", fileCounter, commit);
    }

}
