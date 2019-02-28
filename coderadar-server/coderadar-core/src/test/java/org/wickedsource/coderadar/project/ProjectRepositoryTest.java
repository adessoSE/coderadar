package org.wickedsource.coderadar.project;

import static org.wickedsource.coderadar.factories.entities.EntityFactory.project;

import java.net.MalformedURLException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;
import org.wickedsource.coderadar.testframework.template.IntegrationTestTemplate;

public class ProjectRepositoryTest extends IntegrationTestTemplate {

  @Autowired private ProjectRepository projectRepository;

  @Test
  public void saveEntity() throws MalformedURLException {
    Project project = projectRepository.save(project().validProject());
    Optional<Project> projectResult = projectRepository.findById(project.getId());
    Assertions.assertTrue(projectResult.isPresent());
  }
}
