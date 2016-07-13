package org.wickedsource.coderadar.commit.rest;

import org.springframework.hateoas.ResourceSupport;

import java.util.Date;

public class CommitResource extends ResourceSupport {

    private String name;

    private String parentCommitName;

    private String author;

    private Date timestamp;

    private boolean analyzed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentCommitName() {
        return parentCommitName;
    }

    public void setParentCommitName(String parentCommitName) {
        this.parentCommitName = parentCommitName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isAnalyzed() {
        return analyzed;
    }

    public void setAnalyzed(boolean analyzed) {
        this.analyzed = analyzed;
    }
}
