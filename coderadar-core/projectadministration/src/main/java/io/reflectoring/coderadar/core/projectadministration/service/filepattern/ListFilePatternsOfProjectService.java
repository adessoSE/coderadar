package io.reflectoring.coderadar.core.projectadministration.service.filepattern;

import io.reflectoring.coderadar.core.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.ListFilePatternsOfProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.get.GetFilePatternResponse;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.get.ListFilePatternsOfProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("ListFilePatternsOfProjectService")
public class ListFilePatternsOfProjectService implements ListFilePatternsOfProjectUseCase {

  private final ListFilePatternsOfProjectPort port;
  private final GetProjectPort getProjectPort;

  @Autowired
  public ListFilePatternsOfProjectService(
          @Qualifier("ListFilePatternsOfProjectServiceNeo4j") ListFilePatternsOfProjectPort port,
          @Qualifier("GetProjectServiceNeo4j") GetProjectPort getProjectPort) {
    this.port = port;
    this.getProjectPort = getProjectPort;
  }

  @Override
  public List<GetFilePatternResponse> listFilePatterns(Long projectId) throws ProjectNotFoundException {
    if(getProjectPort.get(projectId).isPresent()){
      List<GetFilePatternResponse> patterns = new ArrayList<>();
      for (FilePattern filePattern : port.listFilePatterns(projectId)) {
        patterns.add(
                new GetFilePatternResponse(
                        filePattern.getId(), filePattern.getPattern(), filePattern.getInclusionType()));
      }
      return patterns;
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }
}
