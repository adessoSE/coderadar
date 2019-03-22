package org.wickedsource.coderadar.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.wickedsource.coderadar.projectadministration.domain.User;
import org.wickedsource.coderadar.security.domain.RefreshToken;
import org.wickedsource.coderadar.security.domain.RefreshTokenRepository;

@ExtendWith(SpringExtension.class)
public class LoginServiceTest {

  @InjectMocks private LoginService loginService;

  @Mock private RefreshTokenRepository refreshTokenRepository;

  @Test
  public void testLogin() throws Exception {}

  @Test
  public void testSaveRefreshToken() throws Exception {

    when(refreshTokenRepository.save(any(RefreshToken.class))).thenReturn(new RefreshToken());
    String refreshToken = "refreshToken";
    User user = new User();
    loginService.saveRefreshToken(user, refreshToken);

    ArgumentCaptor<RefreshToken> refreshTokenArgumentCaptor =
        ArgumentCaptor.forClass(RefreshToken.class);

    verify(refreshTokenRepository).save(refreshTokenArgumentCaptor.capture());
    assertThat(refreshTokenArgumentCaptor.getValue().getToken()).isEqualTo(refreshToken);
    assertThat(refreshTokenArgumentCaptor.getValue().getUser()).isEqualTo(user);
  }
}
