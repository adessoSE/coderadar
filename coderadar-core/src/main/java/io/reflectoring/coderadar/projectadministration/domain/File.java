package io.reflectoring.coderadar.projectadministration.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/** Represents a file in a VCS repository. */
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class File {
  private long id;
  private String path;
  private long sequenceId;
  private String objectHash;

  @EqualsAndHashCode.Exclude private List<File> oldFiles = Collections.emptyList();
}
