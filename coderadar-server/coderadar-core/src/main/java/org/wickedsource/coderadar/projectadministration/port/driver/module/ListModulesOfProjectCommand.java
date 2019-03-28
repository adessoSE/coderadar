package org.wickedsource.coderadar.projectadministration.port.driver.module;

import lombok.Value;

@Value
public class ListModulesOfProjectCommand {
  private Long projectId;
}
