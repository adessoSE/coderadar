package org.wickedsource.coderadar.job.execute.scan;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.vcs.git.walk.CommitProcessor;

import java.util.Date;

/**
 * Takes a GIT commit and stores it in the database.
 */
public class DatabaseUpdatingCommitProcessor implements CommitProcessor {

    private CommitRepository commitRepository;

    private Project project;

    public DatabaseUpdatingCommitProcessor(CommitRepository commitRepository, Project project) {
        this.commitRepository = commitRepository;
        this.project = project;
    }

    @Override
    public void processCommit(Git gitClient, RevCommit gitCommit) {
        Commit commit = new Commit();
        commit.setName(gitCommit.getName());
        commit.setAuthor(gitCommit.getCommitterIdent().getName());
        commit.setComment(gitCommit.getShortMessage());
        commit.setProject(project);
        commit.setTimestamp(new Date(gitCommit.getCommitTime()));
        commitRepository.save(commit);
    }

}
