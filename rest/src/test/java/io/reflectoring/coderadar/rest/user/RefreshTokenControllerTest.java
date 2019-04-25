package io.reflectoring.coderadar.rest.user;

import io.reflectoring.coderadar.core.projectadministration.port.driver.user.refresh.RefreshTokenCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.refresh.RefreshTokenUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class RefreshTokenControllerTest {

  @Mock private RefreshTokenUseCase refreshTokenUseCase;
  private RefreshTokenController testSubject;

  @BeforeEach
  public void setup() {
    testSubject = new RefreshTokenController(refreshTokenUseCase);
  }

  @Test
  public void refreshTokenSuccessfully() {
    RefreshTokenCommand command = new RefreshTokenCommand("accessToken", "refreshToken");
    Mockito.when(refreshTokenUseCase.refreshToken(command)).thenReturn("newAccessToken");
    ResponseEntity<String> responseEntity = testSubject.refreshToken(command);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertEquals("newAccessToken", responseEntity.getBody());
  }
}
