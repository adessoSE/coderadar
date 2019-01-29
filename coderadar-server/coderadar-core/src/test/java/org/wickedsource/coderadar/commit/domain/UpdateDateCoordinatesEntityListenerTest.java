package org.wickedsource.coderadar.commit.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.wickedsource.coderadar.factories.entities.EntityFactory;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;
import org.wickedsource.coderadar.testframework.template.IntegrationTestTemplate;

import java.util.Locale;

public class UpdateDateCoordinatesEntityListenerTest extends IntegrationTestTemplate {

  @Autowired private CommitRepository commitRepository;

  @Autowired private ProjectRepository projectRepository;

  @Test
  public void updatesDateCoordinates() {
    Locale.setDefault(Locale.US);
    Project project = EntityFactory.project().validProject();
    projectRepository.save(project);
    Commit commit = EntityFactory.commit().validCommit();
    commit.setProject(project);
    Commit savedCommit = commitRepository.save(commit);

    Commit loadedCommit = commitRepository.findOne(savedCommit.getId());
    assertThat(loadedCommit.getDateCoordinates()).isNotNull();
    assertThat(loadedCommit.getDateCoordinates().getDayOfMonth()).isNotNull();
    assertThat(loadedCommit.getDateCoordinates().getYear()).isNotNull();
    assertThat(loadedCommit.getDateCoordinates().getYearOfWeek()).isNotNull();
    assertThat(loadedCommit.getDateCoordinates().getWeekOfYear()).isNotNull();
    assertThat(loadedCommit.getDateCoordinates().getMonth()).isNotNull();
  }
}
