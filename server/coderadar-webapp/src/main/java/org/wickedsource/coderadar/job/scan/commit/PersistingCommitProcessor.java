package org.wickedsource.coderadar.job.scan.commit;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.vcs.git.GitCommitFinder;
import org.wickedsource.coderadar.vcs.git.walk.CommitProcessor;

import java.util.Date;

/**
 * Takes a GIT commit and stores it in the database.
 */
class PersistingCommitProcessor implements CommitProcessor {

    private CommitRepository commitRepository;

    private Project project;

    private int updatedCommits;

    private GitCommitFinder commitFinder;

    PersistingCommitProcessor(CommitRepository commitRepository, Project project, GitCommitFinder commitFinder) {
        this.commitRepository = commitRepository;
        this.project = project;
        this.commitFinder = commitFinder;
    }

    @Override
    public void processCommit(Git gitClient, RevCommit gitCommit) {
        Commit commit = new Commit();
        commit.setName(gitCommit.getName());
        commit.setAuthor(gitCommit.getAuthorIdent().getName());
        commit.setComment(gitCommit.getShortMessage());
        commit.setProject(project);
        commit.setTimestamp(new Date(gitCommit.getCommitTime() * 1000L));
        commit.setSequenceNumber(getSequenceNumberForCommit(gitClient, gitCommit));
        if (gitCommit.getParents() != null && gitCommit.getParentCount() > 0) {
            // TODO: support multiple parents?
            commit.setParentCommitName(gitCommit.getParent(0).getName());
        }
        commitRepository.save(commit);
        updatedCommits++;
    }

    private int getSequenceNumberForCommit(Git gitClient, RevCommit gitCommit) {
        if (gitCommit.getParents() == null || gitCommit.getParentCount() == 0) {
            return 1;
        } else {
            String parentName = gitCommit.getParent(0).getName();
            RevCommit parentCommit = commitFinder.findCommit(gitClient, parentName);
            // TODO: support multiple parents?
            return getSequenceNumberForCommit(gitClient, parentCommit) + 1;
        }
    }

    public int getUpdatedCommitsCount() {
        return updatedCommits;
    }

}
