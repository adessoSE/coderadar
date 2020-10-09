package io.reflectoring.coderadar.projectadministration.service.module;

import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.DeleteModulePort;
import io.reflectoring.coderadar.projectadministration.port.driven.module.GetModulePort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.delete.DeleteModuleUseCase;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteModuleService implements DeleteModuleUseCase {

  private final DeleteModulePort deleteModulePort;
  private final ProcessProjectService processProjectService;
  private final GetModulePort getModulePort;

  private static final Logger logger = LoggerFactory.getLogger(DeleteModuleService.class);

  @Override
  public void delete(long id, long projectId) {
    Module module = getModulePort.get(id);
    processProjectService.executeTask(
        () -> {
          deleteModulePort.delete(id, projectId);
          logger.info("Deleted module {} for project with id {}", module.getPath(), projectId);
        },
        projectId);
  }
}
