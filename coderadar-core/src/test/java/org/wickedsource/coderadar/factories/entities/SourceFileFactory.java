package org.wickedsource.coderadar.factories.entities;

import org.wickedsource.coderadar.file.domain.File;
import org.wickedsource.coderadar.file.domain.FileIdentity;

public class SourceFileFactory {

  public File withPath(String filepath) {
    File file = new File();
    file.setIdentity(new FileIdentity());
    file.setFilepath(filepath);
    return file;
  }
}
