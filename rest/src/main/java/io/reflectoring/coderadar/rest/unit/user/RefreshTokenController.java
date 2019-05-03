package io.reflectoring.coderadar.rest.unit.user;

import io.reflectoring.coderadar.core.projectadministration.port.driver.user.refresh.RefreshTokenCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.refresh.RefreshTokenUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RefreshTokenController {
  private final RefreshTokenUseCase refreshTokenUseCase;

  @Autowired
  public RefreshTokenController(RefreshTokenUseCase refreshTokenUseCase) {
    this.refreshTokenUseCase = refreshTokenUseCase;
  }

  @PostMapping(path = "/user/refresh")
  public ResponseEntity<String> refreshToken(@RequestBody RefreshTokenCommand command) {
    return new ResponseEntity<>(refreshTokenUseCase.refreshToken(command), HttpStatus.OK);
  }
}
