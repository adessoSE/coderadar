package org.wickedsource.coderadar.factories.resources;

import java.util.ArrayList;
import java.util.List;
import org.wickedsource.coderadar.projectadministration.domain.FileSetType;
import org.wickedsource.coderadar.filepattern.rest.FilePatternDTO;
import org.wickedsource.coderadar.filepattern.rest.FilePatternResource;
import org.wickedsource.coderadar.projectadministration.domain.InclusionType;

public class FilePatternResourceFactory {

  public FilePatternResource filePatterns() {
    FilePatternResource filePatterns = new FilePatternResource();
    List<FilePatternDTO> patterns = new ArrayList<>();

    FilePatternDTO pattern = new FilePatternDTO();
    pattern.setFileSetType(FileSetType.SOURCE);
    pattern.setInclusionType(InclusionType.INCLUDE);
    pattern.setPattern("src/main/java/**/*.java");
    patterns.add(pattern);

    FilePatternDTO pattern2 = new FilePatternDTO();
    pattern2.setFileSetType(FileSetType.SOURCE);
    pattern2.setInclusionType(InclusionType.EXCLUDE);
    pattern2.setPattern("src/main/java/**/generated/**/*.java");
    patterns.add(pattern2);

    filePatterns.setFilePatterns(patterns);
    return filePatterns;
  }
}
