package org.wickedsource.coderadar.graph.domain.filesnapshot;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.wickedsource.coderadar.graph.Neo4jIntegrationTestTemplate;
import org.wickedsource.coderadar.graph.domain.commit.CommitName;
import org.wickedsource.coderadar.graph.domain.commit.CommitNode;
import org.wickedsource.coderadar.graph.domain.commit.CommitNodeRepository;
import org.wickedsource.coderadar.graph.domain.file.FileId;
import org.wickedsource.coderadar.graph.domain.file.FileNode;

public class FileSnapshotNodeRepositoryTest extends Neo4jIntegrationTestTemplate {

  @Autowired private FileSnapshotNodeRepository fileSnapshotNodeRepository;

  @Autowired private CommitNodeRepository commitNodeRepository;

  @Test
  public void notDeletedInPreviousCommits() {
    Set<FileSnapshotNode> snapshots =
        fileSnapshotNodeRepository.notDeletedInPreviousCommits("commit5");

    assertThat(snapshots).hasSize(2);
    assertThat(snapshots).contains(new FileSnapshotNode("file3WithNewName"));
    assertThat(snapshots).contains(new FileSnapshotNode("file4"));

    // connected nodes must not be null
    for (FileSnapshotNode node : snapshots) {
      assertThat(node.getSnapshotOf()).isNotNull();
    }
  }

  @Autowired private Session session;

  @Test
  public void test() {
    Collection<FileSnapshotNode> nodes = session.loadAll(FileSnapshotNode.class);
  }

  @Before
  public void createTestData() {

    FileNode file1 = new FileNode(FileId.from("file1"));
    FileNode file2 = new FileNode(FileId.from("file2"));
    FileNode file3 = new FileNode(FileId.from("file3"));
    FileNode file4 = new FileNode(FileId.from("file4"));

    CommitNode commit1 = new CommitNode(CommitName.from("commit1"), LocalDateTime.now());
    CommitNode commit2 = new CommitNode(commit1, CommitName.from("commit2"), LocalDateTime.now());
    CommitNode commit3 = new CommitNode(commit2, CommitName.from("commit3"), LocalDateTime.now());
    CommitNode commit4 = new CommitNode(commit3, CommitName.from("commit4"), LocalDateTime.now());
    CommitNode commit5 = new CommitNode(commit4, CommitName.from("commit5"), LocalDateTime.now());

    FileSnapshotNode commit1snapshot1 = new FileSnapshotNode("file1");
    commit1.touched(file1);
    commit1.touched(commit1snapshot1);
    file1.modifiedIn(commit1);
    commit1snapshot1.snapshotOfFile(file1);
    commit1snapshot1.snapshotInCommit(commit1);

    FileSnapshotNode commit2Snapshot1 = new FileSnapshotNode("file1");
    commit2.touched(file1);
    commit2.touched(commit2Snapshot1);
    file1.modifiedIn(commit2);
    commit2Snapshot1.snapshotOfFile(file1);
    commit2Snapshot1.snapshotInCommit(commit2);

    FileSnapshotNode commit2Snapshot2 = new FileSnapshotNode("file2");
    commit2.touched(file2);
    commit2.touched(commit2Snapshot2);
    file2.addedIn(commit2);
    commit2Snapshot2.snapshotOfFile(file2);
    commit2Snapshot2.snapshotInCommit(commit2);

    FileSnapshotNode commit2Snapshot3 = new FileSnapshotNode("file3");
    commit2.touched(file3);
    commit2.touched(commit2Snapshot3);
    file3.addedIn(commit2);
    commit2Snapshot3.snapshotOfFile(file2);
    commit2Snapshot3.snapshotInCommit(commit2);

    FileSnapshotNode commit3Snapshot1 = new FileSnapshotNode("file1");
    commit3.touched(file1);
    commit3.touched(commit3Snapshot1);
    file1.deletedIn(commit3);
    commit3Snapshot1.snapshotOfFile(file1);
    commit3Snapshot1.snapshotInCommit(commit3);

    FileSnapshotNode commit3Snapshot3 = new FileSnapshotNode("file3WithNewName");
    commit3.touched(file3);
    commit3.touched(commit3Snapshot3);
    file3.renamedIn(commit3);
    commit3Snapshot3.snapshotOfFile(file3);
    commit3Snapshot3.snapshotInCommit(commit3);

    FileSnapshotNode commit4Snapshot4 = new FileSnapshotNode("file4");
    commit4.touched(file4);
    commit4.touched(commit4Snapshot4);
    file4.addedIn(commit4);
    commit4Snapshot4.snapshotOfFile(file4);
    commit4Snapshot4.snapshotInCommit(commit4);

    FileSnapshotNode commit4Snapshot2 = new FileSnapshotNode("file2");
    commit4.touched(file2);
    commit4.touched(commit4Snapshot2);
    file2.deletedIn(commit4);
    commit4Snapshot2.snapshotOfFile(file2);
    commit4Snapshot2.snapshotInCommit(commit4);

    commitNodeRepository.save(commit5);
  }
}
