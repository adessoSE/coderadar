package org.wickedsource.coderadar.filepattern.rest;

import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

public class FilePatternResource extends ResourceSupport {

    private List<FilePatternDTO> filePatterns = new ArrayList<>();

    public List<FilePatternDTO> getFilePatterns() {
        return filePatterns;
    }

    public void setFilePatterns(List<FilePatternDTO> filePatterns) {
        this.filePatterns = filePatterns;
    }

    public void addFilePattern(FilePatternDTO filePatterns){
        this.filePatterns.add(filePatterns);
    }
}
