package org.wickedsource.coderadar.filepattern.rest;

import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

public class FilePatternResource extends ResourceSupport {

    private List<FilePatternDTO> projectFilesList = new ArrayList<>();

    List<FilePatternDTO> getProjectFilesList() {
        return projectFilesList;
    }

    public void setProjectFilesList(List<FilePatternDTO> projectFilesList) {
        this.projectFilesList = projectFilesList;
    }

    void addProjectFiles(FilePatternDTO projectFiles){
        this.projectFilesList.add(projectFiles);
    }
}
