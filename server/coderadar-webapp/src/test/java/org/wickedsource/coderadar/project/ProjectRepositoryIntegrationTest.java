package org.wickedsource.coderadar.project;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.wickedsource.coderadar.IntegrationTestTemplate;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;
import org.wickedsource.coderadar.project.domain.VcsCoordinates;
import org.wickedsource.coderadar.project.domain.VcsType;

import java.net.MalformedURLException;
import java.net.URL;

public class ProjectRepositoryIntegrationTest extends IntegrationTestTemplate {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void saveEntity() throws MalformedURLException {
        Project project = new Project();
        project.setName("Testproject");
        VcsCoordinates vcs = new VcsCoordinates(new URL("http://my.url"), VcsType.GIT);
        vcs.setUsername("user");
        vcs.setPassword("pass");
        project.setVcsCoordinates(vcs);
        project = projectRepository.save(project);
        project = projectRepository.findOne(project.getId());
        Assert.assertNotNull(project);
    }
}
