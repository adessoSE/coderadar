package org.wickedsource.coderadar.job.execute.scan.repository;

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
class PersistingCommitProcessor implements CommitProcessor {

    private CommitRepository commitRepository;

    private Project project;

    private int updatedCommits;

    PersistingCommitProcessor(CommitRepository commitRepository, Project project) {
        this.commitRepository = commitRepository;
        this.project = project;
    }

    @Override
    public void processCommit(Git gitClient, RevCommit gitCommit) {
        Commit commit = new Commit();
        commit.setName(gitCommit.getName());
        commit.setAuthor(gitCommit.getAuthorIdent().getName());
        commit.setComment(gitCommit.getShortMessage());
        commit.setProject(project);
        commit.setTimestamp(new Date(gitCommit.getCommitTime() * 1000L));
        if(gitCommit.getParentCount() > 0){
            // TODO: support multiple parents
            commit.setParentCommitName(gitCommit.getParent(0).getName());
        }
        commitRepository.save(commit);
        updatedCommits++;
    }

    public int getUpdatedCommitsCount(){
        return updatedCommits;
    }

}
