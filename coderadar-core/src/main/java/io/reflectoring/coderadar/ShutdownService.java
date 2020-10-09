package io.reflectoring.coderadar;

import io.reflectoring.coderadar.analyzer.service.AnalyzingService;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import io.reflectoring.coderadar.projectadministration.service.project.ScanProjectScheduler;
import io.reflectoring.coderadar.useradministration.service.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShutdownService implements ShutdownUseCase {

  private final ProcessProjectService processProjectService;
  private final ScanProjectScheduler scanProjectScheduler;
  private final AnalyzingService analyzingService;
  private final ConfigurableApplicationContext applicationContext;
  private final TokenService tokenService;

  @Override
  public void shutdown() throws InterruptedException {
    tokenService.setShuttingDown(true);
    analyzingService.onShutdown();
    scanProjectScheduler.onShutdown();
    processProjectService.onShutdown();
    applicationContext.close();
  }
}
