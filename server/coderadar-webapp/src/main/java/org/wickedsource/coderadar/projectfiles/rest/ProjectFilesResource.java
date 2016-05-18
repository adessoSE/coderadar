package org.wickedsource.coderadar.projectfiles.rest;

import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

public class ProjectFilesResource extends ResourceSupport {

    private List<ProjectFilesDTO> projectFilesList = new ArrayList<>();

    List<ProjectFilesDTO> getProjectFilesList() {
        return projectFilesList;
    }

    public void setProjectFilesList(List<ProjectFilesDTO> projectFilesList) {
        this.projectFilesList = projectFilesList;
    }

    void addProjectFiles(ProjectFilesDTO projectFiles){
        this.projectFilesList.add(projectFiles);
    }
}
