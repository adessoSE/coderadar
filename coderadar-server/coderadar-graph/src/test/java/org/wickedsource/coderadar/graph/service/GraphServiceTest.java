package org.wickedsource.coderadar.graph.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.wickedsource.coderadar.graph.service.CommitNodeAssert.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.wickedsource.coderadar.graph.Neo4jIntegrationTestTemplate;
import org.wickedsource.coderadar.graph.domain.commit.CommitName;
import org.wickedsource.coderadar.graph.domain.commit.CommitNode;
import org.wickedsource.coderadar.graph.domain.commit.CommitNodeRepository;
import org.wickedsource.coderadar.graph.domain.file.FileNodeRepository;
import org.wickedsource.coderadar.graph.domain.filesnapshot.FileSnapshotNodeRepository;

public class GraphServiceTest extends Neo4jIntegrationTestTemplate {

  @Autowired private CommitNodeRepository commitNodeRepository;

  @Autowired private FileNodeRepository fileNodeRepository;

  @Autowired private FileSnapshotNodeRepository fileSnapshotNodeRepository;

  @Autowired private GraphService graphService;

  private CommitNode commit1;

  private CommitNode commit2;

  private CommitNode commit3;

  private CommitNode commit4;

  private void runFirstBatch() {
    List<GitChange> changes = new ArrayList<>();
    changes.add(
        new GitChange(
            "commit1",
            null,
            "file1",
            null,
            GitChange.ChangeType.ADDED,
            LocalDateTime.now(),
            LocalDateTime.now()));
    changes.add(
        new GitChange(
            "commit2",
            "commit1",
            "file1",
            null,
            GitChange.ChangeType.MODIFIED,
            LocalDateTime.now(),
            LocalDateTime.now()));
    changes.add(
        new GitChange(
            "commit2",
            "commit1",
            "file2",
            null,
            GitChange.ChangeType.ADDED,
            LocalDateTime.now(),
            LocalDateTime.now()));
    changes.add(
        new GitChange(
            "commit3",
            "commit2",
            "file1WithNewName",
            "file1",
            GitChange.ChangeType.RENAMED,
            LocalDateTime.now(),
            LocalDateTime.now()));
    graphService.addGitChanges(changes);

    commit1 = commitNodeRepository.findByName(CommitName.from("commit1"));
    commit2 = commitNodeRepository.findByName(CommitName.from("commit2"));
    commit3 = commitNodeRepository.findByName(CommitName.from("commit3"));
    commit4 = commitNodeRepository.findByName(CommitName.from("commit4"));
  }

  private void runSecondBatch() {
    List<GitChange> changes = new ArrayList<>();
    changes.add(
        new GitChange(
            "commit3",
            "commit2",
            "file2",
            null,
            GitChange.ChangeType.DELETED,
            LocalDateTime.now(),
            LocalDateTime.now()));
    changes.add(
        new GitChange(
            "commit4",
            "commit3",
            "file1WithNewName",
            null,
            GitChange.ChangeType.MODIFIED,
            LocalDateTime.now(),
            LocalDateTime.now()));
    changes.add(
        new GitChange(
            "commit4",
            "commit3",
            "file3",
            null,
            GitChange.ChangeType.ADDED,
            LocalDateTime.now(),
            LocalDateTime.now()));
    graphService.addGitChanges(changes);

    commit1 = commitNodeRepository.findByName(CommitName.from("commit1"));
    commit2 = commitNodeRepository.findByName(CommitName.from("commit2"));
    commit3 = commitNodeRepository.findByName(CommitName.from("commit3"));
    commit4 = commitNodeRepository.findByName(CommitName.from("commit4"));
  }

  @Test
  public void fileSnapshotNodesAreAddedToGraph() {
    runFirstBatch();
    assertThat(commit1).hasTouchedFileSnapshots(1);
    assertThat(commit2).hasTouchedFileSnapshots(2);
    assertThat(commit3).hasTouchedFileSnapshots(1);
    assertThat(fileSnapshotNodeRepository.findFileSnapshotsInCommit("commit1")).hasSize(1);
    assertThat(fileSnapshotNodeRepository.findFileSnapshotsInCommit("commit2")).hasSize(2);
    assertThat(fileSnapshotNodeRepository.findFileSnapshotsInCommit("commit3")).hasSize(1);
    assertThat(fileSnapshotNodeRepository.findFileSnapshotsInCommit("commit4")).hasSize(0);

    runSecondBatch();
    assertThat(commit1).hasTouchedFileSnapshots(1);
    assertThat(commit2).hasTouchedFileSnapshots(2);
    assertThat(commit3).hasTouchedFileSnapshots(2);
    assertThat(commit4).hasTouchedFileSnapshots(2);
    assertThat(fileSnapshotNodeRepository.findFileSnapshotsInCommit("commit1")).hasSize(1);
    assertThat(fileSnapshotNodeRepository.findFileSnapshotsInCommit("commit2")).hasSize(2);
    assertThat(fileSnapshotNodeRepository.findFileSnapshotsInCommit("commit3")).hasSize(2);
    assertThat(fileSnapshotNodeRepository.findFileSnapshotsInCommit("commit4")).hasSize(2);
  }

  //	@Test
  //	public void fileNodesAreAddedToGraph(){
  //		runFirstBatch();
  //
  //		assertThat(fileNodeRepository.count()).isEqualTo(3);
  //		assertThat(commit1).hasTouchedFiles(1);
  //		assertThat(commit2).hasTouchedFiles(2);
  //		assertThat(commit3).hasTouchedFiles(1);
  //
  //		FileNodeRepository.TouchedFilesCounts touchedInCommit1 = fileNodeRepository
  //				.countTouchedFiles("commit1");
  //		assertThat(touchedInCommit1.getAddedFiles()).isEqualTo(1);
  //		assertThat(touchedInCommit1.getModifiedFiles()).isEqualTo(0);
  //		assertThat(touchedInCommit1.getDeletedFiles()).isEqualTo(0);
  //		assertThat(touchedInCommit1.getRenamedFiles()).isEqualTo(0);
  //
  //		FileNodeRepository.TouchedFilesCounts touchedInCommit2 = fileNodeRepository
  //				.countTouchedFiles("commit2");
  //		assertThat(touchedInCommit2.getAddedFiles()).isEqualTo(1);
  //		assertThat(touchedInCommit2.getModifiedFiles()).isEqualTo(1);
  //		assertThat(touchedInCommit2.getDeletedFiles()).isEqualTo(0);
  //		assertThat(touchedInCommit2.getRenamedFiles()).isEqualTo(0);
  //
  //		FileNodeRepository.TouchedFilesCounts touchedInCommit3 = fileNodeRepository
  //				.countTouchedFiles("commit3");
  //		assertThat(touchedInCommit3.getAddedFiles()).isEqualTo(0);
  //		assertThat(touchedInCommit3.getModifiedFiles()).isEqualTo(0);
  //		assertThat(touchedInCommit3.getDeletedFiles()).isEqualTo(0);
  //		assertThat(touchedInCommit3.getRenamedFiles()).isEqualTo(1);
  //
  //		runSecondBatch();
  //
  //		assertThat(fileNodeRepository.count()).isEqualTo(4);
  //		assertThat(commit3).hasTouchedFiles(2);
  //		assertThat(commit4).hasTouchedFiles(2);
  //
  //		touchedInCommit3 = fileNodeRepository
  //				.countTouchedFiles("commit3");
  //		assertThat(touchedInCommit3.getAddedFiles()).isEqualTo(0);
  //		assertThat(touchedInCommit3.getModifiedFiles()).isEqualTo(0);
  //		assertThat(touchedInCommit3.getDeletedFiles()).isEqualTo(1);
  //		assertThat(touchedInCommit3.getRenamedFiles()).isEqualTo(1);
  //
  //		FileNodeRepository.TouchedFilesCounts touchedInCommit4 = fileNodeRepository
  //				.countTouchedFiles("commit4");
  //		assertThat(touchedInCommit4.getAddedFiles()).isEqualTo(1);
  //		assertThat(touchedInCommit4.getModifiedFiles()).isEqualTo(1);
  //		assertThat(touchedInCommit4.getDeletedFiles()).isEqualTo(0);
  //		assertThat(touchedInCommit4.getRenamedFiles()).isEqualTo(0);
  //	}

  @Test
  public void commitNodesAreAddedToGraph() {
    runFirstBatch();
    assertThat(commitNodeRepository.count()).isEqualTo(3);
    assertThat(commit1).hasNoParent();
    assertThat(commit2).hasParent(commit1);
    assertThat(commit3).hasParent(commit2);

    runSecondBatch();
    assertThat(commitNodeRepository.count()).isEqualTo(4);
    assertThat(commit3).hasParent(commit2);
    assertThat(commit4).hasParent(commit3);
  }
}
