package org.wickedsource.coderadar.project.domain;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.wickedsource.coderadar.testframework.template.IntegrationTestTemplate;

@Transactional
public class ProjectDeleterTest extends IntegrationTestTemplate {

  @Autowired private ProjectDeleter projectDeleter;

  @Test
  public void deleteProject() {
    // TODO: Add actual test data to delete. This test currently only tests that all queries can be
    // executed.
    projectDeleter.deleteProject(1L);
  }
}
