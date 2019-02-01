package org.wickedsource.coderadar.project.domain;

import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.Projects.SINGLE_PROJECT;

import com.github.springtestdbunit.annotation.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.wickedsource.coderadar.testframework.template.IntegrationTestTemplate;

@Transactional
public class ProjectDeleterTest extends IntegrationTestTemplate {

  @Autowired private ProjectDeleter projectDeleter;
  @Autowired private ProjectRepository projectRepository;

  @Test
  @DatabaseSetup(SINGLE_PROJECT)
  @ExpectedDatabase(SINGLE_PROJECT)
  public void deleteProject() {
    projectDeleter.deleteProject(1L);
  }
}
