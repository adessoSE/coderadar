package org.wickedsource.coderadar.filepattern.rest;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.ResourceSupport;

@EqualsAndHashCode(callSuper = true)
@Data
public class FilePatternResource extends ResourceSupport {

  private List<FilePatternDTO> filePatterns = new ArrayList<>();

  public void addFilePattern(FilePatternDTO filePatterns) {
    this.filePatterns.add(filePatterns);
  }
}
