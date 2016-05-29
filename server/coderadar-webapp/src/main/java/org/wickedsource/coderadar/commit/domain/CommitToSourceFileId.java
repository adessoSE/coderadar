package org.wickedsource.coderadar.commit.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.wickedsource.coderadar.metric.domain.SourceFile;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class CommitToSourceFileId implements Serializable {

    @ManyToOne
    private Commit commit;

    @ManyToOne
    private SourceFile sourceFile;

    public CommitToSourceFileId() {
    }

    public CommitToSourceFileId(Commit commit, SourceFile sourceFile) {
        this.commit = commit;
        this.sourceFile = sourceFile;
    }

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

    public SourceFile getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(SourceFile sourceFile) {
        this.sourceFile = sourceFile;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof CommitToSourceFileId)) {
            return false;
        }
        CommitToSourceFileId that = (CommitToSourceFileId) obj;
        return this.commit.getId().equals(that.commit.getId()) && this.sourceFile.getId().equals(that.sourceFile.getId());
    }

    @Override
    public int hashCode() {
        return 17 + commit.getId().hashCode() + sourceFile.getId().hashCode();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
