package org.wickedsource.coderadar.commit.domain;

import org.wickedsource.coderadar.analyzer.api.ChangeType;
import org.wickedsource.coderadar.file.domain.File;
import org.wickedsource.coderadar.module.domain.Module;

import javax.persistence.*;
import java.util.List;

/**
 * Associates a Commit to a File. Each Commit is associated to all Files that have been
 * modified in the Commit and to all files that have been left untouched by it, so that
 * one can easily access the full set of files at the time of the Commit.
 */
@Entity
@Table(name = "commit_file")
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "commit_file_module",
            joinColumns = {
                    @JoinColumn(name = "commit_id", nullable = false, updatable = false, referencedColumnName = "commit_id"),
                    @JoinColumn(name = "file_id", nullable = false, updatable = false, referencedColumnName = "file_id")
            },
            inverseJoinColumns = @JoinColumn(name = "module_id", nullable = false, updatable = false))
    private List<Module> modules;

    public CommitToFileAssociation() {

    }

    public CommitToFileAssociation(Commit commit, File sourcefile, ChangeType changeType) {
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

    public List<Module> getModules() {
        return modules;
    }

    public void setModule(List<Module> modules) {
        this.modules = modules;
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
