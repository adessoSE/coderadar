package org.wickedsource.coderadar.project.domain;

import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.Projects.SINGLE_PROJECT;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.wickedsource.coderadar.testframework.template.IntegrationTestTemplate;

@Transactional
public class ProjectDeleterTest extends IntegrationTestTemplate {

  @Autowired private ProjectDeleter projectDeleter;
  @Autowired private ProjectRepository projectRepository;

  @Test
  @DatabaseSetup(SINGLE_PROJECT)
  public void deleteProject() {
    // TODO: Add actual test data to delete. This test currently only tests that all queries can be
    // executed.
    projectDeleter.deleteProject(1L);
    Assertions.assertEquals(0, projectRepository.count());
  }
}
