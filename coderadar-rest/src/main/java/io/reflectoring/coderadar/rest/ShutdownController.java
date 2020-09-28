package io.reflectoring.coderadar.rest;

import io.reflectoring.coderadar.ShutdownUseCase;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@RequiredArgsConstructor
public class ShutdownController implements AbstractBaseController {

  private final ShutdownUseCase shutdownUseCase;
  private final AuthenticationService authenticationService;

  @GetMapping("/shutdown")
  public void shutdown() throws InterruptedException {
    authenticationService.authenticatePlatformAdmin();
    shutdownUseCase.shutdown();
  }
}
