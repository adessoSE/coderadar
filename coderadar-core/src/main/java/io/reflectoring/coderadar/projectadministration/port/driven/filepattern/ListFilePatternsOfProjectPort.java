package io.reflectoring.coderadar.projectadministration.port.driven.filepattern;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import java.util.Collection;

public interface ListFilePatternsOfProjectPort {
  Collection<FilePattern> listFilePatterns(Long projectId) throws ProjectNotFoundException;
}
