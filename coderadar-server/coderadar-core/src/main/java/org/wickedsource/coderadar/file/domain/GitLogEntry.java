package org.wickedsource.coderadar.file.domain;

import javax.persistence.*;
import org.wickedsource.coderadar.analyzer.api.ChangeType;
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

  @Column(name = "commit_name")
  private String commitName;

  @Column(name = "parent_commit_name")
  private String parentCommitName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id")
  private Project project;

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

  public String getCommitName() {
    return commitName;
  }

  public void setCommitName(String commitName) {
    this.commitName = commitName;
  }

  public String getParentCommitName() {
    return parentCommitName;
  }

  public void setParentCommitName(String parentCommitName) {
    this.parentCommitName = parentCommitName;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }
}
