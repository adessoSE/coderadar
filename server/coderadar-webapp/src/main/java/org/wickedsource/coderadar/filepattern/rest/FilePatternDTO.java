package org.wickedsource.coderadar.filepattern.rest;

import org.wickedsource.coderadar.filepattern.domain.FileSet;
import org.wickedsource.coderadar.project.domain.InclusionType;

import javax.validation.constraints.NotNull;

public class FilePatternDTO {

    @NotNull
    private String pattern;

    @NotNull
    private InclusionType inclusionType;

    @NotNull
    private FileSet fileSet;

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

    public FileSet getFileSet() {
        return fileSet;
    }

    public void setFileSet(FileSet fileSet) {
        this.fileSet = fileSet;
    }

}
