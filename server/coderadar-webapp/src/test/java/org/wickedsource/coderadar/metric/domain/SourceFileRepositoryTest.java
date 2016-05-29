package org.wickedsource.coderadar.metric.domain;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.wickedsource.coderadar.IntegrationTestTemplate;
import org.wickedsource.coderadar.analyzer.api.ChangeType;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.commit.domain.CommitToSourceFileAssociation;
import org.wickedsource.coderadar.factories.Factories;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;

@Transactional
public class SourceFileRepositoryTest extends IntegrationTestTemplate {

    @Autowired
    private SourceFileRepository sourceFileRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CommitRepository commitRepository;

    @Test
    public void testFindInCommit() {
        Project project = Factories.project().validProject();
        project = projectRepository.save(project);
        Commit commit = Factories.commit().unprocessedCommit();
        commit.setProject(project);
        commit = commitRepository.save(commit);
        SourceFile file = Factories.sourceFile().withPath("123");
        file = sourceFileRepository.save(file);
        commit.getSourceFiles().add(new CommitToSourceFileAssociation(commit, file, ChangeType.MODIFY));

        SourceFile foundFile = sourceFileRepository.findInCommit(file.getFilepath(), commit.getName());
        Assert.assertEquals(file.getId(), foundFile.getId());

        SourceFile unknownFile = sourceFileRepository.findInCommit("321",commit.getName());
        Assert.assertNull(unknownFile);
    }

}