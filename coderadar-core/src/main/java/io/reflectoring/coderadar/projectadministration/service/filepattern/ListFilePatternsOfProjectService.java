package io.reflectoring.coderadar.projectadministration.service.filepattern;

import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.ListFilePatternsOfProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get.GetFilePatternResponse;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get.ListFilePatternsOfProjectUseCase;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListFilePatternsOfProjectService implements ListFilePatternsOfProjectUseCase {

  private final ListFilePatternsOfProjectPort port;

  public ListFilePatternsOfProjectService(ListFilePatternsOfProjectPort port) {
    this.port = port;
  }

  @Override
  public List<GetFilePatternResponse> listFilePatterns(Long projectId) {
    return port.listFilePatternResponses(projectId);
  }
}
