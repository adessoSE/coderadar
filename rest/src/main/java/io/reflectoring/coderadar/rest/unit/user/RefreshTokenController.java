package io.reflectoring.coderadar.rest.unit.user;

import io.reflectoring.coderadar.core.projectadministration.RefreshTokenNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.UserNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.refresh.RefreshTokenCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.refresh.RefreshTokenResponse;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.refresh.RefreshTokenUseCase;
import io.reflectoring.coderadar.core.projectadministration.AccessTokenNotExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
  public ResponseEntity refreshToken(@RequestBody @Validated RefreshTokenCommand command) {
    try {
      return new ResponseEntity<>(new RefreshTokenResponse(refreshTokenUseCase.refreshToken(command)), HttpStatus.OK);
    } catch (RefreshTokenNotFoundException | UserNotFoundException e){
      return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    } catch (AccessTokenNotExpiredException e){
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }
}
