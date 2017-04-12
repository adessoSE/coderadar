package org.wickedsource.coderadar.graph.domain.file;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Set;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.wickedsource.coderadar.graph.Neo4jIntegrationTestTemplate;
import org.wickedsource.coderadar.graph.domain.commit.CommitName;
import org.wickedsource.coderadar.graph.domain.commit.CommitNode;
import org.wickedsource.coderadar.graph.domain.commit.CommitNodeRepository;

public class FileNodeRepositoryTest extends Neo4jIntegrationTestTemplate {

  @Autowired private FileNodeRepository fileNodeRepository;

  @Autowired private CommitNodeRepository commitNodeRepository;

  @Test
  public void findFilesFromPreviousCommits() {

    FileNode file1 = new FileNode(FileId.from("file1"));
    FileNode file2 = new FileNode(FileId.from("file2"));
    FileNode file3 = new FileNode(FileId.from("file3"));
    FileNode file4 = new FileNode(FileId.from("file4"));

    CommitNode commit1 = new CommitNode(CommitName.from("commit1"), LocalDateTime.now());
    CommitNode commit2 = new CommitNode(commit1, CommitName.from("commit2"), LocalDateTime.now());
    CommitNode commit3 = new CommitNode(commit2, CommitName.from("commit3"), LocalDateTime.now());
    CommitNode commit4 = new CommitNode(commit3, CommitName.from("commit4"), LocalDateTime.now());
    CommitNode commit5 = new CommitNode(commit4, CommitName.from("commit5"), LocalDateTime.now());

    commit1.touched(file1);
    file1.modifiedIn(commit1);

    commit2.touched(file1);
    file1.modifiedIn(commit2);

    commit2.touched(file2);
    file2.addedIn(commit2);

    commit3.touched(file1);
    file1.deletedIn(commit3);

    commit3.touched(file3);
    file3.renamedIn(commit3);

    commit4.touched(file4);
    file4.addedIn(commit4);

    commit4.touched(file2);
    file2.deletedIn(commit4);

    commitNodeRepository.save(commit5);

    Set<FileNode> fileNodes = fileNodeRepository.findFilesFromPreviousCommits("commit5");

    assertThat(fileNodes).isNotNull();
    assertThat(fileNodes).hasSize(2);
    assertThat(fileNodes).contains(file3);
    assertThat(fileNodes).contains(file4);
  }
}
