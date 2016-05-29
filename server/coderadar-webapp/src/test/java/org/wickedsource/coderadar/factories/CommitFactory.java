package org.wickedsource.coderadar.factories;

import org.wickedsource.coderadar.commit.domain.Commit;

import java.util.Date;

public class CommitFactory {

    public Commit unprocessedCommit(){
        Commit commit = new Commit();
        commit.setParentCommitName("123");
        commit.setName("345");
        commit.setTimestamp(new Date());
        commit.setAuthor("max");
        return commit;
    }
}
