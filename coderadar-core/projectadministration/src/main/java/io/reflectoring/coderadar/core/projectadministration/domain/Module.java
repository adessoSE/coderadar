package io.reflectoring.coderadar.core.projectadministration.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * The codebase may be organized into modules, each module starting at a certain path. All files
 * within that path are considered to be part of the module.
 */
@Entity
@Table(
  name = "module",
  uniqueConstraints = {@UniqueConstraint(columnNames = {"project_id", "path"})}
)
@SequenceGenerator(name = "module_sequence", sequenceName = "seq_modu_id", allocationSize = 1)
@Data
public class Module {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "module_sequence")
  @Column(name = "id")
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "project_id")
  private Project project;

  @Column(name = "path", nullable = false)
  private String path;
}
