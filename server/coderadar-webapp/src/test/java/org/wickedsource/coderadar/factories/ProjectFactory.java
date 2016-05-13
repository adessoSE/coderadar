package org.wickedsource.coderadar.factories;

import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.VcsCoordinates;
import org.wickedsource.coderadar.project.domain.VcsType;

import java.net.MalformedURLException;
import java.net.URL;

public class ProjectFactory {

    public Project validProject() {
        try {
            Project project = new Project();
            project.setId(1L);
            project.setName("Testproject");
            VcsCoordinates vcs = new VcsCoordinates(new URL("http://my.url"), VcsType.GIT);
            vcs.setUsername("user");
            vcs.setPassword("pass");
            project.setVcsCoordinates(vcs);
            return project;
        } catch (MalformedURLException e) {
            throw new RuntimeException("error!", e);
        }
    }

}
