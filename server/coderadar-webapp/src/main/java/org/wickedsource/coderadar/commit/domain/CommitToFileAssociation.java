package org.wickedsource.coderadar.commit.domain;

import org.wickedsource.coderadar.analyzer.api.ChangeType;
import org.wickedsource.coderadar.file.domain.File;

import javax.persistence.*;

@Entity
@Table(name="commit_file")
@AssociationOverrides({
        @AssociationOverride(name = "id.commit", joinColumns = @JoinColumn(name = "commit_id")),
        @AssociationOverride(name = "id.file", joinColumns = @JoinColumn(name = "file_id"))}
)
public class CommitToFileAssociation {

    @EmbeddedId
    private CommitToFileId id;

    @Column
    @Enumerated(EnumType.STRING)
    private ChangeType changeType;

    public CommitToFileAssociation(){

    }

    public CommitToFileAssociation(Commit commit, File sourcefile, ChangeType changeType){
        this.id = new CommitToFileId(commit, sourcefile);
        this.changeType = changeType;
    }

    @Transient
    public Commit getCommit() {
        return id.getCommit();
    }

    @Transient
    public File getSourceFile() {
        return id.getFile();
    }

    public CommitToFileId getId() {
        return id;
    }

    public void setId(CommitToFileId id) {
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
        if (obj == null || !(obj instanceof CommitToFileAssociation)) {
            return false;
        }
        CommitToFileAssociation that = (CommitToFileAssociation) obj;
        return this.id.equals(that.id) && this.changeType == that.changeType;
    }

    @Override
    public int hashCode() {
        return 17 + id.hashCode() + changeType.hashCode();
    }
}
