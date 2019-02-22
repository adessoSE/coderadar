package org.wickedsource.coderadar.file.domain;

import javax.persistence.*;
import org.wickedsource.coderadar.analyzer.api.ChangeType;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.project.domain.Project;

/**
 * Stores metadata about a file that has been part of a commit to a VCS (i.e. metadata of a log
 * entry of that VCS). This metadata is temporary and will be deleted once it has been processed and
 * migrated into the final data structure (see {@link File}).
 */
@Entity
@Table(name = "git_log_entry")
@SequenceGenerator(
  name = "git_log_entry_sequence",
  sequenceName = "seq_glen_id",
  allocationSize = 1
)
public class GitLogEntry {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "git_log_entry_sequence")
  @Column(name = "id")
  private Long id;

  @Column(name = "change_type")
  @Enumerated(EnumType.STRING)
  private ChangeType changeType;

  @Column(name = "filepath")
  private String filepath;

  @Column(name = "old_filepath")
  private String oldFilepath;

  @ManyToOne
  @JoinColumn(name = "commit_id")
  private Commit commit;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id")
  private Project project;

  @Column(name = "file_hash")
  private String fileHash;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ChangeType getChangeType() {
    return changeType;
  }

  public void setChangeType(ChangeType changeType) {
    this.changeType = changeType;
  }

  public String getFilepath() {
    return filepath;
  }

  public void setFilepath(String filepath) {
    this.filepath = filepath;
  }

  public String getOldFilepath() {
    return oldFilepath;
  }

  public void setOldFilepath(String oldFilepath) {
    this.oldFilepath = oldFilepath;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public Commit getCommit() {
    return commit;
  }

  public void setCommit(Commit commit) {
    this.commit = commit;
  }

  public String getFileHash() {
    return fileHash;
  }

  public void setFileHash(String fileHash) {
    this.fileHash = fileHash;
  }
}
