package org.wickedsource.coderadar.filepattern.rest;

import java.util.ArrayList;
import java.util.List;

public class FilePatternResource {

  private List<FilePatternDTO> filePatterns = new ArrayList<>();

  public List<FilePatternDTO> getFilePatterns() {
    return filePatterns;
  }

  public void setFilePatterns(List<FilePatternDTO> filePatterns) {
    this.filePatterns = filePatterns;
  }

  public void addFilePattern(FilePatternDTO filePatterns) {
    this.filePatterns.add(filePatterns);
  }
}
