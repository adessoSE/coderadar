package org.wickedsource.coderadar.analyzer;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.io.DisabledOutputStream;

import java.io.IOException;
import java.util.List;

public class GitCommitWalker {

    private Git git;

    public GitCommitWalker(Git git) {
        this.git = git;
    }

    public void walkCommits() {
        try {
            LogCommand logCommand = git.log();
            Iterable<RevCommit> commits = logCommand
                    .all()
                    .call();
            for (RevCommit commit : commits) {
                System.out.println(String.format("Commit %s", commit.getName()));
                DiffFormatter diffFormatter = new DiffFormatter(DisabledOutputStream.INSTANCE);
                diffFormatter.setRepository(git.getRepository());
                diffFormatter.setDiffComparator(RawTextComparator.DEFAULT);
                diffFormatter.setDetectRenames(true);

                ObjectId parentId = null;
                if (commit.getParentCount() > 0) {
                    // TODO: support branches (commits with 2 parents)
                    parentId = commit.getParent(0).getId();
                }

                List<DiffEntry> diffs = diffFormatter.scan(parentId, commit);
                for (DiffEntry diff : diffs) {
                    System.out.println(String.format("Path: %s; ChangeType: %s", diff.getPath(DiffEntry.Side.NEW), diff.getChangeType()));
                }
            }
        } catch (IOException | GitAPIException e) {
            throw new RuntimeException(e);
        }
    }
}
