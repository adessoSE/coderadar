package org.wickedsource.coderadar.factories.resources;

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

    public ProjectResource validProjectResource2(){
        ProjectResource project = new ProjectResource();
        project.setVcsUser("user2");
        project.setVcsPassword("pass2");
        project.setVcsUrl("http://valid.url2");
        project.setName("name2");
        project.setVcsType(VcsType.GIT);
        return project;
    }
}
