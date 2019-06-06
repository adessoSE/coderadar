package io.reflectoring.coderadar.rest.unit.user;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.projectadministration.port.driver.user.refresh.RefreshTokenCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.user.refresh.RefreshTokenResponse;
import io.reflectoring.coderadar.projectadministration.port.driver.user.refresh.RefreshTokenUseCase;
import io.reflectoring.coderadar.rest.user.RefreshTokenController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class RefreshTokenControllerTest {

  private RefreshTokenUseCase refreshTokenUseCase = mock(RefreshTokenUseCase.class);

  @Test
  void refreshTokenSuccessfully() {
    RefreshTokenController testSubject = new RefreshTokenController(refreshTokenUseCase);

    RefreshTokenCommand command = new RefreshTokenCommand("accessToken", "refreshToken");
    Mockito.when(refreshTokenUseCase.refreshToken(command)).thenReturn("newAccessToken");
    ResponseEntity<RefreshTokenResponse> responseEntity = testSubject.refreshToken(command);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertEquals("newAccessToken", responseEntity.getBody().getToken());
  }
}
