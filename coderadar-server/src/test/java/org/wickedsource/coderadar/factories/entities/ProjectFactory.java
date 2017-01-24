package org.wickedsource.coderadar.factories.entities;

import java.net.MalformedURLException;
import java.net.URL;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.VcsCoordinates;
import org.wickedsource.coderadar.project.domain.VcsType;

public class ProjectFactory {

  public Project validProject() {
    try {
      Project project = new Project();
      project.setId(1L);
      project.setName("Testproject");
      VcsCoordinates vcs =
          new VcsCoordinates(new URL("https://github.com/thombergs/diffparser.git"), VcsType.GIT);
      vcs.setUsername("user");
      vcs.setPassword("pass");
      project.setVcsCoordinates(vcs);
      return project;
    } catch (MalformedURLException e) {
      throw new RuntimeException("error!", e);
    }
  }

  public Project validProject2() {
    try {
      Project project = new Project();
      project.setId(2L);
      project.setName("Another project");
      VcsCoordinates vcs = new VcsCoordinates(new URL("http://your.url"), VcsType.SVN);
      project.setVcsCoordinates(vcs);
      return project;
    } catch (MalformedURLException e) {
      throw new RuntimeException("error!", e);
    }
  }
}
