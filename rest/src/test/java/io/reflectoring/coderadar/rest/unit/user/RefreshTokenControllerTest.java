package io.reflectoring.coderadar.rest.unit.user;

import io.reflectoring.coderadar.core.projectadministration.port.driver.user.refresh.RefreshTokenCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.refresh.RefreshTokenResponse;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.refresh.RefreshTokenUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class RefreshTokenControllerTest {

  @Mock private RefreshTokenUseCase refreshTokenUseCase;
  @InjectMocks private RefreshTokenController testSubject;

  @Test
  void refreshTokenSuccessfully() {
    RefreshTokenCommand command = new RefreshTokenCommand("accessToken", "refreshToken");
    Mockito.when(refreshTokenUseCase.refreshToken(command)).thenReturn("newAccessToken");
    ResponseEntity<RefreshTokenResponse> responseEntity = testSubject.refreshToken(command);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertEquals("newAccessToken", responseEntity.getBody().getToken());
  }
}
