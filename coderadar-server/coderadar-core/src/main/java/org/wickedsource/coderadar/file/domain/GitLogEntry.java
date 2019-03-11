package org.wickedsource.coderadar.file.domain;

import javax.persistence.*;
import lombok.Data;
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
@Data
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
}
