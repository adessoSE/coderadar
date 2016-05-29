package org.wickedsource.coderadar.commit.domain;

import org.wickedsource.coderadar.analyzer.api.ChangeType;
import org.wickedsource.coderadar.metric.domain.SourceFile;

import javax.persistence.*;

@Entity
@Table(name="commit_sourcefile")
@AssociationOverrides({
        @AssociationOverride(name = "id.commit", joinColumns = @JoinColumn(name = "commit_id")),
        @AssociationOverride(name = "id.sourceFile", joinColumns = @JoinColumn(name = "sourcefile_id"))}
)
public class CommitToSourceFileAssociation {

    @EmbeddedId
    private CommitToSourceFileId id;

    @Column
    @Enumerated(EnumType.STRING)
    private ChangeType changeType;

    public CommitToSourceFileAssociation(){

    }

    public CommitToSourceFileAssociation(Commit commit, SourceFile sourcefile, ChangeType changeType){
        this.id = new CommitToSourceFileId(commit, sourcefile);
        this.changeType = changeType;
    }

    @Transient
    public Commit getCommit() {
        return id.getCommit();
    }

    @Transient
    public SourceFile getSourceFile() {
        return id.getSourceFile();
    }

    public CommitToSourceFileId getId() {
        return id;
    }

    public void setId(CommitToSourceFileId id) {
        this.id = id;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public void setChangeType(ChangeType changeType) {
        this.changeType = changeType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof CommitToSourceFileAssociation)) {
            return false;
        }
        CommitToSourceFileAssociation that = (CommitToSourceFileAssociation) obj;
        return this.id.equals(that.id) && this.changeType == that.changeType;
    }

    @Override
    public int hashCode() {
        return 17 + id.hashCode() + changeType.hashCode();
    }
}
