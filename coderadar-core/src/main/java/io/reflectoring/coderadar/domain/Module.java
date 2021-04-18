package io.reflectoring.coderadar.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The codebase may be organized into modules, each module starting at a certain path. All files
 * within that path are considered to be part of the module.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Module {
  private long id;
  private String path;
}
