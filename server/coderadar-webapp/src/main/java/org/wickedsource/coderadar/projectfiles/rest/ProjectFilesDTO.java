package org.wickedsource.coderadar.projectfiles.rest;

import org.wickedsource.coderadar.project.domain.FileType;
import org.wickedsource.coderadar.project.domain.InclusionType;

import javax.validation.constraints.NotNull;

public class ProjectFilesDTO {

    @NotNull
    private String pattern;

    @NotNull
    private InclusionType inclusionType;

    @NotNull
    private FileType fileType;

    String getPattern() {
        return pattern;
    }

    void setPattern(String pattern) {
        this.pattern = pattern;
    }

    InclusionType getInclusionType() {
        return inclusionType;
    }

    void setInclusionType(InclusionType inclusionType) {
        this.inclusionType = inclusionType;
    }

    FileType getFileType() {
        return fileType;
    }

    void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

}
