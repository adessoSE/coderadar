package io.reflectoring.coderadar.rest.unit.user;

import io.reflectoring.coderadar.rest.useradministration.RefreshTokenController;
import io.reflectoring.coderadar.useradministration.port.driver.refresh.RefreshTokenCommand;
import io.reflectoring.coderadar.useradministration.port.driver.refresh.RefreshTokenResponse;
import io.reflectoring.coderadar.useradministration.port.driver.refresh.RefreshTokenUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;

class RefreshTokenControllerTest {

  private final RefreshTokenUseCase refreshTokenUseCase = mock(RefreshTokenUseCase.class);

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
