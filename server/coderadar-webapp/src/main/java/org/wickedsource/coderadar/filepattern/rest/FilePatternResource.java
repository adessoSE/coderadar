package org.wickedsource.coderadar.filepattern.rest;

import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

public class FilePatternResource extends ResourceSupport {

    private List<FilePatternDTO> projectFiles = new ArrayList<>();

    List<FilePatternDTO> getProjectFiles() {
        return projectFiles;
    }

    public void setProjectFiles(List<FilePatternDTO> projectFiles) {
        this.projectFiles = projectFiles;
    }

    void addProjectFiles(FilePatternDTO projectFiles){
        this.projectFiles.add(projectFiles);
    }
}
