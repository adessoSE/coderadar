package io.reflectoring.coderadar.core.projectadministration.service.filepattern;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.ListFilePatternsOfProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.get.GetFilePatternResponse;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.get.ListFilePatternsOfProjectUseCase;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListFilePatternsOfProjectService implements ListFilePatternsOfProjectUseCase {

  private final ListFilePatternsOfProjectPort port;

  @Autowired
  public ListFilePatternsOfProjectService(ListFilePatternsOfProjectPort port) {
    this.port = port;
  }

  @Override
  public List<GetFilePatternResponse> listFilePatterns(Long projectId) {
    List<GetFilePatternResponse> patterns = new ArrayList<>();
    for (FilePattern filePattern : port.listFilePatterns(projectId)) {
      patterns.add(
          new GetFilePatternResponse(
              filePattern.getId(), filePattern.getPattern(), filePattern.getInclusionType()));
    }
    return patterns;
  }
}
