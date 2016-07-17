package org.wickedsource.coderadar.project;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;
import org.wickedsource.coderadar.testframework.template.IntegrationTestTemplate;

import java.net.MalformedURLException;

import static org.wickedsource.coderadar.factories.entities.EntityFactory.project;

public class ProjectRepositoryTest extends IntegrationTestTemplate {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void saveEntity() throws MalformedURLException {
        Project project = projectRepository.save(project().validProject());
        project = projectRepository.findOne(project.getId());
        Assert.assertNotNull(project);
    }
}
