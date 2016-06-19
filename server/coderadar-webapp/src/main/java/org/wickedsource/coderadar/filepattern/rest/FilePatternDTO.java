package org.wickedsource.coderadar.filepattern.rest;

import org.wickedsource.coderadar.filepattern.domain.FileType;
import org.wickedsource.coderadar.project.domain.InclusionType;

import javax.validation.constraints.NotNull;

public class FilePatternDTO {

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
