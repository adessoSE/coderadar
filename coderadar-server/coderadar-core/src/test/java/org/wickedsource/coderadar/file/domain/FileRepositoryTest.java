package org.wickedsource.coderadar.file.domain;

import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.EMPTY;
import static org.wickedsource.coderadar.factories.entities.EntityFactory.*;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.wickedsource.coderadar.analyzer.api.ChangeType;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.commit.domain.CommitToFileAssociation;
import org.wickedsource.coderadar.commit.domain.CommitToFileAssociationRepository;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;
import org.wickedsource.coderadar.testframework.template.IntegrationTestTemplate;

@Transactional
public class FileRepositoryTest extends IntegrationTestTemplate {

  @Autowired private FileRepository fileRepository;

  @Autowired private ProjectRepository projectRepository;

  @Autowired private CommitRepository commitRepository;

  @Autowired private CommitToFileAssociationRepository commitToFileAssociationRepository;

  @Test
  @DatabaseSetup(EMPTY)
  public void testFindInCommit() {
    Project project = project().validProject();
    project = projectRepository.save(project);
    Commit commit = commit().unprocessedCommit();
    commit.setProject(project);
    commit.setName("cafebabe");
    commit.setSequenceNumber(1);
    commit = commitRepository.save(commit);
    File file = sourceFile().withPath("123");
    file = fileRepository.save(file);
    CommitToFileAssociation association =
        new CommitToFileAssociation(commit, file, ChangeType.MODIFY);
    commit.getFiles().add(association);
    commitToFileAssociationRepository.save(association);

    File foundFile =
        fileRepository.findInCommit(file.getFilepath(), commit.getName(), project.getId());
    Assert.assertEquals(file.getId(), foundFile.getId());

    Assert.assertEquals(
        null, fileRepository.findInCommit("321", commit.getName(), project.getId()));
  }
}
