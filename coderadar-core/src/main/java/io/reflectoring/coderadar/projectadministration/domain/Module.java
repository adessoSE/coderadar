package io.reflectoring.coderadar.projectadministration.domain;

import lombok.Data;

/**
 * The codebase may be organized into modules, each module starting at a certain path. All files
 * within that path are considered to be part of the module.
 */
@Data
public class Module {
  private Long id;
  private String path;
}
