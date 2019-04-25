package io.reflectoring.coderadar.core.projectadministration.service.filepattern;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.SetFilePatternForProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.SetFilePatternForProjectCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.SetFilePatternForProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;

public class SetFilePatternForProjectService implements SetFilePatternForProjectUseCase {

  private final SetFilePatternForProjectPort setFilePatternForProjectPort;
  private final GetProjectPort getProjectPort;

  @Autowired
  public SetFilePatternForProjectService(
      SetFilePatternForProjectPort setFilePatternForProjectPort, GetProjectPort getProjectPort) {
    this.setFilePatternForProjectPort = setFilePatternForProjectPort;
    this.getProjectPort = getProjectPort;
  }

  @Override
  public void setFilePattern(SetFilePatternForProjectCommand command) {
    FilePattern filePattern = new FilePattern();
    filePattern.setPattern(command.getPattern());
    filePattern.setInclusionType(command.getInclusionType());
    filePattern.setFileSetType(command.getFileSetType());
    filePattern.setProject(getProjectPort.get(command.getProjectId()));
    setFilePatternForProjectPort.setFilePattern(filePattern);
  }
}
