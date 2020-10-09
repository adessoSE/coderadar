package io.reflectoring.coderadar.projectadministration.domain;

import java.util.Collections;
import java.util.List;
import lombok.*;

/** Represents a file in a VCS repository. */
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class File {
  private long id;
  private String path;

  @EqualsAndHashCode.Exclude @ToString.Exclude
  private List<File> oldFiles = Collections.emptyList();

  public File(String path) {
    this.path = path;
  }

  public File(long id, String path) {
    this.id = id;
    this.path = path;
  }
}
