package org.wickedsource.coderadar.commit.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.wickedsource.coderadar.project.domain.Project;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Metadata about a commit to a VCS.
 */
@Entity
@Table
public class Commit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private Project project;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @Column
    private String parentCommitName;

    @Column
    private String comment;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private boolean scanned = false;

    @Column(nullable = false)
    private boolean merged = false;

    @Column(nullable = false)
    private boolean analyzed = false;

    @Column(nullable = false)
    private Integer sequenceNumber;

    @OneToMany(mappedBy = "id.commit")
    private Set<CommitToFileAssociation> files = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isScanned() {
        return scanned;
    }

    public void setScanned(boolean scanned) {
        this.scanned = scanned;
    }

    public boolean isMerged() {
        return merged;
    }

    public void setMerged(boolean merged) {
        this.merged = merged;
    }

    public boolean isAnalyzed() {
        return analyzed;
    }

    public void setAnalyzed(boolean analyzed) {
        this.analyzed = analyzed;
    }

    @Override
    public String toString() {
        ReflectionToStringBuilder builder = new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        builder.setExcludeFieldNames("project", "files");
        return builder.build();
    }

    public Set<CommitToFileAssociation> getFiles() {
        return files;
    }

    public void setFiles(Set<CommitToFileAssociation> files) {
        this.files = files;
    }

    public String getParentCommitName() {
        return parentCommitName;
    }

    public void setParentCommitName(String parentCommitName) {
        this.parentCommitName = parentCommitName;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
}
