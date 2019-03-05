package org.wickedsource.coderadar.filepattern.rest;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class FilePatternResource {

  private List<FilePatternDTO> filePatterns = new ArrayList<>();

  public void addFilePattern(FilePatternDTO filePatterns) {
    this.filePatterns.add(filePatterns);
  }
}
