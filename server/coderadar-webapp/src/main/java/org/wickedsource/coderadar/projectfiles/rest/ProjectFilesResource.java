package org.wickedsource.coderadar.projectfiles.rest;

import org.springframework.hateoas.ResourceSupport;
import org.wickedsource.coderadar.project.domain.FileType;
import org.wickedsource.coderadar.project.domain.InclusionType;

import javax.validation.constraints.NotNull;

public class ProjectFilesResource extends ResourceSupport {

    @NotNull
    private String pattern;

    @NotNull
    private InclusionType inclusionType;

    @NotNull
    private FileType fileType;

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public InclusionType getInclusionType() {
        return inclusionType;
    }

    public void setInclusionType(InclusionType inclusionType) {
        this.inclusionType = inclusionType;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

}
