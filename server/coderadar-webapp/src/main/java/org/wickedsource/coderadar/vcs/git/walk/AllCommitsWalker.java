package org.wickedsource.coderadar.vcs.git.walk;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

import java.io.IOException;

/**
 * A RepositoryWalker that walks through ALL commits of a repository.
 */
public class AllCommitsWalker implements CommitWalker {

    private String stopAtCommitName;

    @Override
    public void walk(Git gitClient, CommitProcessor commitProcessor) {
        try {
            ObjectId lastCommitId = gitClient.getRepository().resolve(Constants.HEAD);
            RevWalk revWalk = new RevWalk(gitClient.getRepository());
            RevCommit currentCommit = revWalk.parseCommit(lastCommitId);

            while (currentCommit != null && !isStopCommit(currentCommit)) {
                commitProcessor.processCommit(gitClient, currentCommit);
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

    private boolean isStopCommit(RevCommit commit){
        return stopAtCommitName != null && commit.getName().equals(stopAtCommitName);
    }

    public void stopAtCommit(String commitName){
        this.stopAtCommitName = commitName;
    }


}