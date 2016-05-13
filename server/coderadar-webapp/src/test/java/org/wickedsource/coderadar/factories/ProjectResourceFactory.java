package org.wickedsource.coderadar.factories;

import org.wickedsource.coderadar.project.domain.VcsType;
import org.wickedsource.coderadar.project.rest.ProjectResource;

public class ProjectResourceFactory {

    public ProjectResource validProjectResource(){
        ProjectResource project = new ProjectResource();
        project.setVcsUser("user");
        project.setVcsPassword("pass");
        project.setVcsUrl("http://valid.url");
        project.setName("name");
        project.setVcsType(VcsType.GIT);
        return project;
    }
}
