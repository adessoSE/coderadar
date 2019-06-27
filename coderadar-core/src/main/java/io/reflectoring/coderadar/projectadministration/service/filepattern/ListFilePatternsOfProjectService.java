package io.reflectoring.coderadar.projectadministration.service.filepattern;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.ListFilePatternsOfProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get.GetFilePatternResponse;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get.ListFilePatternsOfProjectUseCase;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListFilePatternsOfProjectService implements ListFilePatternsOfProjectUseCase {

  private final ListFilePatternsOfProjectPort port;
  private final GetProjectPort getProjectPort;

  @Autowired
  public ListFilePatternsOfProjectService(
      ListFilePatternsOfProjectPort port, GetProjectPort getProjectPort) {
    this.port = port;
    this.getProjectPort = getProjectPort;
  }

  @Override
  public List<GetFilePatternResponse> listFilePatterns(Long projectId)
      throws ProjectNotFoundException {
    List<GetFilePatternResponse> patterns = new ArrayList<>();
    for (FilePattern filePattern : port.listFilePatterns(projectId)) {
      patterns.add(
          new GetFilePatternResponse(
              filePattern.getId(), filePattern.getPattern(), filePattern.getInclusionType()));
    }
    return patterns;
  }
}
