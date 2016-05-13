package org.wickedsource.coderadar.project;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.wickedsource.coderadar.factories.Factories;
import org.wickedsource.coderadar.IntegrationTestTemplate;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;

import java.net.MalformedURLException;

public class ProjectRepositoryTest extends IntegrationTestTemplate {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void saveEntity() throws MalformedURLException {
        Project project = projectRepository.save(Factories.project().validProject());
        project = projectRepository.findOne(project.getId());
        Assert.assertNotNull(project);
    }
}
