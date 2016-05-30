package org.wickedsource.coderadar.file.domain;

import org.wickedsource.coderadar.analyzer.api.ChangeType;

import javax.persistence.*;

/**
 * Stores metadata about a file that has been part of a commit to a VCS (i.e. metadata
 * of a log entry of that VCS). This metadata is temporary and will be deleted once
 * it has been processed and migrated into the final data structure (see {@link File}).
 */
@Entity
public class CommitLogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private ChangeType changeType;

    @Column
    private String filepath;

    @Column
    private String oldFilepath;

    @Column
    private String commitName;

    @Column
    private String parentCommitName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public void setChangeType(ChangeType changeType) {
        this.changeType = changeType;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getOldFilepath() {
        return oldFilepath;
    }

    public void setOldFilepath(String oldFilepath) {
        this.oldFilepath = oldFilepath;
    }

    public String getCommitName() {
        return commitName;
    }

    public void setCommitName(String commitName) {
        this.commitName = commitName;
    }

    public String getParentCommitName() {
        return parentCommitName;
    }

    public void setParentCommitName(String parentCommitName) {
        this.parentCommitName = parentCommitName;
    }
}
