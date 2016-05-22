package org.wickedsource.coderadar.metric.domain;

import org.wickedsource.coderadar.analyzer.api.ChangeType;
import org.wickedsource.coderadar.commit.domain.Commit;

import javax.persistence.*;

@Entity
@Table
public class SourceFile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String filepath;

    @Column
    private String filepathBeforeRename;

    @Column(nullable = false)
    private ChangeType changeType;

    @ManyToOne(optional = false)
    private Commit commit;

    @Column(nullable = false)
    private String commitName;

    @ManyToOne(cascade = CascadeType.ALL)
    private SourceFileIdentity identity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

    public String getCommitName() {
        return commitName;
    }

    public void setCommitName(String commitName) {
        this.commitName = commitName;
    }

    public SourceFileIdentity getIdentity() {
        return identity;
    }

    public void setIdentity(SourceFileIdentity identity) {
        this.identity = identity;
    }

    public String getFilepathBeforeRename() {
        return filepathBeforeRename;
    }

    public void setFilepathBeforeRename(String filepathBeforeRename) {
        this.filepathBeforeRename = filepathBeforeRename;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public void setChangeType(ChangeType changeType) {
        this.changeType = changeType;
    }
}
