package org.wickedsource.coderadar.commit.domain;

import org.wickedsource.coderadar.project.domain.Project;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Commit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private Project project;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @Column
    private String comment;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private boolean scanned = false;

    @Column(nullable = false)
    private boolean identityMerged = false;

    @Column(nullable = false)
    private boolean analyzed = false;

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

    public boolean isIdentityMerged() {
        return identityMerged;
    }

    public void setIdentityMerged(boolean identityMerged) {
        this.identityMerged = identityMerged;
    }

    public boolean isAnalyzed() {
        return analyzed;
    }

    public void setAnalyzed(boolean analyzed) {
        this.analyzed = analyzed;
    }
}
