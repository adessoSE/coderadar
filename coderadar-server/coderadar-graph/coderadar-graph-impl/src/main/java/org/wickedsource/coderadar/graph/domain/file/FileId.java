package org.wickedsource.coderadar.graph.domain.file;

import java.util.UUID;
import org.wickedsource.coderadar.graph.domain.Immutable;

public class FileId extends Immutable<String> {

  private FileId(String uuid) {
    super(uuid);
  }

  public static FileId newId() {
    return new FileId(UUID.randomUUID().toString());
  }

  public static FileId from(String uuid) {
    return new FileId(uuid);
  }
}
