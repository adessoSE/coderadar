package org.wickedsource.coderadar.job.scan.file;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.file.domain.GitLogEntry;
import org.wickedsource.coderadar.file.domain.GitLogEntryRepository;
import org.wickedsource.coderadar.job.LocalGitRepositoryUpdater;
import org.wickedsource.coderadar.vcs.git.ChangeTypeMapper;
import org.wickedsource.coderadar.vcs.git.GitCommitFinder;

import java.io.IOException;
import java.util.List;

@Service
public class FileScanner {

    private Logger logger = LoggerFactory.getLogger(FileScanner.class);

    private LocalGitRepositoryUpdater updater;

    private CommitRepository commitRepository;

    private GitCommitFinder commitFinder;

    private ChangeTypeMapper changeTypeMapper = new ChangeTypeMapper();

    private GitLogEntryRepository logEntryRepository;

    @Autowired
    public FileScanner(
            LocalGitRepositoryUpdater updater,
            CommitRepository commitRepository,
            GitCommitFinder commitFinder,
            GitLogEntryRepository logEntryRepository) {
        this.updater = updater;
        this.commitRepository = commitRepository;
        this.commitFinder = commitFinder;
        this.logEntryRepository = logEntryRepository;
    }

    /**
     * Goes through all files that were touched in a commit and stores meta data about the files in a "git log" like
     * database table (see {@link GitLogEntry}).
     *
     * @param commit the commit whose files to extract and store in the database.
     */
    public void scan(Commit commit) {
        try {
            if (commit == null) {
                throw new IllegalArgumentException(
                        String.format("Commit with ID %d does not exist!", commit.getId()));
            }
            Git gitClient = updater.updateLocalGitRepository(commit.getProject());
            walkFilesInCommit(gitClient, commit);
        } catch (IOException e) {
            throw new IllegalStateException(
                    String.format("error while scanning commit %d", commit.getId()));
        }
    }

    private void walkFilesInCommit(Git gitClient, Commit commit) throws IOException {
        logger.info("starting scan of commit {}", commit);
        RevCommit gitCommit = commitFinder.findCommit(gitClient, commit.getName());
        DiffFormatter diffFormatter = new DiffFormatter(DisabledOutputStream.INSTANCE);
        diffFormatter.setRepository(gitClient.getRepository());
        diffFormatter.setDiffComparator(RawTextComparator.DEFAULT);
        diffFormatter.setDetectRenames(true);

        int fileCounter = 0;
        for (RevCommit parent : gitCommit.getParents()) {
            List<DiffEntry> diffs = diffFormatter.scan(parent, gitCommit);
            for (DiffEntry diff : diffs) {
                GitLogEntry logEntry = new GitLogEntry();
                logEntry.setCommitName(commit.getName());
                logEntry.setParentCommitName(parent.getName());
                logEntry.setOldFilepath(diff.getOldPath());
                logEntry.setFilepath(diff.getNewPath());
                logEntry.setChangeType(changeTypeMapper.jgitToCoderadar(diff.getChangeType()));
                logEntry.setProject(commit.getProject());
                logEntryRepository.save(logEntry);
                fileCounter++;
            }
        }
        commit.setScanned(true);
        commitRepository.save(commit);
        logger.info("scanned {} files in commit {}", fileCounter, commit);
    }
}
